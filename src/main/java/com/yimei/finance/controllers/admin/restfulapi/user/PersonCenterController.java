package com.yimei.finance.controllers.admin.restfulapi.user;

import com.yimei.finance.config.session.AdminSession;
import com.yimei.finance.entity.admin.finance.TaskObject;
import com.yimei.finance.entity.common.result.Page;
import com.yimei.finance.entity.common.result.Result;
import com.yimei.finance.utils.DozerUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/api/financing/admin")
@RestController("adminPersonCenterController")
public class PersonCenterController {
    @Autowired
    private IdentityService identityService;
    @Autowired
    private AdminSession adminSession;
    @Autowired
    private TaskService taskService;

    @RequestMapping(value = "/user/tasks", method = RequestMethod.GET)
    @ApiOperation(value = "查看个人任务列表", notes = "查看个人任务列表", response = TaskObject.class, responseContainer = "List")
    @ApiImplicitParam(name = "page", value = "当前页数", required = false, dataType = "Integer", paramType = "query")
    public Result getPersonalTasksMethod(Page page) {
        List<TaskObject> taskList = DozerUtils.copy(taskService.createTaskQuery().taskAssignee(adminSession.getUser().getId()).active().orderByTaskDueDate().desc().listPage(page.getOffset(), page.getCount()), TaskObject.class);
        page.setTotal(taskService.createTaskQuery().taskAssignee(adminSession.getUser().getId()).count());
        return Result.success().setData(taskList).setMeta(page);
    }

    @RequestMapping(value = "/user/unclaimed/tasks", method = RequestMethod.GET)
    @ApiOperation(value = "查看个人待签收任务列表", notes = "查看个人待签收任务列表")
    @ApiImplicitParam(name = "page", value = "当前页数", required = false, dataType = "Integer", paramType = "query")
    public Result getPersonalWaitClaimTasksMethod(Page page) {
        List<Group> groupList = identityService.createGroupQuery().groupMember(adminSession.getUser().getId()).list();
        List<String> groupIds = new ArrayList<>();
        for (Group group : groupList) {
            groupIds.add(group.getId());
        }
        if (groupIds != null && groupIds.size() != 0) {
            page.setTotal(taskService.createTaskQuery().taskCandidateGroupIn(groupIds).active().count());
            List<TaskObject> taskList = DozerUtils.copy(taskService.createTaskQuery().taskCandidateGroupIn(groupIds).active().orderByTaskDueDate().desc().listPage(page.getOffset(), page.getCount()), TaskObject.class);
            return Result.success().setData(taskList).setMeta(page);
        }
        return Result.success().setData(null).setMeta(page);
    }

}
