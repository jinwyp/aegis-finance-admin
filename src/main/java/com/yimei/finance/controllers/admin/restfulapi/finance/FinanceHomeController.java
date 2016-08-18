package com.yimei.finance.controllers.admin.restfulapi.finance;

import com.yimei.finance.config.session.AdminSession;
import com.yimei.finance.entity.admin.user.ApplyInfo;
import com.yimei.finance.repository.admin.applyinfo.ApplyInfoRepository;
import com.yimei.finance.repository.admin.applyinfo.EnumAdminFinanceError;
import com.yimei.finance.repository.admin.user.EnumAdminUserError;
import com.yimei.finance.repository.common.result.Page;
import com.yimei.finance.repository.common.result.Result;
import io.swagger.annotations.*;
import org.activiti.engine.IdentityService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private AdminSession adminSession;

    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    @ApiOperation(value = "查看本人任务列表", notes = "通过 User Id 查看本人任务列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页数", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "count", value = "每页显示数量", required = false, dataType = "Integer", paramType = "query")
    })
    public Result getTasksByUserIdMethod(Page page) {
        if (identityService.createUserQuery().userId(adminSession.getUser().getId()).singleResult() == null) return Result.error(EnumAdminUserError.此用户不存在.toString());
        List<Task> taskList = taskService.createTaskQuery().taskAssignee(adminSession.getUser().getId()).orderByTaskDueDate().desc().listPage(page.getOffset(), page.getCount());
        page.setTotal(taskService.createTaskQuery().taskAssignee(adminSession.getUser().getId()).count());
        return Result.success().setData(taskList).setMeta(page);
    }

    @RequestMapping(value = "/determine/type", method = RequestMethod.PUT)
    @ApiOperation(value = "确定金融申请单类型", notes = "确定金融申请单类型")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "金融申请单id", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "applyType", value = "金融申请单类型", required = true, dataType = "String", paramType = "query")
    })
    public Result determineFinanceOrderMethod(@RequestParam(value = "id", required = true)Long id,
                                              @RequestParam(value = "applyType", required = true)String applyType) {
        ApplyInfo applyInfo = applyInfoRepository.findOne(id);
        if (applyInfo == null) return Result.error(EnumAdminFinanceError.此金融单不存在.toString());
//        applyInfoRepository.updateApplyType(id, applyType);
        return Result.success().setData(applyInfoRepository.findOne(id));
    }




}
