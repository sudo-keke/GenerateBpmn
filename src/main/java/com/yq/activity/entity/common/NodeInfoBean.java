package com.yq.activity.entity.common;

import lombok.Getter;

/**
 * @author KK
 * @date 2021/10/21
 * @description 节点对象详细信息
 **/
@Getter
public class NodeInfoBean {

    /**
     * 节点Id(英文)，e.g. submitTask （相同会被覆盖）
     */
    private final String nodeId;

    /**
     * 流程图中显示的节点名称(中文) e.g. 安全专家提交
     */
    private final String nodeName;

    /**
     * 当前节点的用户id e.g. 1000105 （相同会被覆盖）
     */
    private final String assignee;

    public NodeInfoBean(String nodeId, String nodeName, String assignee) {
        this.nodeId = nodeId;
        this.nodeName = nodeName;
        this.assignee = assignee;
    }
}
