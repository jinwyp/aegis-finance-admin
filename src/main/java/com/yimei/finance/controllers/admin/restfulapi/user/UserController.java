package com.yimei.finance.controllers.admin.restfulapi.user;

import com.yimei.finance.config.session.AdminSession;
import com.yimei.finance.entity.admin.user.*;
import com.yimei.finance.entity.common.enums.EnumCommonString;
import com.yimei.finance.entity.common.result.Page;
import com.yimei.finance.entity.common.result.Result;
import com.yimei.finance.service.admin.user.AdminUserServiceImpl;
import com.yimei.finance.utils.DozerUtils;
import io.swagger.annotations.*;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"admin-api-user"}, description = "用户增删改查接口")
@RequestMapping("/api/financing/admin/user")
@RestController("adminUserController")
public class UserController {
    @Autowired
    private IdentityService identityService;
    @Autowired
    private AdminUserServiceImpl userService;
    @Autowired
    private AdminSession adminSession;

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "查询所有用户", notes = "查询所有用户列表", response = UserObject.class, responseContainer = "List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页数", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "count", value = "每页显示数量", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "offset", value = "偏移数", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "total", value = "结果总数量", required = false, dataType = "int", paramType = "query")
    })
    public Result getAllUsersMethod(Page page) {
        page.setTotal(identityService.createUserQuery().count());
        List<UserObject> userObjectList = userService.changeUserObject(identityService.createUserQuery().listPage(page.getOffset(), page.getCount()));
        return Result.success().setData(userObjectList).setMeta(page);
    }


    @ApiOperation(value = "查询单个用户", notes = "根据id查询用户对象", response = UserObject.class)
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result getUserByIdMethod(@PathVariable("id") String id) {
        User user = identityService.createUserQuery().userId(id).singleResult();
        if (user == null) return Result.error(EnumAdminUserError.此用户不存在.toString());
        UserObject userObject = userService.changeUserObject(user);
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
        if (!checkRight()) return Result.error(EnumAdminUserError.只有超级管理员组成员才能执行此操作.toString());
        if (StringUtils.isEmpty(user.getUsername())) return Result.error(EnumAdminUserError.用户登录名不能为空.toString());
        if (identityService.createUserQuery().userFirstName(user.getUsername()).singleResult() != null) return Result.error(EnumAdminUserError.此登录名已经存在.toString());
        User newUser = identityService.newUser("");
        DozerUtils.copy(user, newUser);
        newUser.setId(null);
        newUser.setFirstName(user.getUsername());
        newUser.setPassword(userService.securePassword(EnumCommonString.AdminUser_InitPwd.name));
        newUser.setEmail(user.getEmail());
        identityService.saveUser(newUser);
        identityService.setUserInfo(newUser.getId(), "username", user.getUsername());
        identityService.setUserInfo(newUser.getId(), "name", user.getName());
        identityService.setUserInfo(newUser.getId(), "phone", user.getPhone());
        identityService.setUserInfo(newUser.getId(), "department", user.getDepartment());
        Result result = addUserGroupMemberShip(newUser.getId(), user.getGroupIds());
        if (!result.isSuccess()) return result;
        return Result.success().setData(userService.changeUserObject(identityService.createUserQuery().userId(newUser.getId()).singleResult()));
    }

    @ApiOperation(value = "删除用户", notes = "根据 UserId 删除用户")
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result deleteUserMethod(@PathVariable("id") String id) {
        if (!checkRight()) return Result.error(EnumAdminUserError.只有超级管理员组成员才能执行此操作.toString());
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
        if (!checkRight()) return Result.error(EnumAdminUserError.只有超级管理员组成员才能执行此操作.toString());
        if (StringUtils.isEmpty(id)) return Result.error(EnumAdminUserError.用户id不能为空.toString());
        if (user == null) return Result.error(EnumAdminUserError.用户对象不能为空.toString());
        User oldUser = identityService.createUserQuery().userId(id).singleResult();
        if (oldUser == null) return Result.error(EnumAdminUserError.此用户不存在.toString());
        oldUser.setEmail(user.getEmail());
        identityService.saveUser(oldUser);
        identityService.setUserInfo(oldUser.getId(), "name", user.getName());
        identityService.setUserInfo(oldUser.getId(), "phone", user.getPhone());
        identityService.setUserInfo(oldUser.getId(), "department", user.getDepartment());
        Result result = addUserGroupMemberShip(oldUser.getId(), user.getGroupIds());
        if (!result.isSuccess()) return result;
        return Result.success().setData(userService.changeUserObject(identityService.createUserQuery().userId(id).singleResult()));
    }

    /**
     * 检查是否具有超级管理员权限
     */
    public boolean checkRight() {
        List<Group> groups = identityService.createGroupQuery().groupMember(adminSession.getUser().getId()).list();
        for (Group group : groups) {
            if (group.getId().equals(EnumSpecialGroup.SuperAdminGroup.id)) {
                return true;
            }
        }
        return false;
    }

    public Result addUserGroupMemberShip(String userId, String[] groupIds) {
        List<Group> groupList = identityService.createGroupQuery().groupMember(userId).list();
        if (groupList != null && groupList.size() != 0) {
            for (Group group : groupList) {
                identityService.deleteMembership(userId, group.getId());
            }
        }
        if (groupIds != null && groupIds.length != 0) {
            for (String gid : groupIds) {
                if (identityService.createGroupQuery().groupId(gid).singleResult() == null)
                    return Result.error(EnumAdminGroupError.此组不存在.toString());
                identityService.createMembership(userId, gid);
            }
        }
        return Result.success();
    }

}
