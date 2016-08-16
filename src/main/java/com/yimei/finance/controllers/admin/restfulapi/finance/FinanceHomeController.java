package com.yimei.finance.controllers.admin.restfulapi.finance;

import com.yimei.finance.repository.common.result.Page;
import com.yimei.finance.repository.common.result.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by liuxinjie on 16/8/15.
 */
@RequestMapping("/api/finance/")
@RestController
public class FinanceHomeController {
    @Autowired
    private TaskService taskService;

    /**
     * 确定金融申请单类型
     */

    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "看到本人任务列表", notes = "查看本人任务列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "用户(管理员)id", required = true, dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "meta", value = "page, count, offset, total", required = true, dataType = "String", paramType = "query")
    })
    public Result getTasksByUserId(@RequestParam(value = "id", required = true)String id,
                                   Page meta) {
        List<Task> taskList = taskService.createTaskQuery().taskAssignee(id).orderByTaskDueDate().desc().listPage(meta.getOffset(), meta.getCount());
        meta.setTotal(taskService.createTaskQuery().taskAssignee(id).count());
        return Result.success().setData(taskList).setMeta(meta);
    }



}
