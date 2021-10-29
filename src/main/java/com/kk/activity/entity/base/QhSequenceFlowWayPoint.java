package com.kk.activity.entity.base;

import com.kk.activity.exception.DrawFlowException;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author KK
 * @date 2021/10/29
 * @description 节点连线转折点
 */
@Data
@Accessors(chain = true)
public class QhSequenceFlowWayPoint {

    /**
     * 位置坐标
     */
    private Position position;

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

    public static QhSequenceFlowWayPoint create(int x, int y) {
        return new QhSequenceFlowWayPoint()
                .setPosition(new Position(x, y));
    }
}
