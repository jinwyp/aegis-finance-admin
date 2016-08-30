package com.yimei.finance.service.admin.user;

import com.yimei.finance.entity.admin.user.EnumAdminUserError;
import com.yimei.finance.entity.admin.user.EnumSpecialGroup;
import com.yimei.finance.entity.admin.user.GroupObject;
import com.yimei.finance.entity.admin.user.UserObject;
import com.yimei.finance.entity.common.result.Result;
import com.yimei.finance.utils.DozerUtils;
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

    /**
     * 获取可以查看
     */

    /**
     * 判断一个用户是否有 向该组 添加用户的 权限
     */
    public Result checkAddUserToGroupAuthority(String userId, List<String> groupIds) {
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

    public String securePassword(String password) {
        return DigestUtils.md5Hex("$&*" + DigestUtils.md5Hex("@." + password + "$*************") + "!@#%……&");
    }

    public Result checkUserPhone(String phone) {
        if (StringUtils.isEmpty(phone)) return Result.success();
        List<UserObject> userObjectList = changeUserObject(identityService.createUserQuery().list());
        for (UserObject user : userObjectList) {
            if (!StringUtils.isEmpty(user.getPhone()) && user.getPhone().equals(phone)) return Result.error(EnumAdminUserError.此手机号已经存在.toString());
        }
        return Result.success();
    }
}
