package com.yimei.finance.controllers.admin.restfulapi.user;

import com.yimei.finance.config.session.AdminSession;
import com.yimei.finance.representation.admin.group.GroupObject;
import com.yimei.finance.representation.admin.user.object.AdminUserSearch;
import com.yimei.finance.representation.admin.user.object.UserObject;
import com.yimei.finance.representation.admin.user.object.UserPasswordObject;
import com.yimei.finance.representation.admin.user.object.validated.CreateUser;
import com.yimei.finance.representation.admin.user.object.validated.EditUser;
import com.yimei.finance.representation.common.result.Page;
import com.yimei.finance.representation.common.result.Result;
import com.yimei.finance.service.admin.user.AdminUserServiceImpl;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"admin-api-user"}, description = "用户增删改查接口")
@RequestMapping("/api/financing/admin/users")
@RestController("adminUserController")
public class UserController {
    @Autowired
    private AdminUserServiceImpl userService;
    @Autowired
    private AdminSession adminSession;

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "查询所有用户", notes = "查询所有用户列表", response = UserObject.class, responseContainer = "List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户账号", required = false, defaultValue = "", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "用户姓名", required = false, defaultValue = "", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "groupName", value = "组名", required = false, defaultValue = "", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "当前页数", required = false, defaultValue = "1", dataType = "int", paramType = "query")
    })
    public Result getAllUsersMethod(AdminUserSearch userSearch, Page page) {
        return userService.getUserListBySelect(adminSession.getUser(), userSearch, page);
    }

    @ApiOperation(value = "查询单个用户", notes = "根据id查询用户对象", response = UserObject.class)
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result getUserByIdMethod(@PathVariable("id") String id) {
        return userService.findById(id, adminSession.getUser());
    }

    @ApiOperation(value = "查询用户所在的组", notes = "查询某个用户所在的组", response = GroupObject.class, responseContainer = "List")
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/{id}/groups", method = RequestMethod.GET)
    public Result getUserGroupsMethod(@PathVariable("id") String id, Page page) {
        return userService.findUserGroupList(id, adminSession.getUser(), page);
    }

    @ApiOperation(value = "查询当前用户有权限操作的组列表", notes = "查询当前用户有权限操作的组列表", response = GroupObject.class, responseContainer = "List")
    @ApiImplicitParam(name = "page", value = "当前页数", required = false, dataType = "int", paramType = "query")
    @RequestMapping(value = "/self/groups", method = RequestMethod.GET)
    public Result getHaveRightGroupListMethod(Page page) {
        return userService.findHaveRightGroupList(adminSession.getUser().getId(), page);
    }

    @RequestMapping(value = "/departments", method = RequestMethod.GET)
    @ApiOperation(value = "获取所有部门列表", notes = "获取所有部门列表", response = String.class, responseContainer = "List")
    public Result findAllDepartmentListMethod() {
        return userService.findAllDepartmentList();
    }

    @ApiOperation(value = "创建用户", notes = "根据User对象创建用户", response = UserObject.class)
    @RequestMapping(method = RequestMethod.POST)
    @Transactional
    public Result addUserMethod(@ApiParam(name = "user", value = "用户对象", required = true) @Validated(value = {CreateUser.class}) @RequestBody UserObject user) {
        return userService.addUser(user, adminSession.getUser());
    }

    @ApiOperation(value = "删除用户", notes = "根据 UserId 删除用户", response = UserObject.class)
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result deleteUserMethod(@PathVariable("id") String id) {
        return userService.deleteById(id, adminSession.getUser());
    }

    @ApiOperation(value = "修改用户信息", notes = "根据 User Id修改用户", response = UserObject.class)
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @Transactional
    public Result updateUserInfoMethod(@PathVariable("id") String id,
                                       @ApiParam(name = "user", value = "用户对象", required = true) @Validated(value = {EditUser.class}) @RequestBody UserObject user) {
        return userService.updateUser(id, user, adminSession.getUser());
    }

    @ApiOperation(value = "用户自己修改信息", notes = "用户自己修改信息", response = UserObject.class)
    @RequestMapping(value = "/self", method = RequestMethod.PUT)
    public Result updateUserSelfInfoMethod(@ApiParam(name = "user", value = "用户对象", required = true) @Validated(value = {EditUser.class}) @RequestBody UserObject user) {
        Result result = userService.updateSelfInfo(adminSession.getUser().getId(), user);
        if (!result.isSuccess()) return result;
        adminSession.login((UserObject) result.getData());
        return result;
    }

    @ApiOperation(value = "管理员帮助用户重置密码", notes = "管理员帮助用户重置密码, 生成随机密码, 发送到用户邮箱.", response = Boolean.class)
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/{id}/password", method = RequestMethod.POST)
    public Result resetUserPasswordMethod(@PathVariable("id") String id) {
        return userService.adminResetUserPassword(id, adminSession.getUser());
    }

    @ApiOperation(value = "用户自己修改密码", notes = "用户自己修改密码", response = Boolean.class)
    @RequestMapping(value = "/self/password", method = RequestMethod.PUT)
    public Result resetUserPasswordMethod(@ApiParam(name = "user", value = "用户密码") @Validated @RequestBody UserPasswordObject userObject) {
        return userService.updateSelfPassword(adminSession.getUser().getId(), userObject);
    }


}
