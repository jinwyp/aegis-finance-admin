package com.yimei.service.tpl.impl;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuxinjie on 16/8/1.
 */
@Service
@Transactional
public class ActivitiServiceImpl {

    /**
     * 注入我们自动配置好的服务
     */
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;

    /**
     * 开始流程, 传入申请的 id 以及公司的 id
     */
    public void startProcess(Long personId, Long compId) {
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("personId", personId);
        variables.put("compId", compId);
        runtimeService.startProcessInstanceByKey("joinProcess", variables);
    }

    /**
     * 获得某个人的任务列表
     */
    public List<Task> getTasks(String assignee) {
        return taskService.createTaskQuery().taskCandidateUser(assignee).list();
    }

    /**
     * 完成任务
     */
    public void completeTask(Boolean joinApproved, String taskId) {
        Map<String, Object> taskVariables = new HashMap<String, Object>();
        taskVariables.put("joinApproved", joinApproved);
        taskVariables.put(taskId, taskVariables);
    }

}
