package com.yimei.finance.controllers.admin.restfulapi.user;

import com.yimei.finance.entity.admin.user.EnumAdminUserError;
import com.yimei.finance.entity.admin.user.GroupObject;
import com.yimei.finance.entity.admin.user.UserObject;
import com.yimei.finance.entity.common.result.Page;
import com.yimei.finance.entity.common.result.Result;
import com.yimei.finance.service.admin.user.AdminUserServiceImpl;
import com.yimei.finance.utils.DozerUtils;
import io.swagger.annotations.*;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"admin-api-user"})
@RequestMapping("/api/financing/admin/users")
@RestController
public class UserController {

    @Autowired
    private IdentityService identityService;
    @Autowired
    private AdminUserServiceImpl userService;

    @ApiOperation(value = "查询所有用户", notes = "查询所有用户列表", response = UserObject.class, responseContainer = "List")
    @ApiImplicitParam(name = "page", value = "当前页数", required = false, dataType = "int", paramType = "query")
    @RequestMapping(method = RequestMethod.GET)
    public Result getAllUsersMethod(Page page) {
        page.setTotal(identityService.createUserQuery().count());
        List<UserObject> userObjectList = DozerUtils.copy(identityService.createUserQuery().listPage(page.getOffset(), page.getCount()), UserObject.class);
        return Result.success().setData(userObjectList).setMeta(page);
    }

    @ApiOperation(value = "查询单个用户", notes = "根据id查询用户对象", response = UserObject.class)
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result getUserByIdMethod(@PathVariable("id") String id) {
        User user = identityService.createUserQuery().userId(id).singleResult();
        if (user == null) return Result.error(EnumAdminUserError.此用户不存在.toString());
        UserObject userObject = DozerUtils.copy(user, UserObject.class);
        return Result.success().setData(userObject);
    }

    @ApiOperation(value = "查询用户所在的组", notes = "查询某个用户所在的组", response = GroupObject.class, responseContainer = "List")
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/{id}/groups", method = RequestMethod.GET)
    public Result getUserGroupsMethod(@PathVariable("id") String id, Page page) {
        page.setTotal(identityService.createGroupQuery().groupMember(id).count());
        List<GroupObject> groupObjectList = DozerUtils.copy(identityService.createGroupQuery().groupMember(id).list(), GroupObject.class);
        return Result.success().setData(groupObjectList).setMeta(page);
    }

    @ApiOperation(value = "创建用户", notes = "根据User对象创建用户", response = UserObject.class)
    @RequestMapping(method = RequestMethod.POST)
    public Result addUserMethod(@ApiParam(name = "user", value = "用户对象", required = true)@RequestBody UserObject user) {
        if (StringUtils.isEmpty(user.getEmail())) return Result.error(EnumAdminUserError.用户登录名不能为空.toString());
        if (identityService.createUserQuery().userEmail(user.getEmail()).singleResult() != null) return Result.error(EnumAdminUserError.此登录名已经存在.toString());
        user.setId(null);
        user.setPassword(userService.securePassword(user.getPassword()));
        UserEntity userEntity = DozerUtils.copy(user, UserEntity.class);
        identityService.saveUser(userEntity);
        return Result.success().setData(DozerUtils.copy(identityService.createUserQuery().userId(userEntity.getId()).singleResult(), UserObject.class));
    }

    @ApiOperation(value = "删除用户", notes = "根据 UserId 删除用户")
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result deleteUserMethod(@PathVariable("id") String id) {
        User user = identityService.createUserQuery().userId(id).singleResult();
        if (user == null) return Result.error(EnumAdminUserError.此用户不存在.toString());
        UserObject userObject = DozerUtils.copy(user, UserObject.class);
        identityService.deleteUser(id);
        return Result.success().setData(userObject);
    }


    @ApiOperation(value = "修改用户", notes = "根据 User Id修改用户", response = UserObject.class)
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result updateUserMethod(@PathVariable("id") String id,
                                   @ApiParam(name = "user", value = "用户对象", required = true)@RequestBody UserObject user) {
        if (StringUtils.isEmpty(id)) return Result.error(EnumAdminUserError.用户id不能为空.toString());
        if (user == null) return Result.error(EnumAdminUserError.用户对象不能为空.toString());
        User oldUser = identityService.createUserQuery().userId(id).singleResult();
        if (oldUser == null) return Result.error(EnumAdminUserError.此用户不存在.toString());
        oldUser.setFirstName(user.getFirstName());
        oldUser.setLastName(user.getLastName());
        oldUser.setEmail(user.getEmail());
        identityService.saveUser(oldUser);
        return Result.success().setData(DozerUtils.copy(identityService.createUserQuery().userId(id).singleResult(), UserObject.class));
    }


}
