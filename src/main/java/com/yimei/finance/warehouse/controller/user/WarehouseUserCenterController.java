package com.yimei.finance.warehouse.controller.user;

import com.yimei.finance.admin.representation.activiti.HistoryTaskObject;
import com.yimei.finance.admin.representation.activiti.TaskObject;
import com.yimei.finance.admin.service.finance.FinanceFlowMethodServiceImpl;
import com.yimei.finance.common.representation.result.Page;
import com.yimei.finance.common.representation.result.Result;
import com.yimei.finance.config.session.AdminSession;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"warehouse-admin-api-user"})
@RequestMapping("/api/warehouse/admin/tasks")
@RestController("warehouseAdminUserCenterController")
public class WarehouseUserCenterController {
    @Autowired
    private AdminSession adminSession;
    @Autowired
    private FinanceFlowMethodServiceImpl financeFlowMethodService;

    @RequestMapping(value = "/{taskId}", method = RequestMethod.GET)
    @ApiOperation(value = "通过 id 查询任务对象", notes = "通过 id 查询任务对象", response = TaskObject.class)
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "string", paramType = "path")
    public Result getTaskByIdMethod(@PathVariable(value = "taskId") String taskId) {
        return financeFlowMethodService.findTaskByTaskId(taskId, adminSession.getUser().getCompanyId());
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "个人待办任务列表", notes = "个人待办任务列表", response = TaskObject.class, responseContainer = "List")
    @ApiImplicitParam(name = "page", value = "当前页数", required = false, dataType = "int", paramType = "query")
    public Result getPersonalTasksMethod(@ApiParam(name = "page", value = "分页参数", required = false) Page page) {
        return financeFlowMethodService.findSelfTaskList(adminSession.getUser().getId(), page);
    }

    @RequestMapping(value = "/unclaimed", method = RequestMethod.GET)
    @ApiOperation(value = "给分配管理员查看待领取任务列表", notes = "给线上交易员管理组, 业务员管理组, 尽调员管理组, 监管员管理组, 风控管理组, 查看待领取任务列表", response = TaskObject.class, responseContainer = "List")
    @ApiImplicitParam(name = "page", value = "当前页数", required = false, dataType = "int", paramType = "query")
    public Result getPersonalWaitClaimTasksMethod(@ApiParam(name = "page", value = "分页参数", required = false) Page page) {
        return financeFlowMethodService.findSelfWaitClaimTaskList(adminSession.getUser().getId(), adminSession.getUser().getCompanyId(), page);
    }

    @RequestMapping(value = "/history", method = RequestMethod.GET)
    @ApiOperation(value = "个人已处理任务列表", notes = "个人已处理任务列表", response = HistoryTaskObject.class, responseContainer = "List")
    @ApiImplicitParam(name = "page", value = "当前页数", required = false, dataType = "int", paramType = "query")
    public Result getPersonalHistoryTasksMethod(@ApiParam(name = "page", value = "分页参数", required = false) Page page) {
        return financeFlowMethodService.findSelfHistoryTaskList(adminSession.getUser().getId(), page);
    }

    @RequestMapping(value = "/{taskId}/claim", method = RequestMethod.POST)
    @ApiOperation(value = "管理员领取任务", notes = "管理员领取任务操作", response = Boolean.class)
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "string", paramType = "path")
    public Result adminClaimTaskMethod(@PathVariable(value = "taskId") String taskId) {
        return financeFlowMethodService.adminClaimTask(adminSession.getUser().getId(), adminSession.getUser().getCompanyId(), taskId);
    }

    @RequestMapping(value = "/{taskId}/person/{userId}", method = RequestMethod.PUT)
    @ApiOperation(value = "管理员分配任务", notes = "管理员分配任务操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "userId", value = "被分配人userId", required = true, dataType = "string", paramType = "path")
    })
    public Result assignMYROnlineTraderMethod(@PathVariable(value = "taskId") String taskId,
                                              @PathVariable(value = "userId") String userId) {
        return financeFlowMethodService.adminAssignTask(adminSession.getUser().getId(), adminSession.getUser().getCompanyId(), taskId, userId);
    }

}
