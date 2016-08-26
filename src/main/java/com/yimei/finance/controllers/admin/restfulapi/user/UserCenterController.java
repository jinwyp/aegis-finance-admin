package com.yimei.finance.controllers.admin.restfulapi.user;

import com.yimei.finance.config.session.AdminSession;
import com.yimei.finance.entity.admin.finance.HistoryTaskObject;
import com.yimei.finance.entity.admin.finance.TaskObject;
import com.yimei.finance.entity.common.result.Page;
import com.yimei.finance.entity.common.result.Result;
import com.yimei.finance.service.admin.workflow.WorkFlowServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.Group;
import org.springframework.beans.factory.annotation.Autowired;
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
    private IdentityService identityService;
    @Autowired
    private AdminSession adminSession;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private WorkFlowServiceImpl workFlowService;

    @RequestMapping( method = RequestMethod.GET)
    @ApiOperation(value = "个人待办任务列表", notes = "个人待办任务列表", response = TaskObject.class, responseContainer = "List")
    @ApiImplicitParam(name = "page", value = "当前页数", required = false, dataType = "int", paramType = "query")
    public Result getPersonalTasksMethod(Page page) {
        Result result = workFlowService.changeTaskObject(taskService.createTaskQuery().taskAssignee(adminSession.getUser().getId()).active().orderByTaskCreateTime().desc().listPage(page.getOffset(), page.getCount()));
        if (!result.isSuccess()) return result;
        List<TaskObject> taskList = (List<TaskObject>) result.getData();
        page.setTotal(taskService.createTaskQuery().taskAssignee(adminSession.getUser().getId()).count());
        return Result.success().setData(taskList).setMeta(page);
    }


    @RequestMapping(value = "/unclaimed", method = RequestMethod.GET)
    @ApiOperation(value = "给分配管理员查看待领取任务列表", notes = "给线上交易员管理组, 业务员管理组, 尽调员管理组, 监管员管理组, 风控管理组, 查看待领取任务列表", response = TaskObject.class, responseContainer = "List")
    @ApiImplicitParam(name = "page", value = "当前页数", required = false, dataType = "int", paramType = "query")
    public Result getPersonalWaitClaimTasksMethod(Page page) {
        List<Group> groupList = identityService.createGroupQuery().groupMember(adminSession.getUser().getId()).list();
        List<String> groupIds = new ArrayList<>();
        for (Group group : groupList) {
            groupIds.add(group.getId());
        }
        if (groupIds != null && groupIds.size() != 0) {
            page.setTotal(taskService.createTaskQuery().taskCandidateGroupIn(groupIds).active().count());
            Result result = workFlowService.changeTaskObject(taskService.createTaskQuery().taskCandidateGroupIn(groupIds).active().orderByTaskCreateTime().desc().listPage(page.getOffset(), page.getCount()));
            if (!result.isSuccess()) return result;
            List<TaskObject> taskList = (List<TaskObject>) result.getData();
            return Result.success().setData(taskList).setMeta(page);
        }
        return Result.success().setData(null).setMeta(page);
    }

    @RequestMapping(value = "/history", method = RequestMethod.GET)
    @ApiOperation(value = "个人已处理任务列表", notes = "个人已处理任务列表", response = HistoryTaskObject.class, responseContainer = "List")
    @ApiImplicitParam(name = "page", value = "当前页数", required = false, dataType = "int", paramType = "query")
    public Result getPersonalHistoryTasksMethod(Page page) {
        Result result = workFlowService.changeHistoryTaskObject(historyService.createHistoricTaskInstanceQuery().taskAssignee(adminSession.getUser().getId()).finished().orderByTaskCreateTime().desc().listPage(page.getOffset(), page.getCount()));
        if (!result.isSuccess()) return result;
        List<HistoryTaskObject> taskList = (List<HistoryTaskObject>) result.getData();
        return Result.success().setData(taskList);
    }


}
