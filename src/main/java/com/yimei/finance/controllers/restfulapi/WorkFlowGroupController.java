package com.yimei.finance.controllers.restfulapi;

import com.yimei.finance.repository.common.Result;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.identity.Group;
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
        Group group = identityService.newGroup(String.valueOf(identityService.createGroupQuery().count() + 1));
        group.setName(name);
        identityService.saveGroup(group);
        return null;
    }

}
