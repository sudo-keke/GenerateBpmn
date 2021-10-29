package com.kk.activity.exception;

/**
 * @author KK
 * @date 2021/10/29
 * @description 项目审批流异常类
 */
public class DrawFlowException extends RuntimeException {

    public DrawFlowException() {
        super("动态绘制流程异常!");
    }

    public DrawFlowException(String errorMsg) {
        super(errorMsg);
    }
}
