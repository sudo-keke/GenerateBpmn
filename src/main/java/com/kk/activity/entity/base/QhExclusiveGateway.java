package com.kk.activity.entity.base;

import lombok.Data;

/**
 * @author KK
 * @date 2021/10/21
 * @description 排他网关
 **/
@Data
public class QhExclusiveGateway extends QhGateway {

    public static QhExclusiveGateway create(String id, String name) {
        QhExclusiveGateway exclusiveGateway = new QhExclusiveGateway();
        exclusiveGateway.setId(id);
        exclusiveGateway.setName(name);
        exclusiveGateway.setSize(ConstantFlowData.GATEWAY_SIDE);
        return exclusiveGateway;
    }
}