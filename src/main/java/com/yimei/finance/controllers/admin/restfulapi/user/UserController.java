package com.yimei.finance.controllers.admin.restfulapi.user;

import com.yimei.finance.repository.common.result.Page;
import com.yimei.finance.repository.common.result.Result;
import com.yimei.finance.repository.user.EnumUserError;
import com.yimei.finance.repository.user.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/financing/admin/user")
@Api(value = "User-Controller", description = "用户相关的方法")
@RestController
public class UserController {
    @Autowired
    private IdentityService identityService;
    @Autowired
    private UserServiceImpl userService;

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "创建用户", notes = "根据User对象创建用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "UserEntity", paramType = "body")
    public Result addUserMethod(@RequestBody UserEntity user) {
        user.setId(null);
        user.setPassword(userService.securePassword(user.getPassword()));
        identityService.saveUser(user);
        return Result.success().setData(user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除用户", notes = "通过id删除用户")
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "String", paramType = "path")
    public Result deleteUserMethod(@PathVariable("id") String id) {
        User user = identityService.createUserQuery().userId(id).singleResult();
        if (user == null) return Result.error(EnumUserError.此用户不存在.toString());
        identityService.deleteUser(id);
        return Result.success().setData(user);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ApiOperation(value = "创建用户", notes = "根据User对象创建用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "UserEntity", paramType = "body")
    public Result updateUserMethod(@RequestBody UserEntity user) {
        if (user == null) return Result.error(EnumUserError.用户对象不能为空.toString());
        if (StringUtils.isEmpty(user.getId())) return Result.error(EnumUserError.用户id不能为空.toString());
        User oldUser = identityService.createUserQuery().userId(user.getId()).singleResult();
        if (oldUser == null) return Result.error(EnumUserError.此用户不存在.toString());
        user.setPassword(oldUser.getPassword());
        identityService.deleteUser(user.getId());
        identityService.saveUser(user);
        return Result.success().setData(user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "查询单个用户", notes = "根据id查询用户对象")
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "String", paramType = "path")
    public Result getUserByIdMethod(@PathVariable("id") String id) {
        User user = identityService.createUserQuery().userId(id).singleResult();
        if (user == null) return Result.error(EnumUserError.此用户不存在.toString());
        return Result.success().setData(user);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "查询所有用户", notes = "查询所有用户")
    @ApiImplicitParam(name = "page", value = "分页类page", required = false, dataType = "Page", paramType = "body")
    public Result getAllUsersMethod(Page page) {
        page.setTotal(identityService.createUserQuery().count());
        return Result.success().setData(identityService.createUserQuery().listPage(page.getOffset(), page.getCount())).setMeta(page);
    }

    @RequestMapping(value = "/groups/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "查询一个用户所在的组", notes = "查询一个用户所在的组")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", value = "分页类page", required = false, dataType = "Page", paramType = "body"),
        @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "String", paramType = "path")

    })
    public Result getUserGroupsMethod(@PathVariable("id") String id, Page page) {
        page.setTotal(identityService.createGroupQuery().groupMember(id).count());
        return Result.success().setData(identityService.createGroupQuery().groupMember(id).listPage(page.getOffset(), page.getCount())).setMeta(page);
    }

}
