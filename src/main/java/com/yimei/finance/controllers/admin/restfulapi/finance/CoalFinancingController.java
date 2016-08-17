package com.yimei.finance.controllers.admin.restfulapi.finance;

import com.yimei.finance.config.AdminSession;
import com.yimei.finance.repository.admin.user.EnumGroupId;
import com.yimei.finance.repository.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.IdentityLinkType;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 处理煤易融相关逻辑
 */

@Api(tags = {"admin-api-flow"})
@RequestMapping("/api/financing/admin/myr")
@RestController
public class CoalFinancingController {
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private AdminSession adminSession;




    @ApiOperation(value = "发起流程", notes = "发起流程")
    @ApiImplicitParam(name = "id", value = "金融申请单id", required = true, dataType = "Integer", paramType = "query")
    @RequestMapping(value = "/start", method = RequestMethod.POST)
    public Result startFinancingWorkFlow(@RequestParam(value = "id", required = true) int id) {
        runtimeService.startProcessInstanceByKey("financingWorkFlow", String.valueOf(id));
        Task task =taskService.createTaskQuery().processInstanceBusinessKey(String.valueOf(id)).singleResult();
        taskService.addGroupIdentityLink(task.getId(), EnumGroupId.ManageTraderGroup.id, IdentityLinkType.CANDIDATE);
        return Result.success();
    }



    @ApiOperation(value = "显示交易员列表", notes = "显示交易员列表数据")
    @RequestMapping(value = "/trader", method = RequestMethod.GET)
    public Result assignFinancingOnlineTraderPage() {
        return Result.success().setData(identityService.createUserQuery().memberOfGroup(EnumGroupId.TraderGroup.id));
    }

//    @RequestMapping(value = "/assignTrader", method = RequestMethod.PUT)
//    @ApiOperation(value = "分配线上交易员操作", notes = "分配显示交易员操作")
//    @ApiImplicitParam(name = "id", value = "金融申请单id", required = true, dataType = "String", paramType = "query")
//    public Result assignFinancingOnlineTraderSubmit(@RequestParam(value = "id", required = true) String id) {
//
//    }


}
