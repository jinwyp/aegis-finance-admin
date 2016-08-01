package com.yimei.controllers.restful;

import com.yimei.api.tpl.representations.TaskRepresentation;
import com.yimei.service.tpl.impl.ActivitiServiceImpl;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuxinjie on 16/8/1.
 */
@RestController
public class MyRestController {
    @Autowired
    private ActivitiServiceImpl activitiService;

    /**
     * 开启流程实例
     */
    @RequestMapping(value = "/process/{personId}/{compId}", method = RequestMethod.GET)
    public void startProcessInstance(@PathVariable Long personId,
                                     @PathVariable Long compId) {
        activitiService.startProcess(personId, compId);
    }

    /**
     * 获取当前人的任务
     */
    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    public List<TaskRepresentation> getTasks(@RequestParam String assignee) {
        System.out.println(" --------------------------------------- ");
        System.out.println(" --------------------------------------- ");
        System.out.println(" --------------------------------------- ");
        System.out.println(" --------------------------------------- ");
        System.out.println(" --------------------------------------- ");
        System.out.println(" --------------------------------------- ");
        System.out.println(" --------------------------------------- ");
        System.out.println(" --------------------------------------- ");
        System.out.println(" --------------------------------------- ");
        System.out.println(" --------------------------------------- ");
        System.out.println(" --------------------------------------- ");
        System.out.println(" --------------------------------------- ");
        System.out.println(" --------------------------------------- ");
        List<Task> tasks = activitiService.getTasks(assignee);
        List<TaskRepresentation> dtos = new ArrayList<TaskRepresentation>();

        for (Task task : tasks) {
            dtos.add(new TaskRepresentation(task.getId(), task.getName()));
        }
        System.out.println(" --------- " + dtos.toString());
        System.out.println(" --------- " + dtos.toString());
        System.out.println(" --------- " + dtos.toString());
        System.out.println(" --------- " + dtos.toString());
        System.out.println(" --------- " + dtos.toString());
        System.out.println(" --------- " + dtos.size());
        System.out.println(" --------- " + dtos.size());
        System.out.println(" --------- " + dtos.size());
        System.out.println(" --------- " + dtos.size());
        System.out.println(" --------- " + dtos.size());
        return dtos;
    }

    /**
     * 完成任务
     */
    @RequestMapping(value = "/complete/{joinApproved}/{taskId}", method = RequestMethod.GET)
    public String complete(@PathVariable Boolean joinApproved, @PathVariable String taskId) {
        activitiService.completeTask(joinApproved, taskId);
        return "ok";
    }



}
