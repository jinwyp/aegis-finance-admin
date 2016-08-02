package com.yimei.api.tpl;

import org.activiti.engine.task.Task;

import java.util.List;

/**
 * Created by liuxinjie on 16/8/2.
 */
public interface ActivitiService {

    /**
     * 开始流程, 传入申请的 id 以及公司的 id
     */
    void startProcess(Long personId, Long compId);

    /**
     * 获得某个人的任务列表
     */
    List<Task> getTasks(String assignee);

    /**
     * 完成任务
     */
    void completeTask(Boolean joinApproved, String taskId);
}
