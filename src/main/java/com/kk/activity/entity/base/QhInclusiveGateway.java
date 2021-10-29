package com.kk.activity.entity.base;

import lombok.Data;

/**
 * @author KK
 * @date 2021/10/29
 * @description 包含网关
 */
@Data
public class QhInclusiveGateway extends QhGateway {

    public static QhInclusiveGateway create(String id, String name) {
        QhInclusiveGateway inclusiveGateway = new QhInclusiveGateway();
        inclusiveGateway.setId(id);
        inclusiveGateway.setName(name);
        inclusiveGateway.setSize(ConstantFlowData.GATEWAY_SIDE);
        return inclusiveGateway;
    }
}
