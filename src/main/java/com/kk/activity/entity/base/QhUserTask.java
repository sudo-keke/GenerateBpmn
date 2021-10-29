package com.kk.activity.entity.base;


import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author KK
 * @date 2021/10/29
 * @description 用户任务节点
 */
@Data
public class QhUserTask extends FlowNode {

    /**
     * 办理人
     */
    private String assignee;
    /**
     * 候选人
     */
    private String candidateUsers;

    public static QhUserTask create(String id, String name, String assignee) {
        return create(id, name, assignee, null);
    }

    public static QhUserTask create(String id, String name, List<Integer> userIds) {
        if (userIds.size() == 1) {
            return create(id, name, String.valueOf(userIds.get(0)), null);
        }
        return create(id, name, null, userIds);
    }

    private static QhUserTask create(String id, String name, String assignee, List<Integer> userIdList) {
        QhUserTask userTask = new QhUserTask();
        userTask.setId(id);
        userTask.setName(name);
        if (StringUtils.isNotBlank(assignee)) {
            userTask.setAssignee(assignee);
        }
        if (userIdList != null && userIdList.size() > 0) {
            String userIds = "";
            for (Integer userId : userIdList) {
                userIds += userId + ",";
            }
            userIds = userIds.substring(0, userIds.length() - 1);
            userTask.setCandidateUsers(userIds);
        }
        userTask.setSize(ConstantFlowData.USER_TASK_HEIGHT, ConstantFlowData.USER_TASK_WIDTH);
        return userTask;
    }

    /**
     * 功能描述：设置大小
     *
     * @Author KK 2020/6/9 18:52
     */
    public void setSize(int height, int width) {
        this.setHeight(height);
        this.setWidth(width);
    }

    public static void main(String[] args) {
        List<String> userIdList = Arrays.asList("12", "23", "34");
        String userIds = "";
        for (String userId : userIdList) {
            userIds += userId + ",";
        }
        userIds = userIds.substring(0, userIds.length() - 1);
        System.out.println(userIds);
    }

}
