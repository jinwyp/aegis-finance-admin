package com.yimei.finance.controllers.admin.restfulapi.user;

import com.yimei.finance.repository.admin.user.EnumAdminUserError;
import com.yimei.finance.repository.common.result.Page;
import com.yimei.finance.repository.common.result.Result;
import com.yimei.finance.service.admin.user.AdminUserServiceImpl;
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

@Api(tags = {"admin-api-user"})
@RequestMapping("/api/financing/admin/users")
@RestController
public class UserController {

    @Autowired
    private IdentityService identityService;
    @Autowired
    private AdminUserServiceImpl userService;

    @ApiOperation(value = "查询所有用户", notes = "查询所有用户列表", response = UserEntity.class, responseContainer = "List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页数", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "count", value = "每页显示数量", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "offset", value = "偏移数", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "total", value = "结果总数量", required = false, dataType = "int", paramType = "query")
    })
    @RequestMapping(method = RequestMethod.GET)
    public Result getAllUsersMethod(Page page) {
        page.setTotal(identityService.createUserQuery().count());
        return Result.success().setData(identityService.createUserQuery().listPage(page.getOffset(), page.getCount())).setMeta(page);
    }


    @ApiOperation(value = "查询单个用户", notes = "根据id查询用户对象", response = UserEntity.class)
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result getUserByIdMethod(@PathVariable("id") String id) {
        User user = identityService.createUserQuery().userId(id).singleResult();
        if (user == null) return Result.error(EnumAdminUserError.此用户不存在.toString());
        return Result.success().setData(user);
    }


    @ApiOperation(value = "查询用户所在的组", notes = "查询某个用户所在的组")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "String", paramType = "path")
    })
    @RequestMapping(value = "{id}/groups", method = RequestMethod.GET)
    public Result getUserGroupsMethod(@PathVariable("id") String id, Page page) {
        page.setTotal(identityService.createGroupQuery().groupMember(id).count());
        return Result.success().setData(identityService.createGroupQuery().groupMember(id).listPage(page.getOffset(), page.getCount())).setMeta(page);
    }



    @ApiOperation(value = "创建用户", notes = "根据User对象创建用户", response = UserEntity.class)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "email", value = "用户邮箱", required = true, dataType = "String", paramType = "form"),
        @ApiImplicitParam(name = "password", value = "用户密码", required = true, dataType = "String", paramType = "form")
    })
    @RequestMapping(method = RequestMethod.POST)
    public Result addUserMethod(@RequestBody UserEntity user) {
        user.setId(null);
        user.setPassword(userService.securePassword(user.getPassword()));
        identityService.saveUser(user);
        return Result.success().setData(identityService.createUserQuery().userId(user.getId()).singleResult());
    }


    @ApiOperation(value = "删除用户", notes = "根据 UserId 删除用户")
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result deleteUserMethod(@PathVariable("id") String id) {
        User user = identityService.createUserQuery().userId(id).singleResult();
        if (user == null) return Result.error(EnumAdminUserError.此用户不存在.toString());
        identityService.deleteUser(id);
        return Result.success().setData(user);
    }


    @ApiOperation(value = "修改用户", notes = "根据 User Id修改用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "email", value = "用户邮箱", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "password", value = "用户密码", required = true, dataType = "String", paramType = "form")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result updateUserMethod(@PathVariable("id") String id, @RequestBody UserEntity user) {
        if (StringUtils.isEmpty(id)) return Result.error(EnumAdminUserError.用户id不能为空.toString());
        if (user == null) return Result.error(EnumAdminUserError.用户对象不能为空.toString());
        User oldUser = identityService.createUserQuery().userId(user.getId()).singleResult();
        if (oldUser == null) return Result.error(EnumAdminUserError.此用户不存在.toString());
        user.setPassword(oldUser.getPassword());
        user.setId(id);
        identityService.deleteUser(user.getId());
        identityService.saveUser(user);
        return Result.success().setData(identityService.createUserQuery().userId(user.getId()).singleResult());
    }


}
