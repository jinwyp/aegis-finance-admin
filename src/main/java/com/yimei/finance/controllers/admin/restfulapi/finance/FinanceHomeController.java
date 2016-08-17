package com.yimei.finance.controllers.admin.restfulapi.finance;

import com.yimei.finance.entity.admin.user.ApplyInfo;
import com.yimei.finance.repository.admin.applyinfo.ApplyInfoRepository;
import com.yimei.finance.repository.admin.applyinfo.EnumAdminFinanceError;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = {"admin-api-flow"}, description = "金融公用接口")
@RequestMapping("/api/financing/admin")
@RestController
public class FinanceHomeController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private ApplyInfoRepository applyInfoRepository;

    @RequestMapping(value = "/determine/type/{id}/{applyType}", method = RequestMethod.PUT)
    @ApiOperation(value = "确定金融申请单类型", notes = "确定金融申请单类型")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "金融申请单id", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "applyType", value = "金融申请单类型", required = true, dataType = "String", paramType = "path")
    })
    public Result determineFinanceOrderMethod(@PathVariable("id")Long id, @PathVariable("applyType")String applyType) {
        ApplyInfo applyInfo = applyInfoRepository.findOne(id);
        if (applyInfo == null) return Result.error(EnumAdminFinanceError.此金融单不存在.toString());
//        applyInfoRepository.updateApplyType(id, applyType);
        return Result.success().setData(applyInfoRepository.findOne(id));
    }

    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "查看本人任务列表", notes = "通过 User Id 查看本人任务列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "用户(管理员)id", required = true, dataType = "String", paramType = "path"),
        @ApiImplicitParam(name = "page", value = "分页类page", required = true, dataType = "Page", paramType = "body")
    })
    public Result getTasksByUserIdMethod(@PathVariable("id")String id, Page page) {
        if (identityService.createUserQuery().userId(id).singleResult() == null) return Result.error(EnumAdminUserError.此用户不存在.toString());
        List<Task> taskList = taskService.createTaskQuery().taskAssignee(id).orderByTaskDueDate().desc().listPage(page.getOffset(), page.getCount());
        page.setTotal(taskService.createTaskQuery().taskAssignee(id).count());
        return Result.success().setData(taskList).setMeta(page);
    }



}
