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
import com.yimei.finance.representation.admin.user.enums.EnumAdminUserError;
import com.yimei.finance.representation.admin.user.enums.EnumAdminUserStatus;
import com.yimei.finance.representation.admin.user.object.AdminUserSearch;
import com.yimei.finance.representation.admin.user.object.UserLoginObject;
import com.yimei.finance.representation.admin.user.object.UserObject;
import com.yimei.finance.representation.admin.user.object.UserPasswordObject;
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
import java.util.stream.Collectors;

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
     * 用户相关检查,判断
     */
    public Result userValidated(String id, UserObject sessionUser) {
        User user = identityService.createUserQuery().userId(id).singleResult();
        if (user == null) return Result.error(EnumAdminUserError.此用户不存在.toString());
        UserObject userObject = changeUserObject(user);
        Result result = checkOperateUserAuthority(userObject, sessionUser);
        if (!result.isSuccess()) return result;
        if (userObject.getStatus().equals(EnumAdminUserStatus.Deleted.toString())) return Result.error(EnumAdminUserError.此用户已删除.toString());
        return Result.success().setData(userObject);
    }

    /**
     * 根据id查询用户
     */
    public Result findById(String id, UserObject sessionUser) {
        Result result = userValidated(id, sessionUser);
        if (!result.isSuccess()) return result;
        return Result.success().setData(result.getData());
    }

    /**
     * 查询一个用户所有的组
     */
    public Result findUserGroupList(String id, UserObject sessionUser, Page page) {
        Result result = userValidated(id, sessionUser);
        if (!result.isSuccess()) return result;
        List<Group> groupList = identityService.createGroupQuery().groupMember(id).list();
        page.setTotal(Long.valueOf(groupList.size()));
        List<GroupObject> groupObjectList = groupService.changeGroupObject(groupList);
        return Result.success().setData(groupObjectList).setMeta(page);
    }

    /**
     * 删除用户
     */
    public Result deleteById(String id, UserObject sessionUser) {
        Result result = userValidated(id, sessionUser);
        if (!result.isSuccess()) return result;
        identityService.setUserInfo(id, "status", EnumAdminUserStatus.Deleted.toString());
        return Result.success().setData(result.getData());
    }

    /**
     * 添加用户
     */
    public Result addUser(UserObject user, UserObject sessionUser) {
        Result result = checkOperateGroupsAuthority(user.getGroupIds(), sessionUser.getId());
        if (!result.isSuccess()) return result;
        if (identityService.createUserQuery().userFirstName(user.getUsername()).singleResult() != null)
            return Result.error(EnumAdminUserError.此登录名已经存在.toString());
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
        identityService.setUserInfo(newUser.getId(), "status", EnumAdminUserStatus.Normal.toString());
        Result result3 = checkSuperAdminRight(sessionUser.getId());
        if (result3.isSuccess() && user.getCompanyId() != null && user.getCompanyId().longValue() != 0 && user.getCompanyId().longValue() != -1) {
            Company company = companyRepository.findOne(user.getCompanyId());
            if (company == null) return Result.error(EnumCommonError.Admin_System_Error);
            List<UserObject> userObjectList = changeUserObjectSimple(identityService.createUserQuery().memberOfGroup(EnumSpecialGroup.SystemAdminGroup.id).list()).parallelStream().filter(u -> (u.getCompanyId().longValue() == user.getCompanyId().longValue() && u.getStatus().equals(EnumAdminUserStatus.Normal.toString()))).collect(Collectors.toList());
            if (userObjectList != null && userObjectList.size() != 0) return Result.error(EnumAdminUserError.此风控线已经存在系统管理员.toString());
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
        user.setCompanyId(Long.valueOf(identityService.getUserInfo(user.getId(), "companyId")));
        user.setCompanyName(identityService.getUserInfo(user.getId(), "companyName"));
        Result result = checkOperateUserAuthority(user, sessionUser);
        if (!result.isSuccess()) return result;
        if (identityService.getUserInfo(id, "status").equals(EnumAdminUserStatus.Deleted.toString()))
            return Result.error(EnumAdminUserError.此用户已删除.toString());
        Result result1 = checkUserEmail(user.getEmail(), id);
        if (!result1.isSuccess()) return result1;
        Result result2 = checkUserPhone(user.getPhone(), oldUser.getId());
        if (!result2.isSuccess()) return result2;
        oldUser.setEmail(user.getEmail());
        identityService.saveUser(oldUser);
        identityService.setUserInfo(oldUser.getId(), "name", user.getName());
        identityService.setUserInfo(oldUser.getId(), "phone", user.getPhone());
        identityService.setUserInfo(oldUser.getId(), "department", user.getDepartment());
        addUserGroupMemberShip(oldUser.getId(), user.getGroupIds());
        return Result.success().setData(changeUserObject(identityService.createUserQuery().userId(id).singleResult()));
    }

    /**
     * 修改自己的信息
     */
    public Result updateSelfInfo(String id, UserObject user) {
        if (identityService.getUserInfo(id, "status").equals(EnumAdminUserStatus.Deleted.toString()))
            return Result.error(EnumAdminUserError.您的账号已被删除.toString());
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
        Result result = userValidated(id, sessionUser);
        if (!result.isSuccess()) return result;
        User user = identityService.createUserQuery().userId(id).singleResult();
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
        if (identityService.getUserInfo(id, "status").equals(EnumAdminUserStatus.Deleted.toString()))
            return Result.error(EnumAdminUserError.您的账号已被删除.toString());
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
                userList = identityService.createUserQuery().userFirstNameLike(userSearch.getUsername()).memberOfGroup(userSearch.getGroupId()).orderByUserId().desc().list();
            } else if (!StringUtils.isEmpty(userSearch.getUsername())) {
                userList = identityService.createUserQuery().userFirstNameLike(userSearch.getUsername()).orderByUserId().desc().list();
            } else if (!StringUtils.isEmpty(userSearch.getGroupId())) {
                userList = identityService.createUserQuery().memberOfGroup(userSearch.getGroupId()).orderByUserId().desc().list();
            } else {
                userList = identityService.createUserQuery().orderByUserId().desc().list();
            }
        }

        List<String> sessionUserGroupIdList = getUserGroupIdList(sessionUser.getId());
        if (sessionUserGroupIdList != null && sessionUserGroupIdList.size() != 0) {
            List<UserObject> userObjectList = changeUserObject(userList, sessionUser);
            if (sessionUserGroupIdList.contains(EnumSpecialGroup.SuperAdminGroup.id)) {
                if (!StringUtils.isEmpty(userSearch.getName())) {
                    userObjectList.parallelStream().filter(user ->
                            user.getStatus() != null && user.getStatus().equals(EnumAdminUserStatus.Normal.toString()) && user.getName().contains(userSearch.getName())
                    ).collect(Collectors.toList());
                } else {
                    userObjectList.parallelStream().filter(user ->
                            user.getStatus() != null && user.getStatus().equals(EnumAdminUserStatus.Normal.toString())
                    ).collect(Collectors.toList());
                }
            } else {
                if (!StringUtils.isEmpty(userSearch.getName())) {
                    userObjectList.parallelStream().filter(user ->
                            user.getStatus() != null && user.getStatus().equals(EnumAdminUserStatus.Normal.toString()) && user.getName().contains(userSearch.getName()) && user.getCompanyId().longValue() == sessionUser.getCompanyId().longValue()
                    ).collect(Collectors.toList());
                } else {
                    userObjectList.parallelStream().filter(user ->
                            user.getStatus() != null && user.getStatus().equals(EnumAdminUserStatus.Normal.toString()) && user.getCompanyId().longValue() == sessionUser.getCompanyId().longValue()
                    ).collect(Collectors.toList());
                }
            }
            page.setTotal(Long.valueOf(userObjectList.size()));
            int toIndex = page.getPage() * page.getCount() < userObjectList.size() ? page.getPage() * page.getCount() : userObjectList.size();
            return Result.success().setData(userObjectList.subList(page.getOffset(), toIndex)).setMeta(page);
        } else {
            return Result.success().setData(null).setMeta(page);
        }
    }

    /**
     * 获取一条风控线中的所有用户
     */
    public List<UserObject> getUserByRiskCompanyId(Long companyId) {
        List<UserObject> userList = changeUserObject(identityService.createUserQuery().list());
        userList.parallelStream().filter(user -> (
                user.getCompanyId().longValue() == companyId.longValue() && user.getStatus().equals(EnumAdminUserStatus.Normal.toString())
        )).collect(Collectors.toList());
        return userList;
    }

    /**
     * 用户登陆
     */
    public Result loginMethod(UserLoginObject userLoginObject) {
        User user = identityService.createUserQuery().userFirstName(userLoginObject.getUsername()).singleResult();
        if (user != null) {
            UserObject userObject = changeUserObject(user);
            if (!userObject.getStatus().equals(EnumAdminUserStatus.Normal.toString()))
                return Result.error(EnumAdminUserError.您的账号已被删除.toString());
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
            groupList.parallelStream().forEach(group -> {
                identityService.deleteMembership(userId, group.getId());
            });
        }
        if (groupIds != null && groupIds.size() != 0) {
            groupIds.parallelStream().forEach(id -> {
                if (identityService.createGroupQuery().groupId(id).singleResult() == null)
                    throw new BusinessException(EnumAdminGroupError.此组不存在.toString());
                identityService.createMembership(userId, id);
            });
        }
    }

    /**
     * 获取风控线中的第一个系统管理员姓名
     */
    public String findCompanyFirstAdminName(Long companyId) {
        List<UserObject> userObjectList = changeUserObject(identityService.createUserQuery().memberOfGroup(EnumSpecialGroup.SystemAdminGroup.id).orderByUserId().desc().list());
        if (userObjectList == null || userObjectList.size() == 0) return null;
        userObjectList = userObjectList.parallelStream().filter(user -> (
                user.getCompanyId().longValue() == companyId.longValue() && user.getStatus().equals(EnumAdminUserStatus.Normal.toString())
        )).collect(Collectors.toList());
        if (userObjectList == null || userObjectList.size() == 0) return null;
        return userObjectList.get(0).getUsername();
    }

    /**
     * 获取所有部门列表
     */
    public Result findAllDepartmentList() {
        return Result.success().setData(dataBookRepository.findByType(EnumDataBookType.financedepartment.toString()));
    }

    /**
     * 检查是否具有超级管理员权限
     */
    public Result checkSuperAdminRight(String userId) {
        if (getUserGroupIdList(userId).contains(EnumSpecialGroup.SuperAdminGroup.id)) {
            return Result.success();
        }
        return Result.error(EnumAdminUserError.只有超级管理员才能执行此操作.toString());
    }

    /**
     * 查询一个用户有权限操作的所有的组列表
     */
    public Result findHaveRightGroupList(String sessionUserId, Page page) {
        List<GroupObject> groupList = getCanOperateGroupList(sessionUserId);
        page.setTotal(Long.valueOf(groupList.size()));
        return Result.success().setData(groupList).setMeta(page);
    }

    /**
     * 获取一个用户 有权限操作的 组 list
     */
    public List<GroupObject> getCanOperateGroupList(String userId) {
        List<GroupObject> groupObjectList = new ArrayList<>();
        getCanOperateGroupIdList(userId).parallelStream().forEach(gid -> {
            groupObjectList.add(groupService.changeGroupObject(identityService.createGroupQuery().groupId(gid).singleResult()));
        });
        return groupObjectList;
    }

    /**
     * 获取一个用户 有权限添加 用户的组id list
     */
    public List<String> getCanOperateGroupIdList(String userId) {
        List<Group> groupList = new ArrayList<>();
        List<String> groupIds = getUserGroupIdList(userId);
        List<String> sonGroupIds = new ArrayList<>();
        if (groupIds.contains(EnumSpecialGroup.SuperAdminGroup.id)) {
            groupList = identityService.createGroupQuery().list();
            groupList.parallelStream().forEach(group -> {
                sonGroupIds.add(group.getId());
            });
        } else if (groupIds.contains(EnumSpecialGroup.SystemAdminGroup.id)) {
            groupList = identityService.createGroupQuery().groupType(EnumGroupType.Business_Company_Group.id).list();
            groupList.parallelStream().forEach(group -> {
                sonGroupIds.add(group.getId());
            });
            sonGroupIds.remove(EnumSpecialGroup.SystemAdminGroup.id);
        } else {
            groupList = identityService.createGroupQuery().groupMember(userId).list();
            groupList.parallelStream().filter(group -> EnumSpecialGroup.getSonGroup(group.getId()) != null).forEach(group -> {
                sonGroupIds.add(EnumSpecialGroup.getSonGroup(group.getId()).id);
            });
        }
        return sonGroupIds;
    }

    /**
     * 获取一个用户所有组id list
     */
    public List<String> getUserGroupIdList(String userId) {
        List<String> groupIds = new ArrayList<>();
        identityService.createGroupQuery().groupMember(userId).list().parallelStream().forEach(group -> {
            groupIds.add(group.getId());
        });
        return groupIds;
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
        if (getUserGroupIdList(sessionUser.getId()).contains(EnumSpecialGroup.SuperAdminGroup.id)) return Result.success();
        if (userObject.getCompanyId().longValue() != sessionUser.getCompanyId().longValue())
            return Result.error(EnumAdminUserError.你没有操作此用户的权限.toString());
        return checkOperateGroupsAuthority(getUserGroupIdList(userObject.getId()), sessionUser.getId());
    }

    /**
     * 封装 user, 从 User 到 UserObject
     */
    public UserObject changeUserObject(User user) {
        return improveUserObject(DozerUtils.copy(user, UserObject.class), null);
    }

    public List<UserObject> changeUserObject(List<User> userList) {
        if (userList == null || userList.size() == 0) return null;
        List<UserObject> userObjectList = DozerUtils.copy(userList, UserObject.class);
        userObjectList.parallelStream().forEach(userObject -> {
            userObject = improveUserObject(userObject, null);
        });
        return userObjectList;
    }

    public List<UserObject> changeUserObjectSimple(List<User> userList) {
        if (userList == null || userList.size() == 0) return null;
        List<UserObject> userObjectList = DozerUtils.copy(userList, UserObject.class);
        userObjectList.parallelStream().forEach(userObject -> {
            String companyId = identityService.getUserInfo(userObject.getId(), "companyId");
            if (!StringUtils.isEmpty(companyId) && !companyId.equals("null")) {
                userObject.setCompanyId(Long.valueOf(companyId));
            } else {
                userObject.setCompanyId(-1L);
            }
            userObject.setStatus(identityService.getUserInfo(userObject.getId(), "status"));
        });
        return userObjectList;
    }

    public List<UserObject> changeUserObject(List<User> userList, UserObject sessionUser) {
        if (userList == null || userList.size() == 0) return null;
        List<UserObject> userObjectList = DozerUtils.copy(userList, UserObject.class);
        userObjectList.parallelStream().forEach(userObject -> {
            improveUserObject(userObject, sessionUser);
        });
        return userObjectList;
    }

    public UserObject improveUserObject(UserObject userObject, UserObject sessionUser) {
        userObject.setUsername(identityService.getUserInfo(userObject.getId(), "username"));
        userObject.setCompanyName(identityService.getUserInfo(userObject.getId(), "companyName"));
        userObject.setStatus(identityService.getUserInfo(userObject.getId(), "status"));
        userObject.setName(identityService.getUserInfo(userObject.getId(), "name"));
        userObject.setPhone(identityService.getUserInfo(userObject.getId(), "phone"));
        userObject.setDepartment(identityService.getUserInfo(userObject.getId(), "department"));
        String companyId = identityService.getUserInfo(userObject.getId(), "companyId");
        if (!StringUtils.isEmpty(companyId) && !companyId.equals("null")) {
            userObject.setCompanyId(Long.valueOf(companyId));
        } else {
            userObject.setCompanyId(-1L);
        }
        UserLoginRecord userLoginRecord = loginRecordRepository.findTopByUserIdOrderByCreateTimeDesc(userObject.getId());
        if (userLoginRecord != null) {
            userObject.setLastLoginTime(userLoginRecord.getCreateTime());
        }
        if (sessionUser != null) {
            userObject.setOperateAuthority(checkOperateUserAuthority(userObject, sessionUser).isSuccess());
        }
        List<Group> groupList = identityService.createGroupQuery().groupMember(userObject.getId()).list();
        userObject.setGroupList(DozerUtils.copy(groupList, GroupObject.class));
        List<String> userGroupIdList = new ArrayList<>();
        groupList.parallelStream().forEach(group -> {
            userGroupIdList.add(group.getId());
        });
        userObject.setGroupIds(userGroupIdList);
        if (userGroupIdList != null && userGroupIdList.size() != 0) {
            if (userGroupIdList.contains(EnumSpecialGroup.SuperAdminGroup.id)) {
                userObject.setLevel(1);
            } else if (userGroupIdList.contains(EnumSpecialGroup.SystemAdminGroup.id)) {
                userObject.setLevel(2);
            } else {
                userObject.setLevel(100);
            }
        } else {
            userObject.setLevel(100);
        }
        return userObject;
    }

    /**
     * 检查手机号
     */
    public Result checkUserPhone(String phone) {
        if (StringUtils.isEmpty(phone)) return Result.success();
        List<UserObject> userObjectList = changeUserObject(identityService.createUserQuery().list());
        for (UserObject user : userObjectList) {
            if (user.getStatus().equals(EnumAdminUserStatus.Normal.toString()) && !StringUtils.isEmpty(user.getPhone()) && user.getPhone().equals(phone))
                return Result.error(EnumAdminUserError.此手机号已经存在.toString());
        }
        return Result.success();
    }

    public Result checkUserPhone(String phone, String userId) {
        if (StringUtils.isEmpty(phone)) return Result.success();
        List<UserObject> userObjectList = changeUserObject(identityService.createUserQuery().list());
        for (UserObject user : userObjectList) {
            if (user.getStatus().equals(EnumAdminUserStatus.Normal.toString()) && !StringUtils.isEmpty(user.getPhone()) && user.getPhone().equals(phone) && !user.getId().equals(userId))
                return Result.error(EnumAdminUserError.此手机号已经存在.toString());
        }
        return Result.success();
    }

    /**
     * 检查邮箱
     */
    public Result checkUserEmail(String email) {
        if (StringUtils.isEmpty(email)) return Result.error(EnumAdminUserError.邮箱不能为空.toString());
        List<UserObject> userObjectList = changeUserObject(identityService.createUserQuery().userEmail(email).list());
        if (userObjectList == null || userObjectList.size() == 0) return Result.success();
        for (UserObject user : userObjectList) {
            if (user.getStatus().equals(EnumAdminUserStatus.Normal.toString()))
                return Result.error(EnumAdminUserError.此邮箱已经存在.toString());
        }
        return Result.success();
    }

    public Result checkUserEmail(String email, String userId) {
        if (StringUtils.isEmpty(email)) return Result.error(EnumAdminUserError.邮箱不能为空.toString());
        List<UserObject> userObjectList = changeUserObject(identityService.createUserQuery().userEmail(email).list());
        if (userObjectList == null || userObjectList.size() == 0) return Result.success();
        for (UserObject user : userObjectList) {
            if (user.getStatus().equals(EnumAdminUserStatus.Normal.toString()) && !user.getId().equals(userId))
                return Result.error(EnumAdminUserError.此邮箱已经存在.toString());
        }
        return Result.success();
    }

    public String securePassword(String password) {
        return DigestUtils.md5Hex("$&*" + DigestUtils.md5Hex("@." + password + "$*************") + "!@#%……&");
    }


    public void updateOldUserData(Company company) {
        List<Group> groupList = identityService.createGroupQuery().groupType(EnumSpecialGroup.SuperAdminGroup.type).list();
        groupList.parallelStream().forEach(group -> {
            identityService.createUserQuery().memberOfGroup(group.getId()).list().parallelStream().forEach(user -> {
                changeSystemUserData(user);
            });
        });
        groupList = identityService.createGroupQuery().groupType(EnumSpecialGroup.SystemAdminGroup.type).list();
        groupList.parallelStream().forEach(group -> {
            identityService.createUserQuery().memberOfGroup(group.getId()).list().parallelStream().forEach(user -> {
                changeRiskCompanyUserData(user, company);
            });
        });
    }

    void changeSystemUserData(User user) {
        if (StringUtils.isEmpty(identityService.getUserInfo(user.getId(), "companyId")) && StringUtils.isEmpty(identityService.getUserInfo(user.getId(), "companyName"))) {
            identityService.setUserInfo(user.getId(), "companyId", "0");
            identityService.setUserInfo(user.getId(), "companyName", "易煤网金融系统");
        }
        if (StringUtils.isEmpty(identityService.getUserInfo(user.getId(), "status"))) {
            identityService.setUserInfo(user.getId(), "status", EnumAdminUserStatus.Normal.toString());
        }
    }

    void changeRiskCompanyUserData(User user, Company company) {
        if ((StringUtils.isEmpty(identityService.getUserInfo(user.getId(), "companyId")) && StringUtils.isEmpty(identityService.getUserInfo(user.getId(), "companyName"))) | Integer.valueOf(identityService.getUserInfo(user.getId(), "companyId")) == 0) {
            identityService.setUserInfo(user.getId(), "companyId", String.valueOf(company.getId()));
            identityService.setUserInfo(user.getId(), "companyName", company.getName());
        }
        if (StringUtils.isEmpty(identityService.getUserInfo(user.getId(), "status"))) {
            identityService.setUserInfo(user.getId(), "status", EnumAdminUserStatus.Normal.toString());
        }
    }
}
