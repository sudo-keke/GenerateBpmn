package com.yq.activity.entity.base;


import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;


/**
 * @author KK
 * @date 2021/10/29
 * @description 绘制 流程图 xml
 */
public class DrawBpmnXmlUtils {

    public static Document generateXml(QhProcess qhProcess) {
        try {
            Document document = DocumentHelper.createDocument();
            // 根节点
            Element root = produceRoot(document);
            // prcess 流程节点信息
            produceProcess(root, qhProcess);
            // 流程节点坐标信息
            produceDiagram(root, qhProcess);
            return document;
        } catch (Exception e) {
            e.printStackTrace();
            // 如果出现异常，则不再往下执行
            return null;
        }

    }

    /**
     * 添加XML根节点
     *
     * <definitions xmlns="http://www.omg.org/spec/BPMN/20100524/MODEL" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:activiti="http://activiti.org/bpmn" xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" xmlns:omgdc="http://www.omg.org/spec/DD/20100524/DC" xmlns:omgdi="http://www.omg.org/spec/DD/20100524/DI" typeLanguage="http://www.w3.org/2001/XMLSchema" expressionLanguage="http://www.w3.org/1999/XPath" targetNamespace="http://www.activiti.org/test">
     */
    private static Element produceRoot(Document document) {
        Element root = document.addElement("definitions", "http://www.omg.org/spec/BPMN/20100524/MODEL");
        root.addNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
        root.addNamespace("xsd", "http://www.w3.org/2001/XMLSchema");
        root.addNamespace("activiti", "http://activiti.org/bpmn");
        root.addNamespace("bpmndi", "http://www.omg.org/spec/BPMN/20100524/DI");
        root.addNamespace("omgdc", "http://www.omg.org/spec/DD/20100524/DC");
        root.addNamespace("omgdi", "http://www.omg.org/spec/DD/20100524/DI");
        root.addAttribute("typeLanguage", "http://www.w3.org/2001/XMLSchema");
        root.addAttribute("expressionLanguage", "http://www.w3.org/1999/XPath");
        root.addAttribute("targetNamespace", "http://www.activiti.org/test");
        root.addAttribute("targetNamespace", "http://www.activiti.org/test");
        return root;
    }

    /**
     * 流程的节点
     * <process id="projectOrderApprove" name="projectOrderApprove" isExecutable="true">
     *
     * </process>
     */
    private static Element produceProcess(Element root, QhProcess qhProcess) {
        Element process = root.addElement("process");
        process.addAttribute("id", qhProcess.getId());
        process.addAttribute("name", qhProcess.getName());
        process.addAttribute("isExecutable", "true");

        // 添加进程节点
        addProcessTask(process, qhProcess);

        return process;
    }

    /**
     * 添加进程节点
     * <process> 标签内的子标签数据填充
     */
    private static void addProcessTask(Element process, QhProcess myProcess) {
        // 流程起点
        Element start = process.addElement(ConstantFlowData.TASK_NAME_START);
        start.addAttribute("id", myProcess.getStartEvent().getId());
        start.addAttribute("name", myProcess.getStartEvent().getName());
        // 任务节点
        for (QhUserTask qhUserTask : myProcess.getUserTaskList()) {
            Element userTask = process.addElement(ConstantFlowData.USER_TASK);
            userTask.addAttribute("id", qhUserTask.getId());
            if (StringUtils.isNotBlank(qhUserTask.getName())) {
                userTask.addAttribute("name", qhUserTask.getName());
            }
            if (StringUtils.isNotBlank(qhUserTask.getAssignee())) {
                userTask.addAttribute("activiti:assignee", qhUserTask.getAssignee());
            }
            if (StringUtils.isNotBlank(qhUserTask.getCandidateUsers())) {
                userTask.addAttribute("activiti:candidateUsers", qhUserTask.getCandidateUsers());
            }
        }
        // 流程终点
        Element end = process.addElement(ConstantFlowData.TASK_NAME_END);
        end.addAttribute("id", myProcess.getEndEvent().getId());
        end.addAttribute("name", myProcess.getEndEvent().getName());

        // 任务节点连线
        for (QhSequenceFlow qhSequenceFlow : myProcess.getSequenceFlowList()) {
            Element sequenceFlow = process.addElement(ConstantFlowData.SEQUENCE_FLOW);
            sequenceFlow.addAttribute("id", qhSequenceFlow.getId());
            sequenceFlow.addAttribute("sourceRef", qhSequenceFlow.getSourceId());
            sequenceFlow.addAttribute("targetRef", qhSequenceFlow.getTargetId());
            if (StringUtils.isNotBlank(qhSequenceFlow.getName())) {
                sequenceFlow.addAttribute("name", qhSequenceFlow.getName());
            }
            if (StringUtils.isNotBlank(qhSequenceFlow.getCondition())) {
                Element conditionExpression = sequenceFlow.addElement(ConstantFlowData.CONDITION_EXPRESSION);
                conditionExpression.addAttribute("xsi:type", "tFormalExpression");
                conditionExpression.setText("<![CDATA[" + qhSequenceFlow.getCondition() + "]]>");
            }
        }
        // 并行网关
        if (myProcess.getParallelGatewayList() != null && (myProcess.getParallelGatewayList().size() > 0)) {
            for (QhParallelGateway qhParallelGateway : myProcess.getParallelGatewayList()) {
                Element parallelGateway = process.addElement(ConstantFlowData.PARALLEL_GATEWAY);
                parallelGateway.addAttribute("id", qhParallelGateway.getId());
                parallelGateway.addAttribute("name", qhParallelGateway.getName());
            }
        }
        // 包含网关
        if (myProcess.getInclusiveGatewayList() != null && myProcess.getInclusiveGatewayList().size() > 0) {
            for (QhInclusiveGateway qhInclusiveGateway : myProcess.getInclusiveGatewayList()) {
                Element inclusiveGateway = process.addElement(ConstantFlowData.INCLUSIVE_GATEWAY);
                inclusiveGateway.addAttribute("id", qhInclusiveGateway.getId());
                inclusiveGateway.addAttribute("name", qhInclusiveGateway.getName());
            }
        }
    }

    /**
     * 添加流程节点坐标信息，子标签填充
     *  <bpmndi:BPMNDiagram id="BPMNDiagram_1">
     *  </bpmndi:BPMNDiagram>
     */
    private static void produceDiagram(Element root, QhProcess qhProcess) {
        Element diagram = root.addElement("bpmndi:BPMNDiagram");
        diagram.addAttribute("id", "BPMNDiagram_" + qhProcess.getId());

        Element plane = diagram.addElement("bpmndi:BPMNPlane");
        plane.addAttribute("id", "BPMNPlane_" + qhProcess.getId());
        plane.addAttribute("bpmnElement", qhProcess.getId());

        addShapeNode(plane, qhProcess.getStartEvent());
        addShapeNode(plane, qhProcess.getEndEvent());
        for (QhUserTask qhUserTask : qhProcess.getUserTaskList()) {
            addShapeNode(plane, qhUserTask);
        }
        for (QhParallelGateway qhParallelGateway : qhProcess.getParallelGatewayList()) {
            addShapeNode(plane, qhParallelGateway);
        }
        for (QhInclusiveGateway inclusiveGateway : qhProcess.getInclusiveGatewayList()) {
            addShapeNode(plane, inclusiveGateway);
        }
        for (QhSequenceFlow qhSequenceFlow : qhProcess.getSequenceFlowList()) {
            Element edge = plane.addElement("bpmndi:BPMNEdge");
            edge.addAttribute("id", "BPMNEdge_sequenceFlow_" + qhSequenceFlow.getId());
            edge.addAttribute("bpmnElement", qhSequenceFlow.getId());
            List<QhSequenceFlowWayPoint> flowCoordinateList = qhSequenceFlow.getWayPointList();
            if (flowCoordinateList != null && flowCoordinateList.size() > 0) {
                for (QhSequenceFlowWayPoint flowWayPoint : flowCoordinateList) {
                    Element waypoint = edge.addElement("omgdi:waypoint");
                    waypoint.addAttribute("x", String.valueOf(flowWayPoint.getX()));
                    waypoint.addAttribute("y", String.valueOf(flowWayPoint.getY()));
                }
            }
        }
    }

    /**
     * 绘制节点形状
     */
    private static void addShapeNode(Element plane, FlowNode flowNode) {
        Element shape = plane.addElement("bpmndi:BPMNShape");
        shape.addAttribute("id", "BPMNShape_" + flowNode.getId());
        shape.addAttribute("bpmnElement", flowNode.getId());

        Element bounds = shape.addElement("omgdc:Bounds");
        bounds.addAttribute("height", String.valueOf(flowNode.getHeight()));
        bounds.addAttribute("width", String.valueOf(flowNode.getWidth()));
        bounds.addAttribute("x", String.valueOf(flowNode.getX()));
        bounds.addAttribute("y", String.valueOf(flowNode.getY()));
    }

    /**
     * xml 转字符串
     */
    public static String xmlToStr(Document document) {
        String str = null;
        XMLWriter xmlWriter = null;
        try {
            // 设置XML文档格式
            OutputFormat outputFormat = OutputFormat.createPrettyPrint();
            // 设置XML编码方式,即是用指定的编码方式保存XML文档到字符串(String),这里也可以指定为GBK或是ISO8859-1
            outputFormat.setEncoding("UTF-8");
            //是否生产xml头
            //outputFormat.setSuppressDeclaration(true);
            //设置是否缩进
            outputFormat.setIndent(true);
            //以四个空格方式实现缩进
            outputFormat.setIndent("  ");
            //设置是否换行
            outputFormat.setNewlines(true);
            // stringWriter字符串是用来保存XML文档的
            StringWriter stringWriter = new StringWriter();
            // xmlWriter是用来把XML文档写入字符串的(工具)
            xmlWriter = new XMLWriter(stringWriter, outputFormat);
            // 不转义特殊字符
            xmlWriter.setEscapeText(false);
            // 把创建好的XML文档写入字符串
            xmlWriter.write(document);
            // 打印字符串,即是XML文档
            str = stringWriter.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (xmlWriter != null) {
                try {
                    xmlWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return str;
    }
}


