package com.yimei.finance.service.admin.user;

import com.yimei.finance.entity.admin.company.Company;
import com.yimei.finance.entity.admin.user.UserLoginRecord;
import com.yimei.finance.exception.BusinessException;
import com.yimei.finance.repository.admin.company.CompanyRepository;
import com.yimei.finance.repository.admin.user.AdminUserLoginRecordRepository;
import com.yimei.finance.repository.common.DataBookRepository;
import com.yimei.finance.representation.admin.group.EnumAdminGroupError;
import com.yimei.finance.representation.admin.group.EnumGroupType;
import com.yimei.finance.representation.admin.group.EnumSpecialGroup;
import com.yimei.finance.representation.admin.group.GroupObject;
import com.yimei.finance.representation.admin.user.*;
import com.yimei.finance.representation.common.databook.EnumDataBookType;
import com.yimei.finance.representation.common.enums.EnumCommonError;
import com.yimei.finance.representation.common.result.Page;
import com.yimei.finance.representation.common.result.Result;
import com.yimei.finance.service.common.message.MessageServiceImpl;
import com.yimei.finance.utils.CodeUtils;
import com.yimei.finance.utils.DozerUtils;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AdminUserServiceImpl {
    @Autowired
    private IdentityService identityService;
    @Autowired
    private AdminGroupServiceImpl groupService;
    @Autowired
    private AdminUserLoginRecordRepository loginRecordRepository;
    @Autowired
    private MessageServiceImpl messageService;
    @Autowired
    private DataBookRepository dataBookRepository;
    @Autowired
    private CompanyRepository companyRepository;

    /**
     * 根据id查询用户
     */
    public Result findById(String id, UserObject sessionUser) {
        User user = identityService.createUserQuery().userId(id).singleResult();
        if (user == null) return Result.error(EnumAdminUserError.此用户不存在.toString());
        UserObject userObject = changeUserObject(user);
        Result result = checkOperateUserAuthority(userObject, sessionUser);
        if (!result.isSuccess()) return result;
        return Result.success().setData(userObject);
    }

    /**
     * 查询一个用户所有的组
     */
    public Result findUserGroupList(String id, Page page) {
        if (identityService.createUserQuery().userId(id).singleResult() == null) return Result.error(EnumAdminUserError.此用户不存在.toString());
        page.setTotal(identityService.createGroupQuery().groupMember(id).count());
        List<GroupObject> groupObjectList = groupService.changeGroupObject(identityService.createGroupQuery().groupMember(id).list());
        return Result.success().setData(groupObjectList).setMeta(page);
    }

    /**
     * 查询一个用户有权限操作的所有的组列表
     */
    public Result findHaveRightGroupList(String id, Page page) {
        List<GroupObject> groupList = getCanOperateGroupList(id);
        page.setTotal(Long.valueOf(groupList.size()));
        return Result.success().setData(groupList).setMeta(page);
    }

    /**
     * 获取所有部门列表
     */
    public Result findAllDepartmentList() {
        return Result.success().setData(dataBookRepository.findByType(EnumDataBookType.financedepartment.toString()));
    }

    /**
     * 删除用户
     */
    public Result deleteById(String id, UserObject sessionUser) {
        User user = identityService.createUserQuery().userId(id).singleResult();
        if (user == null) return Result.error(EnumAdminUserError.此用户不存在.toString());
        UserObject userObject = DozerUtils.copy(user, UserObject.class);
        Result result = checkOperateUserAuthority(userObject, sessionUser);
        if (!result.isSuccess()) return result;
        identityService.deleteUser(id);
        return Result.success().setData(userObject);
    }

    /**
     * 添加用户
     */
    public Result addUser(UserObject user, UserObject sessionUser) {
        Result result = checkOperateGroupsAuthority(user.getGroupIds(), sessionUser.getId());
        if (!result.isSuccess()) return result;
        if (identityService.createUserQuery().userFirstName(user.getUsername()).singleResult() != null) return Result.error(EnumAdminUserError.此登录名已经存在.toString());
        Result result1 = checkUserEmail(user.getEmail());
        if (!result1.isSuccess()) return result1;
        Result result2 = checkUserPhone(user.getPhone());
        if (!result2.isSuccess()) return result2;
        User newUser = identityService.newUser("");
        DozerUtils.copy(user, newUser);
        newUser.setId(null);
        newUser.setFirstName(user.getUsername());
        String password = CodeUtils.CreateNumLetterCode();
        newUser.setPassword(securePassword(password));
        newUser.setEmail(user.getEmail());
        identityService.saveUser(newUser);
        identityService.setUserInfo(newUser.getId(), "username", user.getUsername());
        identityService.setUserInfo(newUser.getId(), "name", user.getName());
        identityService.setUserInfo(newUser.getId(), "phone", user.getPhone());
        identityService.setUserInfo(newUser.getId(), "department", user.getDepartment());
        Result result3 = checkSuperAdminRight(sessionUser.getId());
        if (result3.isSuccess() && user.getCompanyId() != null && user.getCompanyId() != 0 && user.getCompanyId() != -1) {
            Company company = companyRepository.findOne(user.getCompanyId());
            if (company == null) return Result.error(EnumCommonError.Admin_System_Error);
            identityService.setUserInfo(newUser.getId(), "companyId", String.valueOf(company.getId()));
            identityService.setUserInfo(newUser.getId(), "companyName", company.getName());
        } else {
            identityService.setUserInfo(newUser.getId(), "companyId", String.valueOf(sessionUser.getCompanyId()));
            identityService.setUserInfo(newUser.getId(), "companyName", sessionUser.getCompanyName());
        }
        addUserGroupMemberShip(newUser.getId(), user.getGroupIds());
        String subject = "开通账户通知邮件";
        String content = "你好: 你的账号已开通, 用户名:" + user.getUsername() + ", 初始密码:" + password + ", 请修改密码. [易煤网金融系统]";
        messageService.sendSimpleMail(user.getEmail(), subject, content);
        return Result.success().setData(changeUserObject(identityService.createUserQuery().userId(newUser.getId()).singleResult()));
    }

    /**
     * 修改用户
     */
    public Result updateUser(String id, UserObject user, UserObject sessionUser) {
        if (StringUtils.isEmpty(id)) return Result.error(EnumAdminUserError.用户id不能为空.toString());
        User oldUser = identityService.createUserQuery().userId(id).singleResult();
        if (oldUser == null) return Result.error(EnumAdminUserError.此用户不存在.toString());
        Result result = checkOperateUserAuthority(user, sessionUser);
        if (!result.isSuccess()) return result;
        Result result1 = checkUserEmail(user.getEmail(), id);
        if (!result1.isSuccess()) return result1;
        Result result2 = checkUserPhone(user.getPhone(), oldUser.getId());
        if (!result2.isSuccess()) return result2;
        oldUser.setEmail(user.getEmail());
        identityService.saveUser(oldUser);
        identityService.setUserInfo(oldUser.getId(), "name", user.getName());
        identityService.setUserInfo(oldUser.getId(), "phone", user.getPhone());
        identityService.setUserInfo(oldUser.getId(), "department", user.getDepartment());
        identityService.setUserInfo(oldUser.getId(), "companyId", String.valueOf(sessionUser.getCompanyId()));
        identityService.setUserInfo(oldUser.getId(), "companyName", sessionUser.getCompanyName());
        addUserGroupMemberShip(oldUser.getId(), user.getGroupIds());
        return Result.success().setData(changeUserObject(identityService.createUserQuery().userId(id).singleResult()));
    }

    /**
     * 修改自己的信息
     */
    public Result updateSelfInfo(String id, UserObject user) {
        Result result1 = checkUserEmail(user.getEmail(), id);
        if (!result1.isSuccess()) return result1;
        Result result2 = checkUserPhone(user.getPhone(), id);
        if (!result2.isSuccess()) return result2;
        User oldUser = identityService.createUserQuery().userId(id).singleResult();
        oldUser.setEmail(user.getEmail());
        identityService.saveUser(oldUser);
        identityService.setUserInfo(oldUser.getId(), "name", user.getName());
        identityService.setUserInfo(oldUser.getId(), "phone", user.getPhone());
        identityService.setUserInfo(oldUser.getId(), "department", user.getDepartment());
        UserObject userObject = changeUserObject(identityService.createUserQuery().userId(id).singleResult());
        return Result.success().setData(userObject);
    }

    /**
     * 管理员重置密码
     */
    public Result adminResetUserPassword(String id, UserObject sessionUser) {
        boolean haveAuthority = getUserGroupIdList(id).contains(EnumSpecialGroup.SystemAdminGroup.id) && getUserGroupIdList(sessionUser.getId()).contains(EnumSpecialGroup.SuperAdminGroup.id);
        User user = identityService.createUserQuery().userId(id).singleResult();
        if (user == null) return Result.error(EnumAdminUserError.此用户不存在.toString());
        UserObject userObject = DozerUtils.copy(user, UserObject.class);
        Result result = checkOperateUserAuthority(userObject, sessionUser);
        haveAuthority = haveAuthority || result.isSuccess();
        if (!haveAuthority) return result;
        String subject = "重置密码邮件";
        String password = CodeUtils.CreateNumLetterCode();
        user.setPassword(securePassword(password));
        identityService.saveUser(user);
        String content = "你好: " + user.getFirstName() + ", 管理员为你重置密码, 新密码是: " + password + " . [易煤网金融系统]";
        messageService.sendSimpleMail(user.getEmail(), subject, content);
        return Result.success().setData(true);
    }

    /**
     * 用户更改自己密码
     */
    public Result updateSelfPassword(String id, UserPasswordObject userObject) {
        if (!identityService.checkPassword(id, securePassword(userObject.getOldPassword()))) {
            return Result.error(EnumAdminUserError.原密码不正确.toString());
        } else if (securePassword(userObject.getNewPassword()).equals(identityService.createUserQuery().userId(id).singleResult().getPassword())) {
            return Result.error(EnumAdminUserError.新密码和原密码一样.toString());
        } else {
            User user = identityService.createUserQuery().userId(id).singleResult();
            user.setPassword(securePassword(userObject.getNewPassword()));
            identityService.saveUser(user);
            return Result.success().setData(true);
        }
    }

    /**
     * 用户管理 - 搜索查询 列表
     */
    public Result getUserListBySelect(UserObject sessionUser, AdminUserSearch userSearch, Page page) {
        List<User> userList = new ArrayList<>();
        if (userSearch == null) {
            userList = identityService.createUserQuery().orderByUserId().desc().list();
        } else {
            if (!StringUtils.isEmpty(userSearch.getUsername()) && !StringUtils.isEmpty(userSearch.getGroupId())) {
                userList = identityService.createUserQuery().userFirstNameLike(userSearch.getUsername()).memberOfGroup(userSearch.getGroupId()).orderByUserId().list();
            } else if (!StringUtils.isEmpty(userSearch.getUsername())) {
                userList = identityService.createUserQuery().userFirstNameLike(userSearch.getUsername()).list();
            } else if (!StringUtils.isEmpty(userSearch.getGroupId())) {
                userList = identityService.createUserQuery().memberOfGroup(userSearch.getGroupId()).list();
            } else {
                userList = identityService.createUserQuery().list();
            }
        }
        List<UserObject> userObjectList = changeUserObject(userList, sessionUser);
        List<UserObject> userObjList = new ArrayList<>();
        if (!StringUtils.isEmpty(userSearch.getName())) {
            for (UserObject user : userObjectList) {
                if (user.getName() != null && user.getName().contains(userSearch.getName())) {
                    userObjList.add(user);
                }
            }
        } else {
            userObjList = userObjectList;
        }
        List<UserObject> finalUserList = new ArrayList<>();
        userObjList.forEach(user -> {
            if (!StringUtils.isEmpty(user.getCompanyId()) && !StringUtils.isEmpty(sessionUser.getCompanyId()) && (user.getCompanyId().longValue() == sessionUser.getCompanyId().longValue())) {
                finalUserList.add(user);
            }
        });
        page.setTotal((long) finalUserList.size());
        int toIndex = page.getPage() * page.getCount() < finalUserList.size() ? page.getPage() * page.getCount() : finalUserList.size();
        return Result.success().setData(finalUserList.subList(page.getOffset(), toIndex)).setMeta(page);
    }

    /**
     * 用户登陆
     */
    public Result loginMethod(UserLoginObject userLoginObject) {
        User user = identityService.createUserQuery().userFirstName(userLoginObject.getUsername()).singleResult();
        if (user != null) {
            UserObject userObject = changeUserObject(user);
            if (identityService.checkPassword(user.getId(), securePassword(userLoginObject.getPassword()))) {
                loginRecordRepository.save(new UserLoginRecord(userObject.getId(), userObject.getUsername(), new Date()));
                return Result.success().setData(userObject);
            } else {
                return Result.error(401, EnumAdminUserError.用户名或者密码错误.toString());
            }
        } else {
            return Result.error(401, EnumAdminUserError.该用户不存在或者已经被禁用.toString());
        }
    }

    /**
     * 添加用户到指定的组
     */
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

    public String findCompanyFirstAdminName(Long companyId) {
        List<UserObject> userObjectList = changeUserObject(identityService.createUserQuery().memberOfGroup(EnumSpecialGroup.SystemAdminGroup.id).orderByUserId().desc().list());
        String adminName = null;
        for (UserObject user : userObjectList) {
            if (user.getCompanyId() == companyId) return user.getUsername();
        }
        return null;
    }

    /**
     * 检查是否具有超级管理员权限
     */
    public Result checkSuperAdminRight(String userId) {
        if (getUserGroupIdList(userId).contains(EnumSpecialGroup.SuperAdminGroup.id)) {
            return Result.success();
        }
        return Result.error(EnumAdminUserError.只有超级管理员组成员才能执行此操作.toString());
    }

    /**
     * 检查是否具有系统管理员权限
     */
    public Result checkSystemAdminRight(String userId) {
        if (getUserGroupIdList(userId).contains(EnumSpecialGroup.SystemAdminGroup.id)) {
            return Result.success();
        }
        return Result.error(EnumAdminUserError.只有系统管理员组成员才能执行此操作.toString());
    }

    /**
     * 获取一个用户 有权限操作的 组 list
     */
    public List<GroupObject> getCanOperateGroupList(String userId) {
        List<GroupObject> groupObjectList = new ArrayList<>();
        List<String> groupIds = getCanOperateGroupIdList(userId);
        for (String gid : groupIds) {
            groupObjectList.add(groupService.changeGroupObject(identityService.createGroupQuery().groupId(gid).singleResult()));
        }
        return groupObjectList;
    }

    /**
     * 获取一个用户所有组id list
     */
    public List<String> getUserGroupIdList(String userId) {
        List<String> groupIds = new ArrayList<>();
        List<Group> groupList = identityService.createGroupQuery().groupMember(userId).list();
        if (groupList != null && groupList.size() != 0) {
            for (Group group : groupList) {
                groupIds.add(group.getId());
            }
        }
        return groupIds;
    }

    /**
     * 获取一个用户 有权限添加 用户的组id list
     */
    public List<String> getCanOperateGroupIdList(String userId) {
        List<Group> groupList = new ArrayList<>();
        List<String> groupIds = getUserGroupIdList(userId);
        List<String> sonGroupIds = new ArrayList<>();
        if (groupIds.contains(EnumSpecialGroup.SuperAdminGroup.id)) {
            groupList = identityService.createGroupQuery().groupType(EnumGroupType.YIMEI_System_Group.id).list();
            for (Group group : groupList) {
                sonGroupIds.add(group.getId());
            }
            sonGroupIds.add(EnumSpecialGroup.SystemAdminGroup.id);
        } else if (groupIds.contains(EnumSpecialGroup.SystemAdminGroup.id)) {
            groupList = identityService.createGroupQuery().groupType(EnumGroupType.Business_Company_Group.id).list();
            for (Group group : groupList) {
                sonGroupIds.add(group.getId());
            }
        } else {
            groupList = identityService.createGroupQuery().groupMember(userId).list();
            for (Group group : groupList) {
                EnumSpecialGroup sonGroup = EnumSpecialGroup.getSonGroup(group.getId());
                if (sonGroup != null) sonGroupIds.add(sonGroup.id);
            }
        }
        return sonGroupIds;
    }

    public Result checkOperateGroupsAuthority(List<String> groupIdList, String userId) {
        List<String> canGroupIds = getCanOperateGroupIdList(userId);
        for (String gid : groupIdList) {
            if (!canGroupIds.contains(gid)) {
                EnumSpecialGroup group = EnumSpecialGroup.getGroupById(gid);
                return Result.error("您没有操作 " + group.name + " 用户的权限");
            }
        }
        return Result.success();
    }

    /**
     * 检查是否有更改一个用户的权限
     */
    public Result checkOperateUserAuthority(UserObject userObject, UserObject sessionUser) {
        Result result = checkOperateGroupsAuthority(getUserGroupIdList(userObject.getId()), sessionUser.getId());
        if (!result.isSuccess()) return result;
        boolean haveAuthority = getUserGroupIdList(userObject.getId()).contains(EnumSpecialGroup.SystemAdminGroup.id) && getUserGroupIdList(sessionUser.getId()).contains(EnumSpecialGroup.SuperAdminGroup.id);
        haveAuthority = haveAuthority || (userObject.getCompanyId() == sessionUser.getCompanyId());
        if (!haveAuthority) return Result.error(EnumAdminUserError.你没有操作此用户的权限.toString());
        return Result.success();
    }

    /**
     * 封装 user, 从 User 到 UserObject
     */
    public UserObject changeUserObject(User user) {
        UserObject userObject = new UserObject();
        DozerUtils.copy(user, userObject);
        userObject.setUsername(identityService.getUserInfo(user.getId(), "username"));
        userObject.setPhone(identityService.getUserInfo(user.getId(), "phone"));
        userObject.setName(identityService.getUserInfo(user.getId(), "name"));
        userObject.setDepartment(identityService.getUserInfo(user.getId(), "department"));
        String companyId = identityService.getUserInfo(user.getId(), "companyId");
        if (companyId != null && !companyId.equals("null")) {
            userObject.setCompanyId(Long.valueOf(companyId));
        }
        String companyName = identityService.getUserInfo(user.getId(), "companyName");
        if (companyName != null && !companyName.equals("null")) {
            userObject.setCompanyName(companyName);
        }
        userObject.setGroupList(DozerUtils.copy(identityService.createGroupQuery().groupMember(user.getId()).list(), GroupObject.class));
        UserLoginRecord loginRecord = loginRecordRepository.findTopByUserIdOrderByCreateTimeDesc(user.getId());
        if (loginRecord != null) {
            userObject.setLastLoginTime(loginRecord.getCreateTime());
        }
        return userObject;
    }

    public UserObject changeUserObject(User user, UserObject sessionUser) {
        UserObject userObject = DozerUtils.copy(user, UserObject.class);
        userObject.setUsername(identityService.getUserInfo(user.getId(), "username"));
        userObject.setPhone(identityService.getUserInfo(user.getId(), "phone"));
        userObject.setName(identityService.getUserInfo(user.getId(), "name"));
        userObject.setDepartment(identityService.getUserInfo(user.getId(), "department"));
        String companyId = identityService.getUserInfo(user.getId(), "companyId");
        if (companyId != null && !companyId.equals("null")) {
            userObject.setCompanyId(Long.valueOf(companyId));
        }
        String companyName = identityService.getUserInfo(user.getId(), "companyName");
        if (companyName != null && !companyName.equals("null")) {
            userObject.setCompanyName(companyName);
        }
        userObject.setGroupList(DozerUtils.copy(identityService.createGroupQuery().groupMember(user.getId()).list(), GroupObject.class));
        UserLoginRecord loginRecord = loginRecordRepository.findTopByUserIdOrderByCreateTimeDesc(user.getId());
        if (loginRecord != null) {
            userObject.setLastLoginTime(loginRecord.getCreateTime());
        }
        userObject.setOperateAuthority(checkOperateUserAuthority(userObject, sessionUser).isSuccess());
        return userObject;
    }

    public List<UserObject> changeUserObject(List<User> userList) {
        if (userList == null || userList.size() == 0) return null;
        List<UserObject> userObjectList = new ArrayList<>();
        for (User user : userList) {
            userObjectList.add(changeUserObject(user));
        }
        return userObjectList;
    }

    public List<UserObject> changeUserObject(List<User> userList, UserObject sessionUser) {
        if (userList == null || userList.size() == 0) return null;
        List<UserObject> userObjectList = new ArrayList<>();
        for (User user : userList) {
            userObjectList.add(changeUserObject(user, sessionUser));
        }
        return userObjectList;
    }

    /**
     * 检查手机号
     */
    public Result checkUserPhone(String phone) {
        if (StringUtils.isEmpty(phone)) return Result.success();
        List<UserObject> userObjectList = changeUserObject(identityService.createUserQuery().list());
        for (UserObject user : userObjectList) {
            if (!StringUtils.isEmpty(user.getPhone()) && user.getPhone().equals(phone))
                return Result.error(EnumAdminUserError.此手机号已经存在.toString());
        }
        return Result.success();
    }

    public Result checkUserPhone(String phone, String userId) {
        if (StringUtils.isEmpty(phone)) return Result.success();
        List<UserObject> userObjectList = changeUserObject(identityService.createUserQuery().list());
        for (UserObject user : userObjectList) {
            if (!StringUtils.isEmpty(user.getPhone()) && user.getPhone().equals(phone) && !user.getId().equals(userId))
                return Result.error(EnumAdminUserError.此手机号已经存在.toString());
        }
        return Result.success();
    }

    /**
     * 检查邮箱
     */
    public Result checkUserEmail(String email) {
        if (StringUtils.isEmpty(email)) return Result.error(EnumAdminUserError.邮箱不能为空.toString());
        User emailUser = identityService.createUserQuery().userEmail(email).singleResult();
        if (emailUser != null) return Result.error(EnumAdminUserError.此邮箱已经存在.toString());
        return Result.success();
    }

    public Result checkUserEmail(String email, String userId) {
        if (StringUtils.isEmpty(email)) return Result.error(EnumAdminUserError.邮箱不能为空.toString());
        User emailUser = identityService.createUserQuery().userEmail(email).singleResult();
        if (emailUser != null && !emailUser.getId().equals(userId))
            return Result.error(EnumAdminUserError.此邮箱已经存在.toString());
        return Result.success();
    }

    public String securePassword(String password) {
        return DigestUtils.md5Hex("$&*" + DigestUtils.md5Hex("@." + password + "$*************") + "!@#%……&");
    }


}
