package com.yimei.finance.controllers.admin.restfulapi.user;

import com.yimei.finance.config.session.AdminSession;
import com.yimei.finance.exception.BusinessException;
import com.yimei.finance.representation.admin.activiti.HistoryTaskObject;
import com.yimei.finance.representation.admin.activiti.TaskObject;
import com.yimei.finance.representation.admin.finance.enums.EnumAdminFinanceError;
import com.yimei.finance.representation.admin.finance.enums.EnumFinanceAssignType;
import com.yimei.finance.representation.admin.user.enums.EnumAdminUserError;
import com.yimei.finance.representation.common.enums.EnumCommonError;
import com.yimei.finance.representation.common.result.Page;
import com.yimei.finance.representation.common.result.Result;
import com.yimei.finance.service.admin.finance.FinanceFlowMethodServiceImpl;
import com.yimei.finance.service.admin.user.AdminUserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.User;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api(tags = {"admin-api-flow"}, description = "金融公用接口")
@RequestMapping("/api/financing/admin/tasks")
@RestController("adminUserCenterController")
public class UserCenterController {
    @Autowired
    private AdminSession adminSession;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private FinanceFlowMethodServiceImpl financeFlowMethodService;
    @Autowired
    private AdminUserServiceImpl userService;
    @Autowired
    private IdentityService identityService;

    @RequestMapping(value = "/{taskId}", method = RequestMethod.GET)
    @ApiOperation(value = "通过 id 查询任务对象", notes = "通过 id 查询任务对象", response = TaskObject.class)
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path")
    public Result getTaskByIdMethod(@PathVariable(value = "taskId") String taskId) {
        return financeFlowMethodService.findTaskByTaskId(taskId, adminSession.getUser().getCompanyId());
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "个人待办任务列表", notes = "个人待办任务列表", response = TaskObject.class, responseContainer = "List")
    @ApiImplicitParam(name = "page", value = "当前页数", required = false, dataType = "int", paramType = "query")
    public Result getPersonalTasksMethod(Page page) {
        return financeFlowMethodService.findSelfTaskList(adminSession.getUser().getId(), adminSession.getUser().getCompanyId(), page);
    }

    @RequestMapping(value = "/unclaimed", method = RequestMethod.GET)
    @ApiOperation(value = "给分配管理员查看待领取任务列表", notes = "给线上交易员管理组, 业务员管理组, 尽调员管理组, 监管员管理组, 风控管理组, 查看待领取任务列表", response = TaskObject.class, responseContainer = "List")
    @ApiImplicitParam(name = "page", value = "当前页数", required = false, dataType = "int", paramType = "query")
    public Result getPersonalWaitClaimTasksMethod(Page page) {
        return financeFlowMethodService.findSelfWaitClaimTaskList(adminSession.getUser().getId(), adminSession.getUser().getCompanyId(), page);
    }

    @RequestMapping(value = "/history", method = RequestMethod.GET)
    @ApiOperation(value = "个人已处理任务列表", notes = "个人已处理任务列表", response = HistoryTaskObject.class, responseContainer = "List")
    @ApiImplicitParam(name = "page", value = "当前页数", required = false, dataType = "int", paramType = "query")
    public Result getPersonalHistoryTasksMethod(Page page) {
        return financeFlowMethodService.findSelfHistoryTaskList(adminSession.getUser().getId(), page);
    }

    @RequestMapping(value = "/{taskId}/claim", method = RequestMethod.POST)
    @ApiOperation(value = "管理员领取任务", notes = "管理员领取任务操作", response = Boolean.class)
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path")
    public Result adminClaimTaskMethod(@PathVariable(value = "taskId") String taskId) {
        return financeFlowMethodService.adminClaimTask(adminSession.getUser().getId(), adminSession.getUser().getCompanyId(), taskId);
    }

    @RequestMapping(value = "/{taskId}/person/{userId}", method = RequestMethod.PUT)
    @ApiOperation(value = "管理员分配任务", notes = "管理员分配任务操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "userId", value = "被分配人userId", required = true, dataType = "String", paramType = "path")
    })
    public Result assignMYROnlineTraderMethod(@PathVariable(value = "taskId") String taskId,
                                              @PathVariable(value = "userId") String userId) {
        return financeFlowMethodService.adminAssignTask(adminSession.getUser().getId(), adminSession.getUser().getCompanyId(), taskId, userId);
    }

}
