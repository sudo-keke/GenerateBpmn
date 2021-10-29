package com.kk.activity.entity.base;

import lombok.Data;

/**
 * @author KK
 * @date 2021/10/29
 * @description 流程起点
 */
@Data
public class QhStartEvent extends FlowNode {

    public static QhStartEvent create() {
        QhStartEvent startEvent = new QhStartEvent();
        startEvent.setId(ConstantFlowData.TASK_ID_START);
        startEvent.setName(ConstantFlowData.TASK_NAME_START);
        startEvent.setHeight(ConstantFlowData.START_AND_END_TASK_SIZE);
        startEvent.setWidth(ConstantFlowData.START_AND_END_TASK_SIZE);
        return startEvent;
    }
}
