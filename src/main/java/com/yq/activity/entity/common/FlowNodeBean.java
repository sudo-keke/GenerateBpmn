package com.yq.activity.entity.common;

import lombok.Getter;

import java.util.List;

/**
 * @author KK
 * @date 2021/10/21
 * @description 流程节点信息
 **/
@Getter
public class FlowNodeBean {

    /**
     * 节点顺序 0、1、2、3.... 数字越大节点越靠后
     */
    private final Integer order;

    /**
     * 当前节点为单节点
     */
    private final NodeInfoBean node;

    /**
     * 当前节点为多节点
     */
    private final List<NodeInfoBean> nodeList;

    /**
     * 当前节点为多节点时，节点前后的网关设置为何种类型的网关,目前只支持（1,2）
     * 1-并行网关 QhParallelGateway
     * 2-包含网关 QhInclusiveGateway
     * 3-排他网关 ExclusiveGateway
     */
    private Integer gateway;

    /**
     * @param order 节点顺序
     * @param node  节点信息
     * @author KK
     * @date 2021/10/21
     * @description 创建单节点
     */
    public FlowNodeBean(Integer order, NodeInfoBean node) {
        this.order = order;
        this.node = node;
        this.nodeList = null;
    }

    /**
     * @param order    节点顺序
     * @param gateway  网关类型
     * @param nodeList 多节点信息
     * @author KK
     * @date 2021/10/21
     * @description 创建多节点, 并确定网关类型
     */
    public FlowNodeBean(Integer order, Integer gateway, List<NodeInfoBean> nodeList) {
        this.order = order;
        this.nodeList = nodeList;
        this.node = null;
        this.gateway = gateway;
    }

    /**
     * 判断当前 FlowNodeBean 是否是单节点，true单节点，false多节点
     */
    public boolean isSingleNode() {
        return this.node != null;
    }
}
