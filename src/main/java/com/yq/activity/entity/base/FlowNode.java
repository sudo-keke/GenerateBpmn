package com.yq.activity.entity.base;

import com.yq.activity.exception.DrawFlowException;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author KK
 * @date 2021/10/29
 * @description 流程节点
 */
@Data
public class FlowNode {

    /**
     * id
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 高
     */
    private int height;

    /**
     * 宽
     */
    private int width;

    /**
     * 位置坐标
     */
    private Position position;

    /**
     * 设置坐标
     */
    public void setPosition(int x, int y) {
        this.position = new Position(x, y);
    }

    /**
     * 获取x轴坐标
     */
    public int getX() {
        if (null == position) {
            throw new DrawFlowException("节点未设置坐标");
        }
        return position.getX();
    }

    /**
     * 获取y轴坐标
     */
    public int getY() {
        if (null == position) {
            throw new DrawFlowException("节点未设置坐标");
        }
        return position.getY();
    }

    /**
     * 添加横向连线，只有起点、终点两个结点(x,y 轴默认递增添加节点)
     */
    public QhSequenceFlow linkTransverseLineTo(FlowNode linkedNode) {
        return linkTransverseLineTo(linkedNode, null, null);
    }

    /**
     * @param linkedNode 目标节点
     * @param name       线条名称
     * @param condition  连线条件
     * @return com.yq.activity.entity.base.QhSequenceFlow
     * @author KK
     * @date 2021/10/29
     * @description 添加横向连线，只有起点、终点两个结点(x,y 轴默认递增添加节点)
     */
    public QhSequenceFlow linkTransverseLineTo(FlowNode linkedNode, String name, String condition) {
        // 1. 创建连线
        QhSequenceFlow sequenceFlow = QhSequenceFlow.create(this.getId(), linkedNode.getId(), name, condition);

        List<QhSequenceFlowWayPoint> wayPointList = new ArrayList<>();
        // 连线起点
        QhSequenceFlowWayPoint startWapPoint = QhSequenceFlowWayPoint.create(
                this.getX() + this.width, this.getY() + this.height / 2);
        // 连线终点
        QhSequenceFlowWayPoint endWapPoint = QhSequenceFlowWayPoint.create(
                startWapPoint.getX() + ConstantFlowData.TRANSVERSE_FLOW_LENGTH, startWapPoint.getY());
        wayPointList.add(startWapPoint);
        wayPointList.add(endWapPoint);
        sequenceFlow.setWayPointList(wayPointList);

        // 2.设置下一个节点起点
        if (null == linkedNode.getPosition()) {
            linkedNode.setPosition(endWapPoint.getX(), endWapPoint.getY() - linkedNode.getHeight() / 2);
        }
        return sequenceFlow;
    }


    /**
     * @param linkedNode 目标节点
     * @param down       true-向下画线 y轴值增加，false-向上画线 y轴值增加
     * @param multiple   长度倍数
     * @return com.yq.activity.entity.base.QhSequenceFlow
     * @author KK
     * @date 2021/10/29
     * @description 添加纵向连线，有起点、转折点、终点共3个结点，
     */
    public QhSequenceFlow linkEndwiseLineTo(FlowNode linkedNode, boolean down, int multiple) {
        // 1. 创建连线
        QhSequenceFlow sequenceFlow = QhSequenceFlow.create(this.getId(), linkedNode.getId());

        List<QhSequenceFlowWayPoint> wayPointList = new ArrayList<>();

        QhSequenceFlowWayPoint startWapPoint;   // 连线起点
        QhSequenceFlowWayPoint changeWapPoint;  // 转折点
        QhSequenceFlowWayPoint endWapPoint;     // 连线终点
        if (down) {
            startWapPoint = QhSequenceFlowWayPoint.create(this.getX() + this.width / 2, this.getY() + this.getHeight());
            changeWapPoint = QhSequenceFlowWayPoint.create(
                    startWapPoint.getX(),
                    startWapPoint.getY() + ConstantFlowData.VERTICAL_FLOW_LENGTH * multiple);
            endWapPoint = QhSequenceFlowWayPoint.create(
                    changeWapPoint.getX() + this.getWidth() / 2 + ConstantFlowData.TRANSVERSE_FLOW_LENGTH,
                    changeWapPoint.getY());
        } else {
            startWapPoint = QhSequenceFlowWayPoint.create(this.getX() + this.width, this.getY() + this.getHeight() / 2);
            changeWapPoint = QhSequenceFlowWayPoint.create(
                    startWapPoint.getX() + ConstantFlowData.TRANSVERSE_FLOW_LENGTH + linkedNode.getWidth() / 2, startWapPoint.getY());
            endWapPoint = QhSequenceFlowWayPoint.create(
                    changeWapPoint.getX(), changeWapPoint.getY() - ConstantFlowData.VERTICAL_FLOW_LENGTH * 2);
        }
        wayPointList.add(startWapPoint);
        wayPointList.add(changeWapPoint);
        wayPointList.add(endWapPoint);
        sequenceFlow.setWayPointList(wayPointList);

        // 2.设置下一个节点起点
        if (null == linkedNode.getPosition()) {
            linkedNode.setPosition(endWapPoint.getX(), endWapPoint.getY() - linkedNode.getHeight() / 2);
        }

        return sequenceFlow;
    }

    /**
     * @param linkedNode 目标节点
     * @param multiple   长度倍数
     * @return com.yq.activity.entity.base.QhSequenceFlow
     * @author KK
     * @date 2021/10/29
     * @description 添加长纵向连线，有起点、转折点、终点共3个结点，
     */
    public QhSequenceFlow linkEndwiseLongLineTo(FlowNode linkedNode, int multiple) {
        return linkEndwiseLongLineTo(linkedNode, multiple, null, null);
    }


    /**
     * @param linkedNode 目标节点
     * @param multiple   长度倍数
     * @param name       名称
     * @param condition  条件
     * @return com.yq.activity.entity.base.QhSequenceFlow
     * @author KK
     * @date 2021/10/29
     * @description 添加长纵向连线，有起点、转折点、终点共3个结点，
     */
    public QhSequenceFlow linkEndwiseLongLineTo(FlowNode linkedNode, int multiple, String name, String condition) {
        // 1. 创建连线
        QhSequenceFlow sequenceFlow = QhSequenceFlow.create(this.getId(), linkedNode.getId(), name, condition);

        List<QhSequenceFlowWayPoint> wayPointList = new ArrayList<>();
        // 连线起点
        QhSequenceFlowWayPoint startWapPoint = QhSequenceFlowWayPoint.create(
                this.getX() + this.width / 2, this.getY() + this.height);
        // 转折点
        QhSequenceFlowWayPoint changeWapPoint = QhSequenceFlowWayPoint.create(
                startWapPoint.getX(), this.getY() + ConstantFlowData.VERTICAL_FLOW_LENGTH);
        // 连线终点
        QhSequenceFlowWayPoint endWapPoint = QhSequenceFlowWayPoint.create(
                changeWapPoint.getX() + this.getWidth() / 2 +
                        (ConstantFlowData.TRANSVERSE_FLOW_LENGTH + ConstantFlowData.USER_TASK_WIDTH) * multiple + ConstantFlowData.TRANSVERSE_FLOW_LENGTH,
                changeWapPoint.getY());
        wayPointList.add(startWapPoint);
        wayPointList.add(changeWapPoint);
        wayPointList.add(endWapPoint);
        sequenceFlow.setWayPointList(wayPointList);

        // 2.设置下一个节点起点
        if (null == linkedNode.getPosition()) {
            linkedNode.setPosition(endWapPoint.getX(), endWapPoint.getY() - linkedNode.getHeight() / 2);
        }
        return sequenceFlow;
    }

}
