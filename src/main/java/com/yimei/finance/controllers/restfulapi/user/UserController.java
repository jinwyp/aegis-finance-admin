package com.yimei.finance.controllers.restfulapi.user;

import com.yimei.finance.repository.common.result.Page;
import com.yimei.finance.repository.user.EnumUserError;
import com.yimei.finance.repository.common.result.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * Created by liuxinjie on 16/8/10.
 */
@RequestMapping("/api/user")
@RestController
public class UserController {
    @Autowired
    private IdentityService identityService;

    @ApiOperation(value="创建用户", notes="根据User对象创建用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    @RequestMapping(method = RequestMethod.POST)
    public Result addUserMethod(User user) {
        user.setId(null);
        identityService.saveUser(user);
        return Result.success().setData(user);
    }

    /**
     * 删除用户
     * @param id                     用户 id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result deleteUserMethod(@PathVariable("id")String id) {
        User user = identityService.createUserQuery().userId(id).singleResult();
        if (user == null) return Result.error(EnumUserError.此用户不存在.toString());
        identityService.deleteUser(id);
        return Result.success().setData(user);
    }

    /**
     * 修改用户
     * @param user                   用户 对象
     */
    @RequestMapping(method = RequestMethod.PUT)
    public Result updateUserMethod(User user) {
        if (user == null) return Result.error(EnumUserError.用户对象不能为空.toString());
        if (StringUtils.isEmpty(user.getId())) return Result.error(EnumUserError.用户id不能为空.toString());
        if (identityService.createUserQuery().userId(user.getId()).singleResult() == null) return Result.error(EnumUserError.此用户不存在.toString());
        identityService.deleteUser(user.getId());
        identityService.saveUser(user);
        return Result.success().setData(user);
    }

    /**
     * 根据 id 查询用户
     * @param id                     用户 id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result getUserByIdMethod(@PathVariable("id")String id) {
        User user = identityService.createUserQuery().userId(id).singleResult();
        if (user == null) return Result.error(EnumUserError.此用户不存在.toString());
        return Result.success().setData(user);
    }

    /**
     * 查询所有用户
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result getAllUsersMethod(Page meta) {
        meta.setTotal(identityService.createUserQuery().count());
        return Result.success().setData(identityService.createUserQuery().list()).setMeta(meta);
    }

    /**
     * 查询一个 用户 所在的 所有的组
     * @param id                     用户 id
     */
    @RequestMapping(value = "/groups/{id}", method = RequestMethod.GET)
    public Result getUserGroupsMethod(@PathVariable("id")String id, Page meta) {
        meta.setTotal(identityService.createGroupQuery().groupMember(id).count());
        return Result.success().setData(identityService.createGroupQuery().groupMember(id).list()).setMeta(meta);
    }

}