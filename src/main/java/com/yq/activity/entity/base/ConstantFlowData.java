package com.yq.activity.entity.base;

/**
 * @author KK
 * @date 2021/10/29
 * @description 绘图基础数据
 */
public class ConstantFlowData {

    private ConstantFlowData() {}

    /**
     * 流程起点id
     */
    public static final String TASK_ID_START = "start";
    
    /**
     * 流程终点id
     */
    public static final String TASK_ID_END = "end";

    /**
     * 流程起点名称
     */ 
    public static final String TASK_NAME_START = "startEvent";
    
    /**
     * 流程终点名称
     */
    public static final String TASK_NAME_END = "endEvent";

    /**
     * 任务节点
     */
    public static final String USER_TASK = "userTask";

    /**
     * 连线节点
     */
    public static final String SEQUENCE_FLOW = "sequenceFlow";

    /**
     * 并行网关
     */
    public static final String PARALLEL_GATEWAY = "parallelGateway";
    
    /**
     * 包含网关
     */
    public static final String INCLUSIVE_GATEWAY = "inclusiveGateway";
    
    /**
     * 表达式
     */
    public static final String CONDITION_EXPRESSION = "conditionExpression";
    


    public static final int STARTX = 0;
    public static final int STARTY = 24;

    /**
     * 流程起点、终点大小
     */
    public static final int START_AND_END_TASK_SIZE = 35;   

    /**
     * 流程任务节点高
     */
    public static final int USER_TASK_HEIGHT = 55;        

    /**
     * 流程任务节点宽
     */
    public static final int USER_TASK_WIDTH = 105;        

    /**
     * 网关大小
     */
    public static final int GATEWAY_SIDE = 40;           

    /**
     * 横向线条长度
     */
    public static final int TRANSVERSE_FLOW_LENGTH = 60; 

    /**
     * 纵向线条长度
     */
    public static final int VERTICAL_FLOW_LENGTH = 100;

    /**
     * 流程图任务节点单个字符占位长度
     */
    public static final int baseCharLength = 13;  

    /**
     * 流程图任务节点单行最低高度
     */
    public static final int baseLineHeight = 54;  

}
