package com.yimei.finance.controllers.admin.restfulapi.finance;

import com.yimei.finance.repository.admin.user.EnumAdminUserError;
import com.yimei.finance.repository.common.result.Page;
import com.yimei.finance.repository.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.IdentityService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by liuxinjie on 16/8/15.
 */
@RequestMapping("/api/financing/admin")
@Api(value = "Admin-Finance-Home-API", description = "个人待办任务接口")
@RestController
public class FinanceHomeController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private IdentityService identityService;

    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "查看本人任务列表", notes = "通过 User Id 查看本人任务列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "用户(管理员)id", required = true, dataType = "String", paramType = "path"),
        @ApiImplicitParam(name = "page", value = "分页类page", required = true, dataType = "Page", paramType = "body")
    })
    public Result getTasksByUserId(@PathVariable("id")String id, Page page) {
        if (identityService.createUserQuery().userId(id).singleResult() == null) return Result.error(EnumAdminUserError.此用户不存在.toString());
        List<Task> taskList = taskService.createTaskQuery().taskAssignee(id).orderByTaskDueDate().desc().listPage(page.getOffset(), page.getCount());
        page.setTotal(taskService.createTaskQuery().taskAssignee(id).count());
        return Result.success().setData(taskList).setMeta(page);
    }



}
