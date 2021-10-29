package com.yq.activity.entity.base;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author KK
 * @date 2021/10/29
 * @description 坐标位置
 */
@Data
@AllArgsConstructor
public class Position {

    /**
     * x 轴坐标
     */
    private int x;

    /**
     * y 轴坐标
     */
    private int y;
}
