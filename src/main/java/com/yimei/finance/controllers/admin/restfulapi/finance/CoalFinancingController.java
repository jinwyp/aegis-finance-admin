package com.yimei.finance.controllers.admin.restfulapi.finance;

import com.yimei.finance.repository.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 处理煤易融相关逻辑
 */
@RequestMapping("/api/financing")
@Api(value = "CoalFinancingController", description = "煤易融相关方法")
@RestController
public class CoalFinancingController {
//    @Autowired
//    private EngineServices engineServices;
//    @Autowired
//    private RuntimeService runtimeService;
//    @Autowired
//    private IdentityService identityService;
//    @Autowired
//    private TaskService taskService;

    @RequestMapping(value = "/start", method = RequestMethod.POST)
    @ApiOperation(value="创建用户", notes="根据User对象创建用户")
    @ApiImplicitParam(name = "id", value = "金融申请单id", required = true, dataType = "Long")
    public Result startFinancingWorkFlow(@RequestParam(value = "id", required = true)int id) {
//        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("financingWorkFlow");
//        List<Task> taskList = taskService.createTaskQuery().
//        taskService.addCandidateGroup(engineServices.getTaskService());
        return Result.success();
    }

}
