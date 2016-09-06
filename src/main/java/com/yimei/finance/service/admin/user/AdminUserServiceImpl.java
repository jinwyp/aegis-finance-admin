package com.yimei.finance.service.admin.user;

import com.yimei.finance.representation.admin.user.GroupObject;
import com.yimei.finance.entity.admin.user.UserLoginRecord;
import com.yimei.finance.representation.admin.user.UserObject;
import com.yimei.finance.repository.admin.user.AdminUserLoginRecordRepository;
import com.yimei.finance.representation.admin.user.AdminUserSearch;
import com.yimei.finance.representation.admin.user.EnumAdminUserError;
import com.yimei.finance.representation.admin.user.EnumSpecialGroup;
import com.yimei.finance.representation.common.result.Page;
import com.yimei.finance.representation.common.result.Result;
import com.yimei.finance.utils.DozerUtils;
import com.yimei.finance.utils.Where;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminUserServiceImpl {
    @Autowired
    private IdentityService identityService;
    @Autowired
    private AdminGroupServiceImpl groupService;
    @Autowired
    private AdminUserLoginRecordRepository loginRecordRepository;

    /**
     * 判断一个用户是否有 向该组 添加用户的 权限
     */
    public Result checkAddUserGroupAuthority(String userId, List<String> groupIds) {
        if (getUserGroupIdList(userId).contains(EnumSpecialGroup.SuperAdminGroup.id)) return Result.success();
        List<String> canGroupIds = getCanAddUserGroupIds(userId);
        for (String gid : groupIds) {
            if (!canGroupIds.contains(gid)) {
                EnumSpecialGroup group = EnumSpecialGroup.getGroupById(gid);
                return Result.error("您没有更改 " + group.name + " 用户的权限");
            }
        }
        return Result.success();
    }

    /**
     * 获取一个用户 有权限添加 用户的组 list
     */
    public List<GroupObject> getCanAddUserGroupList(String userId) {
        List<GroupObject> groupObjectList = new ArrayList<>();
        List<String> groupIds = getCanAddUserGroupIds(userId);
        for (String gid : groupIds) {
            groupObjectList.add(groupService.changeGroupObject(identityService.createGroupQuery().groupId(gid).singleResult()));
        }
        return groupObjectList;
    }


    /**
     * 获取一个用户 有权限添加 用户的组id list
     */
    public List<String> getCanAddUserGroupIds(String userId) {
        List<Group> groupList = new ArrayList<>();
        List<String> sonGroupIds = new ArrayList<>();
        if (getUserGroupIdList(userId).contains(EnumSpecialGroup.SuperAdminGroup.id)) {
            groupList = identityService.createGroupQuery().list();
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
     * 判断 用户1 是否有权限操作 用户2
     */
    public boolean checkOperateRight(String userId1, String userId2) {
        List<String> user1GroupList = getUserGroupIdList(userId1);
        if (user1GroupList.contains(EnumSpecialGroup.SuperAdminGroup.id)) return true;
        List<String> user2GroupList = getUserGroupIdList(userId2);
        for (String gid : user2GroupList) {
            EnumSpecialGroup sonGroup = EnumSpecialGroup.getSonGroup(gid);
            if (sonGroup != null) return false;
        }
        List<String> user1SonGroupList = new ArrayList<>();
        for (String gid : user1GroupList) {
            EnumSpecialGroup sonGroup = EnumSpecialGroup.getSonGroup(gid);
            if (sonGroup != null) user1SonGroupList.add(sonGroup.id);
        }
        if (user1SonGroupList == null || user1SonGroupList.size() == 0) return false;
        for (String gid : user2GroupList) {
            if (user1SonGroupList.contains(gid)) return true;
        }
        return false;
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
        userObject.setGroupList(DozerUtils.copy(identityService.createGroupQuery().groupMember(user.getId()).list(), GroupObject.class));
        UserLoginRecord loginRecord = loginRecordRepository.findTopByUserId(user.getId());
        if (loginRecord != null) {
            userObject.setLastLoginTime(loginRecord.getCreateTime());
        }
        return userObject;
    }

    public UserObject changeUserObject(User user, String operateUserId) {
        UserObject userObject = new UserObject();
        DozerUtils.copy(user, userObject);
        userObject.setUsername(identityService.getUserInfo(user.getId(), "username"));
        userObject.setPhone(identityService.getUserInfo(user.getId(), "phone"));
        userObject.setName(identityService.getUserInfo(user.getId(), "name"));
        userObject.setDepartment(identityService.getUserInfo(user.getId(), "department"));
        userObject.setGroupList(DozerUtils.copy(identityService.createGroupQuery().groupMember(user.getId()).list(), GroupObject.class));
        UserLoginRecord loginRecord = loginRecordRepository.findTopByUserId(user.getId());
        if (loginRecord != null) {
            userObject.setLastLoginTime(loginRecord.getCreateTime());
        }
        userObject.setOperateAuthority(checkOperateRight(operateUserId, user.getId()));
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

    public List<UserObject> changeUserObject(List<User> userList, String operateUserId) {
        if (userList == null || userList.size() == 0) return null;
        List<UserObject> userObjectList = new ArrayList<>();
        for (User user : userList) {
            userObjectList.add(changeUserObject(user, operateUserId));
        }
        return userObjectList;
    }

    public String securePassword(String password) {
        return DigestUtils.md5Hex("$&*" + DigestUtils.md5Hex("@." + password + "$*************") + "!@#%……&");
    }

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
    public Result checkUserEmail(String email) {
        if (StringUtils.isEmpty(email)) return Result.error(EnumAdminUserError.邮箱不能为空.toString());
        User emailUser = identityService.createUserQuery().userEmail(email).singleResult();
        if (emailUser != null) return Result.error(EnumAdminUserError.此邮箱已经存在.toString());
        return Result.success();
    }

    public Result checkUserEmail(String email, String userId) {
        if (StringUtils.isEmpty(email)) return Result.error(EnumAdminUserError.邮箱不能为空.toString());
        User emailUser = identityService.createUserQuery().userEmail(email).singleResult();
        if (emailUser != null && !emailUser.getId().equals(userId)) return Result.error(EnumAdminUserError.此邮箱已经存在.toString());
        return Result.success();
    }

    /**
     * 检查是否具有系统管理员权限
     */
    public Result checkSuperAdminRight(String userId) {
        List<Group> groups = identityService.createGroupQuery().groupMember(userId).list();
        for (Group group : groups) {
            if (group.getId().equals(EnumSpecialGroup.SuperAdminGroup.id)) {
                return Result.success();
            }
        }
        return Result.error(EnumAdminUserError.只有系统管理员组成员才能执行此操作.toString());
    }

    public Result getUserListBySelect(String operateUserId, AdminUserSearch userSearch, Page page) {
        if (userSearch == null) {
            page.setTotal(identityService.createUserQuery().count());
            return Result.success().setData(identityService.createUserQuery().list()).setMeta(page);
        } else {
            List<User> userList = new ArrayList<>();
            List<UserObject> userObjectList = new ArrayList<>();
            List<UserObject> userObjList = new ArrayList<>();
            if (!StringUtils.isEmpty(userSearch.getUsername()) && !StringUtils.isEmpty(userSearch.getGroupName())) {
                List<Group> groupList = identityService.createGroupQuery().groupNameLike(userSearch.getGroupName()).list();
                if (groupList != null && groupList.size() != 0) {
                    List<String> groupIds = new ArrayList<>();
                    for (Group group : groupList) {
                        groupIds.add(group.getId());
                    }
                    for (String gid : groupIds) {
                        List<User> users = identityService.createUserQuery().userFirstNameLike(userSearch.getUsername()).memberOfGroup(gid).orderByUserId().list();
                        if (users != null && users.size() != 0) {
                            userList.addAll(users);
                        }
                    }
                } else {
                    userList = identityService.createUserQuery().userFirstNameLike(userSearch.getUsername()).orderByUserId().list();
                }
            } else if (!StringUtils.isEmpty(userSearch.getUsername())){
                userList = identityService.createUserQuery().userFirstNameLike(userSearch.getUsername()).list();
            } else if (!StringUtils.isEmpty(userSearch.getGroupName())) {
                List<Group> groupList = identityService.createGroupQuery().groupNameLike(Where.$like$(userSearch.getGroupName())).list();
                if (groupList != null && groupList.size() != 0) {
                    List<String> groupIds = new ArrayList<>();
                    for (Group group : groupList) {
                        groupIds.add(group.getId());
                    }
                    for (String gid : groupIds) {
                        List<User> users = identityService.createUserQuery().memberOfGroup(gid).list();
                        if (users != null && users.size() != 0) {
                            userList.addAll(users);
                        }
                    }
                } else {
                    userList = identityService.createUserQuery().list();
                }
            } else {
                userList = identityService.createUserQuery().list();
            }
            userObjectList = changeUserObject(userList, operateUserId);
            if (!StringUtils.isEmpty(userSearch.getName()) ) {
                for (UserObject userObject : userObjectList) {
                    if (userObject.getName() != null && userObject.getName().contains(userSearch.getName())) {
                        userObjList.add(userObject);
                    }
                }
                if (userObjList == null) {
                    return Result.success();
                } else {
                    page.setTotal((long) userObjList.size());
                    return Result.success().setData(userObjList).setMeta(page);
                }
            } else {
                if (userObjectList == null) {
                    return Result.success();
                } else {
                    page.setTotal(Long.valueOf(userObjectList.size()));
                    return Result.success().setData(userObjectList).setMeta(page);
                }
            }
        }
    }


}
