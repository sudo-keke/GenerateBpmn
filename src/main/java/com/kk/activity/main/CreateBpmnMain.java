package com.kk.activity.main;

import com.kk.activity.entity.common.FlowNodeBean;
import com.kk.activity.entity.common.NodeInfoBean;
import com.kk.activity.util.AutoGenerateFlowUtils;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author KK
 * @className CreateBpmnMain
 * @date 2021/10/29
 * @description 演示类
 **/
@Service
public class CreateBpmnMain {

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private RuntimeService runtimeService;


    /**
     * 流程实例id，保证每种流程的 processId 不相同
     */
    private static final String PROCESS_ID = "SICK_LEAVE_PROCESS";

    /**
     * 流程实例名称
     */
    private static final String PROCESS_NAME = "病假流程";

    /**
     * 业务key，方便查询
     */
    private static final String BUSINESS_KEY = "病假流程:{业务id}";


    /**
     * 每个流程节点只有一个人审批 （示例：templates\single.png）
     * <p>
     * 员工提交 --> 负责人审批 --> HR审批
     */
    public String singleNode() {
        List<FlowNodeBean> nodeList = new ArrayList<>();
        FlowNodeBean node1 = new FlowNodeBean(1, new NodeInfoBean("submitTask", "员工提交", "100001"));
        FlowNodeBean node2 = new FlowNodeBean(2, new NodeInfoBean("PrincipalTask", "负责人审批", "100002"));
        FlowNodeBean node3 = new FlowNodeBean(3, new NodeInfoBean("HRTask", "HR审批", "100003"));
        nodeList.add(node1);
        nodeList.add(node2);
        nodeList.add(node3);
        String xmlStr = AutoGenerateFlowUtils.producedXmlStr(PROCESS_ID, PROCESS_NAME, nodeList);
        //createDeploymentStart(PROCESS_ID, PROCESS_NAME, BUSINESS_KEY, xmlStr);
        return xmlStr;
    }


    /**
     * 某个流程节点有多人审批 （示例：templates\multi.png）
     * <p>
     * 员工提交
     * --> 负责人审批 [or/and] 组长审批 [or/and] ....
     * --> HR审批
     */
    public String multiNode() {
        List<FlowNodeBean> nodeList = new ArrayList<>();
        FlowNodeBean node1 = new FlowNodeBean(1, new NodeInfoBean("submitTask", "员工提交", "100001"));
        List<NodeInfoBean> nodeInfoBeans = Arrays.asList(
                new NodeInfoBean("PrincipalTask-1", "组长", "1000021"),
                new NodeInfoBean("PrincipalTask-2", "副组长", "1000022"),
                new NodeInfoBean("PrincipalTask-3", "部门负责人", "1000023")
        );
        FlowNodeBean node2 = new FlowNodeBean(2, 1, nodeInfoBeans);
        FlowNodeBean node3 = new FlowNodeBean(3, new NodeInfoBean("HRTask", "HR审批", "100003"));
        nodeList.add(node1);
        nodeList.add(node2);
        nodeList.add(node3);

        String xmlStr = AutoGenerateFlowUtils.producedXmlStr(PROCESS_ID, PROCESS_NAME, nodeList);
        //createDeploymentStart(PROCESS_ID, PROCESS_NAME, BUSINESS_KEY, xmlStr);
        return xmlStr;
    }


    /**
     * @param processId   流程实例id
     * @param processName 流程实例名称（随便取，尽量与生成xml时的相同）
     * @param businessKey 业务key（随便取，尽量使查询时更方便，如 {流程类型}:{实例id}:{业务id}:{提交人id}）
     * @param xmlStr      生成的xml
     * @return org.activiti.engine.runtime.ProcessInstance
     * @author KK
     * @date 2021/10/29
     * @description 部署 bpmn 并启动流程
     */
    public ProcessInstance createDeploymentStart(String processId, String processName, String businessKey, String xmlStr) {
        Deployment deploy = repositoryService.createDeployment()
                .addString(processId + ".bpmn", xmlStr)
                .name(processName)
                .deploy();
        //可以使用各种启动方式，参数也不相同
        return runtimeService.startProcessInstanceById(processId, businessKey);
    }
}
