package com.kk.activity.entity.base;

import lombok.Data;

/**
 * @author KK
 * @date 2021/10/29
 * @description 并行网关
 */
@Data
public class QhParallelGateway extends QhGateway {

    public static QhParallelGateway create(String id, String name) {
        QhParallelGateway parallelGateway = new QhParallelGateway();
        parallelGateway.setId(id);
        parallelGateway.setName(name);
        parallelGateway.setSize(ConstantFlowData.GATEWAY_SIDE);
        return parallelGateway;
    }
}
