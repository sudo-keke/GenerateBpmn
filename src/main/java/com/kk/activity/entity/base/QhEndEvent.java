package com.kk.activity.entity.base;

import lombok.Data;

/**
 * @author KK
 * @date 2021/10/29
 * @description 流程终点
 */
@Data
public class QhEndEvent extends FlowNode {

    public static QhEndEvent create() {
        QhEndEvent endEvent = new QhEndEvent();
        endEvent.setId(ConstantFlowData.TASK_ID_END);
        endEvent.setName(ConstantFlowData.TASK_NAME_END);
        endEvent.setHeight(ConstantFlowData.START_AND_END_TASK_SIZE);
        endEvent.setWidth(ConstantFlowData.START_AND_END_TASK_SIZE);
        return endEvent;
    }
}
