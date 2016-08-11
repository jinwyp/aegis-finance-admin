package com.yimei.finance.controllers.restfulapi.user;

import com.yimei.finance.repository.common.result.Result;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by liuxinjie on 16/8/10.
 */
@RestController
public class UserController {
    @Autowired
    private IdentityService identityService;

    /**
     * 添加用户
     * @param user                   user 对象
     */
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public Result userAddMethod(User user) {
        String userId = String.valueOf(identityService.createUserQuery().count() + 1);
        user.setId(userId);
        identityService.saveUser(user);
        return Result.success().setData(user);
    }

}
