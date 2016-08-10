package com.yimei.finance.controllers.restfulapi;

import com.yimei.finance.repository.common.result.Result;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by liuxinjie on 16/8/10.
 */
@RestController
public class UserGroupController {
    @Autowired
    private IdentityService identityService;

    /**
     * 添加组
     * @param name                组名称
     */
    @RequestMapping(value = "/user/group", method = RequestMethod.POST)
    public Result groupAddMethod(@RequestParam(value = "name", required = true)String name) {
        Group group = identityService.newGroup(String.valueOf(identityService.createGroupQuery().count() + 1));
        group.setName(name);
        identityService.saveGroup(group);
        return Result.success().setData(group);
    }

    /**
     * 添加用户
     *
     */
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public Result userAddMethod(User user) {
        User user = identityService.newUser();
        // 设置用户的各个属性
        user.setFirstName("Angus");
        user.setLastName("Young");
        user.setEmail("yangenxiong@163.com");
        user.setPassword("abc");
        // 使用saveUser方法保存用户
        identityService.saveUser(user);

        user = identityService.newUser("2");
        user.setFirstName("XINJIE");
        user.setLastName("LIU");
        user.setEmail("1134005157@qq.com");
        user.setPassword("ccc");
        identityService.saveUser(user);
    }

}
