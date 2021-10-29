package com.kk.activity.entity.base;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * @author KK
 * @date 2021/10/29
 * @description 流程信息
 */
@Data
@Accessors(chain = true)
public class QhProcess {

    /**
     * 流程id
     */
    private String id;

    /**
     * 流程名称
     */
    private String name;

    /**
     * 流程起点
     */
    private QhStartEvent startEvent = QhStartEvent.create();

    /**
     * 流程终点
     */
    private QhEndEvent endEvent = QhEndEvent.create();

    /**
     * 任务节点
     */
    List<QhUserTask> userTaskList = new ArrayList<>();

    /**
     * 节点连线
     */
    List<QhSequenceFlow> sequenceFlowList = new ArrayList<>();

    /**
     * 并行网关
     */
    List<QhParallelGateway> parallelGatewayList = new ArrayList<>();

    /**
     * 包含网关
     */
    List<QhInclusiveGateway> inclusiveGatewayList = new ArrayList<>();

    public static QhProcess create(String id, String name) {
        QhProcess qhProcess = new QhProcess().setId(id)
                .setName(name)
                .setUserTaskList(new ArrayList<>())
                .setSequenceFlowList(new ArrayList<>())
                .setParallelGatewayList(new ArrayList<>());
        qhProcess.getStartEvent().setPosition(120, 90);
        return qhProcess;
    }

    /**
     * 功能描述： 添加连线
     *
     * @Author KK 2020/6/10 13:44
     */
    public void addLine(QhSequenceFlow qhSequenceFlow) {
        this.sequenceFlowList.add(qhSequenceFlow);
    }

    /**
     * 功能描述： 添加任务节点
     *
     * @Author KK 2020/6/10 16:28
     */
    public void addUserTask(QhUserTask userTask) {
        this.userTaskList.add(userTask);
    }
}
