package com.yq.activity.main;

import com.yq.activity.entity.common.FlowNodeBean;
import com.yq.activity.entity.common.NodeInfoBean;
import com.yq.activity.util.AutoGenerateFlowUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author KK
 * @className CreateBpmnMain
 * @date 2021/10/29
 * @description
 **/
public class CreateBpmnMain {


    /**
     * 每个流程节点只有一个人审批
     * 员工提交 --> 负责人审批 --> HR审批
     */
    public static String SingleNode() {
        //员工提交--负责人审批--HR审批
        List<FlowNodeBean> nodeList = new ArrayList<>();
        FlowNodeBean node1 = new FlowNodeBean(1, new NodeInfoBean("submitTask", "员工提交", "100001"));
        FlowNodeBean node2 = new FlowNodeBean(2, new NodeInfoBean("marketTask", "负责人审批", "100002"));
        FlowNodeBean node3 = new FlowNodeBean(3, new NodeInfoBean("pomTask", "HR审批", "100003"));
        nodeList.add(node1);
        nodeList.add(node2);
        nodeList.add(node3);
        String processId = "SICK_LEAVE_PROCESS";
        String processName = "病假流程";
        return AutoGenerateFlowUtils.producedXmlStr(processId, processName, nodeList);
    }


    /**
     * 某个流程节点有多人审批
     * 员工提交
     * --> 负责人审批 [or/and] 组长审批 [or/and] ....
     * --> HR审批
     */
//    public static String multiNode() {
//        List<FlowNodeBean> nodeList = new ArrayList<>();
//        FlowNodeBean node1 = new FlowNodeBean(1, new NodeInfoBean("submitTask", "员工提交", "100001"));
//        FlowNodeBean node2 = new FlowNodeBean(2, new NodeInfoBean("marketTask", "负责人审批", "100002"));
//        FlowNodeBean node3 = new FlowNodeBean(3, new NodeInfoBean("pomTask", "HR审批", "100003"));
//        nodeList.add(node1);
//        nodeList.add(node2);
//        nodeList.add(node3);
//        String processId = "SICK_LEAVE_PROCESS";
//        String processName = "病假流程";
//        return AutoGenerateFlowUtils.producedXmlStr(processId, processName, nodeList);
//    }
}
