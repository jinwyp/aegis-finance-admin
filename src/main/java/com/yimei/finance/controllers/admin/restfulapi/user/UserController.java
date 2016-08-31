package com.yimei.finance.controllers.admin.restfulapi.user;

import com.yimei.finance.config.session.AdminSession;
import com.yimei.finance.entity.admin.user.GroupObject;
import com.yimei.finance.entity.admin.user.UserObject;
import com.yimei.finance.exception.BusinessException;
import com.yimei.finance.repository.admin.databook.DataBookRepository;
import com.yimei.finance.representation.admin.user.AdminUserSearch;
import com.yimei.finance.representation.admin.user.EnumAdminGroupError;
import com.yimei.finance.representation.admin.user.EnumAdminUserError;
import com.yimei.finance.representation.admin.user.UserPasswordObject;
import com.yimei.finance.representation.common.databook.EnumDataBookType;
import com.yimei.finance.representation.common.enums.EnumCommonString;
import com.yimei.finance.representation.common.result.Page;
import com.yimei.finance.representation.common.result.Result;
import com.yimei.finance.service.admin.user.AdminGroupServiceImpl;
import com.yimei.finance.service.admin.user.AdminUserServiceImpl;
import com.yimei.finance.service.common.message.MailServiceImpl;
import com.yimei.finance.utils.CodeUtils;
import com.yimei.finance.utils.DozerUtils;
import io.swagger.annotations.*;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = {"admin-api-user"}, description = "用户增删改查接口")
@RequestMapping("/api/financing/admin/users")
@RestController("adminUserController")
public class UserController {
    @Autowired
    private DataBookRepository dataBookRepository;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private AdminUserServiceImpl userService;
    @Autowired
    private AdminGroupServiceImpl groupService;
    @Autowired
    private MailServiceImpl mailService;
    @Autowired
    private AdminSession adminSession;

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "查询所有用户", notes = "查询所有用户列表", response = UserObject.class, responseContainer = "List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户账号", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "用户姓名", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "groupName", value = "组名", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "当前页数", required = false, dataType = "int", paramType = "query")
    })
    public Result getAllUsersMethod(AdminUserSearch userSearch, Page page) {
        return userService.getUserListBySelect(userSearch, page);
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
        List<GroupObject> groupObjectList = groupService.changeGroupObject(identityService.createGroupQuery().groupMember(id).list());
        return Result.success().setData(groupObjectList).setMeta(page);
    }

    @ApiOperation(value = "查询当前用户有权限添加用户的组列表", notes = "查询当前用户有权限添加用户的组列表", response = GroupObject.class, responseContainer = "List")
    @RequestMapping(value = "/haveright", method = RequestMethod.GET)
    public Result getHaveRightGroupListMethod() {
        return Result.success().setData(userService.getCanAddUserGroupList(adminSession.getUser().getId()));
    }

    @RequestMapping(value = "/departments", method = RequestMethod.GET)
    @ApiOperation(value = "获取所有部门列表", notes = "获取所有部门列表", response = String.class, responseContainer = "List")
    public Result findAllDepartmentListMethod() {
        return Result.success().setData(dataBookRepository.findByType(EnumDataBookType.financedepartment.toString()));
    }

    @Transactional
    @ApiOperation(value = "创建用户", notes = "根据User对象创建用户", response = UserObject.class)
    @RequestMapping(method = RequestMethod.POST)
    public Result addUserMethod(@ApiParam(name = "user", value = "用户对象", required = true)@RequestBody UserObject user) {
        Result result = userService.checkAddUserToGroupAuthority(adminSession.getUser().getId(), user.getGroupIds());
        if (!result.isSuccess()) return result;
        if (StringUtils.isEmpty(user.getUsername())) return Result.error(EnumAdminUserError.用户登录名不能为空.toString());
        if (identityService.createUserQuery().userFirstName(user.getUsername()).singleResult() != null) return Result.error(EnumAdminUserError.此登录名已经存在.toString());
        if (identityService.createUserQuery().userEmail(user.getEmail()).singleResult() != null) return Result.error(EnumAdminUserError.此邮箱已经存在.toString());
        Result result1 = userService.checkUserPhone(user.getPhone());
        if (!result1.isSuccess()) return result1;
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
        addUserGroupMemberShip(newUser.getId(), user.getGroupIds());
        String subject = "开通账户通知邮件";
        String content = "你好: 你的账号已开通, 用户名:" + user.getUsername() + ", 初始密码:123456, 请修改密码.";
        mailService.sendSimpleMail(user.getEmail(), subject, content);
        return Result.success().setData(userService.changeUserObject(identityService.createUserQuery().userId(newUser.getId()).singleResult()));
    }

    @ApiOperation(value = "删除用户", notes = "根据 UserId 删除用户")
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result deleteUserMethod(@PathVariable("id") String id) {
        List<String> groupIds = userService.getUserGroupIdList(id);
        Result result = userService.checkAddUserToGroupAuthority(adminSession.getUser().getId(), groupIds);
        if (!result.isSuccess()) return result;
        User user = identityService.createUserQuery().userId(id).singleResult();
        if (user == null) return Result.error(EnumAdminUserError.此用户不存在.toString());
        UserObject userObject = DozerUtils.copy(user, UserObject.class);
        identityService.deleteUser(id);
        return Result.success().setData(userObject);
    }

    @ApiOperation(value = "修改用户信息", notes = "根据 User Id修改用户", response = UserObject.class)
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result updateUserInfoMethod(@PathVariable("id") String id,
                                   @ApiParam(name = "user", value = "用户对象", required = true)@RequestBody UserObject user) {
        Result result = userService.checkAddUserToGroupAuthority(adminSession.getUser().getId(), user.getGroupIds());
        if (!result.isSuccess()) return result;
        if (StringUtils.isEmpty(id)) return Result.error(EnumAdminUserError.用户id不能为空.toString());
        if (user == null) return Result.error(EnumAdminUserError.用户对象不能为空.toString());
        User oldUser = identityService.createUserQuery().userId(id).singleResult();
        if (oldUser == null) return Result.error(EnumAdminUserError.此用户不存在.toString());
        oldUser.setEmail(user.getEmail());
        identityService.saveUser(oldUser);
        identityService.setUserInfo(oldUser.getId(), "name", user.getName());
        identityService.setUserInfo(oldUser.getId(), "phone", user.getPhone());
        identityService.setUserInfo(oldUser.getId(), "department", user.getDepartment());
        addUserGroupMemberShip(oldUser.getId(), user.getGroupIds());
        return Result.success().setData(userService.changeUserObject(identityService.createUserQuery().userId(id).singleResult()));
    }

    @ApiOperation(value = "用户自己修改信息", notes = "用户自己修改信息", response = UserObject.class)
    @RequestMapping(value = "/edit", method = RequestMethod.PUT)
    public Result updateUserSelfInfoMethod(@ApiParam(name = "user", value = "用户对象", required = true)@RequestBody UserObject user) {
        if (user == null) return Result.error(EnumAdminUserError.用户对象不能为空.toString());
        User oldUser = identityService.createUserQuery().userId(adminSession.getUser().getId()).singleResult();
        oldUser.setEmail(user.getEmail());
        identityService.saveUser(oldUser);
        identityService.setUserInfo(oldUser.getId(), "name", user.getName());
        identityService.setUserInfo(oldUser.getId(), "phone", user.getPhone());
        identityService.setUserInfo(oldUser.getId(), "department", user.getDepartment());
        addUserGroupMemberShip(oldUser.getId(), user.getGroupIds());
        UserObject userObject = userService.changeUserObject(identityService.createUserQuery().userId(adminSession.getUser().getId()).singleResult());
        adminSession.login(userObject);
        return Result.success().setData(userObject);
    }

    @ApiOperation(value = "管理员帮助用户重置密码", notes = "管理员帮助用户重置密码, 生成随机密码, 发送到用户邮箱.", response = Boolean.class)
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/resetpwd/{id}", method = RequestMethod.POST)
    public Result resetUserPasswordMethod(@PathVariable("id")String id) {
        User user = identityService.createUserQuery().userId(id).singleResult();
        String subject = "重置密码邮件";
        String password = CodeUtils.CreateNumLetterCode();
        user.setPassword(password);
        identityService.saveUser(user);
        String content = "你好: " + user.getFirstName() + ", 管理员为你重置密码, 新密码是: " + password;
        mailService.sendSimpleMail(user.getEmail(), subject, content);
        return Result.success().setData(true);
    }

    @ApiOperation(value = "用户修改密码", notes = "用户自己修改密码", response = Boolean.class)
    @RequestMapping(value = "/changepwd", method = RequestMethod.POST)
    public Result resetUserPasswordMethod(@ApiParam(name = "user", value = "用户密码") @Valid @RequestBody UserPasswordObject userObject) {
        if (!identityService.checkPassword(adminSession.getUser().getId(), userService.securePassword(userObject.getOldPassword()))) {
            return Result.error(EnumAdminUserError.原密码不正确.toString());
        } else if (userService.securePassword(userObject.getNewPassword()).equals(identityService.createUserQuery().userId(adminSession.getUser().getId()).singleResult().getPassword())) {
            return Result.error(EnumAdminUserError.新密码和原密码一样.toString());
        } else {
            User user = identityService.createUserQuery().userId(adminSession.getUser().getId()).singleResult();
            user.setPassword(userService.securePassword(userObject.getNewPassword()));
            identityService.saveUser(user);
            return Result.success().setData(true);
        }
    }

    @Transactional
    public void addUserGroupMemberShip(String userId, List<String> groupIds) {
        List<Group> groupList = identityService.createGroupQuery().groupMember(userId).list();
        if (groupList != null && groupList.size() != 0) {
            for (Group group : groupList) {
                identityService.deleteMembership(userId, group.getId());
            }
        }
        if (groupIds != null && groupIds.size() != 0) {
            for (String gid : groupIds) {
                if (identityService.createGroupQuery().groupId(gid).singleResult() == null)
                    throw new BusinessException(EnumAdminGroupError.此组不存在.toString());
                identityService.createMembership(userId, gid);
            }
        }
    }

}
