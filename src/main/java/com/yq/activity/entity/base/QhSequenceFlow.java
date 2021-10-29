package com.yq.activity.entity.base;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author KK
 * @date 2021/10/29
 * @description 节点连线
 */
@Data
@Accessors(chain = true)
public class QhSequenceFlow {
    /**
     * 连线id
     */
    private String id;
    /**
     * 连线name
     */
    private String name;
    /**
     * 连线源节点
     */
    private String sourceId;
    /**
     * 连线目标节点
     */
    private String targetId;
    /**
     * 连线条件
     */
    private String condition;
    /**
     * 连线节点
     */
    private List<QhSequenceFlowWayPoint> wayPointList;

    public static QhSequenceFlow create(String sourceId, String targetId) {
        return create(sourceId, targetId, null, null);
    }

    public static QhSequenceFlow create(String sourceId, String targetId, String name, String condition) {
        return new QhSequenceFlow().setId(sourceId + "_" + targetId)
                .setSourceId(sourceId)
                .setTargetId(targetId)
                .setName(name)
                .setCondition(condition);
    }
}
