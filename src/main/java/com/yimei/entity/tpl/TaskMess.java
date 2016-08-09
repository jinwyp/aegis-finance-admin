package com.yimei.entity.tpl;

import org.activiti.engine.task.Task;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by wangqi on 16/8/9.
 */
@Component
public class TaskMess implements Serializable {
    private Task task;
    private String username;
    private String processId;


    public TaskMess(){
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
