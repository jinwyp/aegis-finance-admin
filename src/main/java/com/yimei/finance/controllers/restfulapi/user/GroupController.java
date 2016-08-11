package com.yimei.finance.controllers.restfulapi.user;

import com.yimei.finance.repository.common.result.Result;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by liuxinjie on 16/8/11.
 */
@RestController
public class GroupController {
    @Autowired
    private IdentityService identityService;


    /**
     * 添加组
     * @param name                组名
     */
    @RequestMapping(value = "/user/group", method = RequestMethod.POST)
    public Result groupAddMethod(@RequestParam(value = "name", required = true)String name) {
        Group group = identityService.newGroup(String.valueOf(identityService.createGroupQuery().count() + 1));
        group.setName(name);
        identityService.saveGroup(group);
        return Result.success().setData(group);
    }
}
