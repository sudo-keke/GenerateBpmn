package com.yq.activity.entity.base;

import lombok.Data;

/**
 * @author KK
 * @date 2021/10/29
 * @description 网关
 */
@Data
public class QhGateway extends FlowNode {

    protected void setSize(int gatewaySide) {
        this.setHeight(gatewaySide);
        this.setWidth(gatewaySide);
    }
}
