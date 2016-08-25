package com.yimei.finance.service.admin.user;

import com.yimei.finance.config.session.AdminSession;
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

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminUserServiceImpl {
    @Autowired
    private AdminSession adminSession;
    @Autowired
    private IdentityService identityService;

    /**
     * 判断一个用户是否有 向该组 添加用户的 权限
     */
    public Result checkAddUserToGroupAuthority(List<String> groupIds) {
        if (getUserGroupIdList(adminSession.getUser().getId()).contains(EnumSpecialGroup.SuperAdminGroup.id)) return Result.success();
        List<String> canGroupIds = getCanAddUserGroupIds(adminSession.getUser().getId());
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
    public List<String> getCanAddUserGroupIds(String userId) {
        List<Group> groupList = identityService.createGroupQuery().groupMember(userId).list();
        List<String> sonGroupIds = new ArrayList<>();
        for (Group group : groupList) {
            EnumSpecialGroup sonGroup = EnumSpecialGroup.getSonGroup(group.getId());
            if (sonGroup != null) sonGroupIds.add(sonGroup.id);
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

    /**
     * 登陆方法
     * @param username              用户名
     * @param password              密码
     */
    public Result login(String username, String password) {
        User user = identityService.createUserQuery().userFirstName(username).singleResult();
        if (user != null) {
            UserObject userObject = changeUserObject(user);
            if (identityService.checkPassword(user.getId(), securePassword(password))) {
                adminSession.login(userObject);
                return Result.success().setData(userObject);
            } else {
                return Result.error(401, EnumAdminUserError.用户名或者密码错误.toString());
            }
        } else {
            return Result.error(401, EnumAdminUserError.该用户不存在或者已经被禁用.toString());
        }
    }

    public String securePassword(String password) {
        return DigestUtils.md5Hex("$&*" + DigestUtils.md5Hex("@." + password + "$*************") + "!@#%……&");
    }
}
