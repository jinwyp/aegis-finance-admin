package com.yimei.finance.controllers.restfulapi;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.apache.ibatis.annotations.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by liuxinjie on 16/8/10.
 */
@RestController
public class WorkFlowGroupController {
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private IdentityService identityService;

    @RequestMapping("/group/add")
    public Result groupAddMethod(@RequestParam(value = "name", required = true)String name) {
//        Group group = identityService.newGroup();
//        group.setName(name);
//        group.setType();
//        //保存Group到数据库
//        identityService.saveGroup(group);
//        // 查询用户组
//        Group data = identityService.createGroupQuery().groupId("1").singleResult();
//        System.out.println(data.getId() + " --- " + data.getName() + " --- " + data.getType());
//        // 设置名称
//        data.setName("经理2组");
//        identityService.saveGroup(data);
        return null;
    }

}
