package com.yimei.finance.controllers.admin.restfulapi.finance;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by liuxinjie on 16/8/15.
 */


@Api(tags = {"admin-api-flow"}, description = "金融公用接口")
@RequestMapping("/api/financing/admin")
@RestController
public class FinanceHomeController {
//    @Autowired
//    private TaskService taskService;
//    @Autowired
//    private IdentityService identityService;
//    @Autowired
//    private ApplyInfoServiceImpl applyInfoService;
//
//    @RequestMapping(value = "/determine/type/{id}/{applyType}", method = RequestMethod.PUT)
//    @ApiOperation(value = "确定金融申请单类型", notes = "确定金融申请单类型")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "id", value = "金融申请单id", required = true, dataType = "Integer", paramType = "path"),
//            @ApiImplicitParam(name = "applyType", value = "金融申请单类型", required = true, dataType = "String", paramType = "path")
//    })
//    public Result determineFinanceOrderMethod(@PathVariable("id")Integer id, @PathVariable("applyType")String applyType) {
//        ApplyInfo applyInfo = applyInfoService.findOne(id);
//        if (applyInfo == null) return Result.error(EnumFinanceError.此金融单不存在.toString());
//        applyInfoService.updateApplyType(id, applyType);
//        return Result.success().setData(applyInfoService.findOne(id));
//    }
//
//    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.GET)
//    @ApiOperation(value = "查看本人任务列表", notes = "通过 User Id 查看本人任务列表")
//    @ApiImplicitParams({
//        @ApiImplicitParam(name = "id", value = "用户(管理员)id", required = true, dataType = "String", paramType = "path"),
//        @ApiImplicitParam(name = "page", value = "分页类page", required = true, dataType = "Page", paramType = "body")
//    })
//    public Result getTasksByUserId(@PathVariable("id")String id, Page page) {
//        if (identityService.createUserQuery().userId(id).singleResult() == null) return Result.error(EnumAdminUserError.此用户不存在.toString());
//        List<Task> taskList = taskService.createTaskQuery().taskAssignee(id).orderByTaskDueDate().desc().listPage(page.getOffset(), page.getCount());
//        page.setTotal(taskService.createTaskQuery().taskAssignee(id).count());
//        return Result.success().setData(taskList).setMeta(page);
//    }



}
