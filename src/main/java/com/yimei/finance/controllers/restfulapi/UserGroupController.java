package com.yimei.finance.controllers.restfulapi;

import org.springframework.web.bind.annotation.RestController;

/**
 * Created by liuxinjie on 16/8/10.
 */
@RestController
public class UserGroupController {
//    @Autowired
//    private IdentityService identityService;

//    /**
//     * 添加组
//     * @param name                组名称
//     */
//    @RequestMapping(value = "/user/group", method = RequestMethod.POST)
//    public Result groupAddMethod(@RequestParam(value = "name", required = true)String name) {
//        Group group = identityService.newGroup(String.valueOf(identityService.createGroupQuery().count() + 1));
//        group.setName(name);
//        identityService.saveGroup(group);
//        return Result.success().setData(group);
//    }
//
//    /**
//     * 添加用户
//     *
//     */
//    @RequestMapping(value = "/user", method = RequestMethod.POST)
//    public Result userAddMethod(User user) {
//        String userId = String.valueOf(identityService.createUserQuery().count() + 1);
//        user.setId(userId);
//        identityService.saveUser(user);
//        return Result.success().setData(user);
//    }

}
