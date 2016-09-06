package com.yimei.finance.controllers.admin.restfulapi.user;

import com.yimei.finance.config.session.AdminSession;
import com.yimei.finance.entity.admin.finance.HistoryTaskObject;
import com.yimei.finance.entity.admin.finance.TaskObject;
import com.yimei.finance.representation.admin.finance.EnumAdminFinanceError;
import com.yimei.finance.representation.common.result.Page;
import com.yimei.finance.representation.common.result.Result;
import com.yimei.finance.service.admin.finance.FinanceFlowMethodServiceImpl;
import com.yimei.finance.service.admin.user.AdminUserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = {"admin-api-flow"}, description = "金融公用接口")
@RequestMapping("/api/financing/admin/tasks")
@RestController("adminUserCenterController")
public class UserCenterController {
    @Autowired
    private IdentityService identityService;
    @Autowired
    private AdminSession adminSession;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private FinanceFlowMethodServiceImpl workFlowService;
    @Autowired
    private AdminUserServiceImpl userService;

    @RequestMapping(value = "/{taskId}", method = RequestMethod.GET)
    @ApiOperation(value = "通过 id 查询任务对象", notes = "通过 id 查询任务对象", response = TaskObject.class)
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path")
    public Result getTaskByIdMethod(@PathVariable(value = "taskId") String taskId) {
        HistoricTaskInstance taskInstance = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
        if (taskInstance == null) return Result.error(EnumAdminFinanceError.不存在此任务.toString());
        return workFlowService.changeHistoryTaskObject(taskInstance);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "个人待办任务列表", notes = "个人待办任务列表", response = TaskObject.class, responseContainer = "List")
    @ApiImplicitParam(name = "page", value = "当前页数", required = false, dataType = "int", paramType = "query")
    public Result getPersonalTasksMethod(Page page) {
        List<Task> taskList = taskService.createTaskQuery().taskAssignee(adminSession.getUser().getId()).active().orderByProcessInstanceId().desc().orderByTaskCreateTime().desc().list();
        int toIndex = page.getPage() * page.getCount() < taskList.size() ? page.getPage() * page.getCount() : taskList.size();
        Result result = workFlowService.changeTaskObject(taskList.subList(page.getOffset(), toIndex));
        if (!result.isSuccess()) return result;
        List<TaskObject> taskObjectList = (List<TaskObject>) result.getData();
        page.setTotal((long) taskList.size());
        return Result.success().setData(taskObjectList).setMeta(page);
    }

    @RequestMapping(value = "/unclaimed", method = RequestMethod.GET)
    @ApiOperation(value = "给分配管理员查看待领取任务列表", notes = "给线上交易员管理组, 业务员管理组, 尽调员管理组, 监管员管理组, 风控管理组, 查看待领取任务列表", response = TaskObject.class, responseContainer = "List")
    @ApiImplicitParam(name = "page", value = "当前页数", required = false, dataType = "int", paramType = "query")
    public Result getPersonalWaitClaimTasksMethod(Page page) {
        List<String> groupIds = userService.getUserGroupIdList(adminSession.getUser().getId());
        if (groupIds != null && groupIds.size() != 0) {
            List<Task> taskList = taskService.createTaskQuery().taskCandidateGroupIn(groupIds).active().orderByProcessInstanceId().desc().orderByTaskCreateTime().desc().list();
            page.setTotal(Long.valueOf(taskList.size()));
            int toIndex = page.getPage() * page.getCount() < taskList.size() ? page.getPage() * page.getCount() : taskList.size();
            Result result = workFlowService.changeTaskObject(taskList.subList(page.getOffset(), toIndex));
            if (!result.isSuccess()) return result;
            List<TaskObject> taskObjectList = (List<TaskObject>) result.getData();
            return Result.success().setData(taskObjectList).setMeta(page);
        }
        return Result.success().setData(null).setMeta(page);
    }

    @RequestMapping(value = "/history", method = RequestMethod.GET)
    @ApiOperation(value = "个人已处理任务列表", notes = "个人已处理任务列表", response = HistoryTaskObject.class, responseContainer = "List")
    @ApiImplicitParam(name = "page", value = "当前页数", required = false, dataType = "int", paramType = "query")
    public Result getPersonalHistoryTasksMethod(Page page) {
        List<HistoricTaskInstance> historicTaskInstanceList = historyService.createHistoricTaskInstanceQuery().taskAssignee(adminSession.getUser().getId()).finished().orderByProcessInstanceId().desc().orderByTaskCreateTime().desc().list();
        int toIndex = page.getPage() * page.getCount() < historicTaskInstanceList.size() ? page.getPage() * page.getCount() : historicTaskInstanceList.size();
        Result result = workFlowService.changeHistoryTaskObject(historicTaskInstanceList.subList(page.getOffset(), toIndex));
        if (!result.isSuccess()) return result;
        List<HistoryTaskObject> taskList = (List<HistoryTaskObject>) result.getData();
        page.setTotal(Long.valueOf(historicTaskInstanceList.size()));
        return Result.success().setData(taskList);
    }


}
