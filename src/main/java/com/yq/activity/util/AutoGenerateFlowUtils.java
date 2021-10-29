package com.yq.activity.util;


import com.yq.activity.entity.base.*;
import com.yq.activity.entity.common.FlowNodeBean;
import com.yq.activity.entity.common.NodeInfoBean;
import org.dom4j.Document;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author KK
 * @date 2021/10/21
 * @description 自动生成节点流程图（公共）
 **/
public class AutoGenerateFlowUtils {

    /**
     * @param processId   流程id，e.g. projectApprove
     * @param processName 流程名称，e.g. XXX审批流程
     * @param nodeList    流程节点信息
     * @return java.lang.String
     * @author KK
     */
    public static String producedXmlStr(String processId, String processName, List<FlowNodeBean> nodeList) {
        if (CollectionUtils.isEmpty(nodeList) || !nodeList.get(0).isSingleNode()) {
            return null;
        }
        QhProcess process = QhProcess.create(processId, processName);
        //第一个节点一定是单节点且为提交节点
        FlowNodeBean firstNode = nodeList.get(0);
        NodeInfoBean submitTask = firstNode.getNode();
        QhUserTask task = QhUserTask.create(submitTask.getNodeId(), submitTask.getNodeName(), submitTask.getAssignee());
        process.addUserTask(task);
        process.addLine(process.getStartEvent().linkTransverseLineTo(task));

        nodeList = nodeList.stream().sorted(Comparator.comparing(FlowNodeBean::getOrder)).collect(Collectors.toList());
        //前节点
        FlowNode node = task;
        for (int i = 1; i < nodeList.size(); i++) {
            FlowNodeBean flowNodeBean = nodeList.get(i);
            if (flowNodeBean.isSingleNode()) {
                NodeInfoBean singleNode = flowNodeBean.getNode();
                QhUserTask nextTask = QhUserTask.create(singleNode.getNodeId(), singleNode.getNodeName(), singleNode.getAssignee());
                process.addUserTask(nextTask);
                process.addLine(node.linkTransverseLineTo(nextTask));
                node = nextTask;
            } else {
                QhGateway startGateway;
                if (flowNodeBean.getGateway() == 1) {
                    QhParallelGateway parallelGateway = QhParallelGateway.create("parallelGatewayStart" + i, "Parallel Gateway Start" + i);
                    process.getParallelGatewayList().add(parallelGateway);
                    process.addLine(node.linkTransverseLineTo(parallelGateway));
                    startGateway = parallelGateway;
                } else {
                    QhInclusiveGateway inclusiveGateway = QhInclusiveGateway.create("InclusiveGatewayStart" + i, "Inclusive Gateway Start" + i);
                    process.getInclusiveGatewayList().add(inclusiveGateway);
                    process.addLine(node.linkTransverseLineTo(inclusiveGateway));
                    startGateway = inclusiveGateway;
                }
                QhGateway endGateway;
                if (flowNodeBean.getGateway() == 1) {
                    QhParallelGateway parallelGateway = QhParallelGateway.create("parallelGatewayEnd" + i, "Parallel Gateway End" + i);
                    process.getParallelGatewayList().add(parallelGateway);
                    endGateway = parallelGateway;
                } else {
                    QhInclusiveGateway inclusiveGateway = QhInclusiveGateway.create("InclusiveGatewayEnd" + i, "Inclusive Gateway End" + i);
                    process.getInclusiveGatewayList().add(inclusiveGateway);
                    endGateway = inclusiveGateway;
                }
                for (int j = 0; j < flowNodeBean.getNodeList().size(); j++) {
                    NodeInfoBean nodeInfoBean = flowNodeBean.getNodeList().get(j);
                    QhUserTask taskInGateway = QhUserTask.create(nodeInfoBean.getNodeId(), nodeInfoBean.getNodeName(), nodeInfoBean.getAssignee());
                    process.addUserTask(taskInGateway);

                    if (j == 0) {
                        process.addLine(startGateway.linkTransverseLineTo(taskInGateway));
                        process.addLine(taskInGateway.linkTransverseLineTo(endGateway));
                    } else {
                        process.addLine(startGateway.linkEndwiseLineTo(taskInGateway, true, j));
                        process.addLine(taskInGateway.linkEndwiseLineTo(endGateway, false, j));
                    }
                }
                node = endGateway;
            }
        }
        process.addLine(node.linkTransverseLineTo(process.getEndEvent()));
        Document document = DrawBpmnXmlUtils.generateXml(process);
        return DrawBpmnXmlUtils.xmlToStr(document);
    }
}
