package com.yimei.finance.service.admin.user;

import com.yimei.finance.config.session.AdminSession;
import com.yimei.finance.entity.admin.user.EnumAdminUserError;
import com.yimei.finance.entity.admin.user.GroupObject;
import com.yimei.finance.entity.admin.user.UserObject;
import com.yimei.finance.entity.common.result.Result;
import com.yimei.finance.utils.DozerUtils;
import org.activiti.engine.IdentityService;
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
    @Autowired
    private AdminUserServiceImpl userService;

//    /**
//     * 判断一个用户是否有 向该组 添加用户的 权限
//     */
//    public boolean checkUserAddUserToGroupRight(String[] groupIds) {
//
//    }

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
            if (identityService.checkPassword(user.getId(), securePassword(password))) {
                adminSession.login(user);
                return Result.success().setData(userService.changeUserObject(user));
            } else {
                return Result.error(EnumAdminUserError.用户名或者密码错误.toString());
            }
        } else {
            return Result.error(EnumAdminUserError.该用户不存在或者已经被禁用.toString());
        }
    }

    public String securePassword(String password) {
        return DigestUtils.md5Hex("$&*" + DigestUtils.md5Hex("@." + password + "$*************") + "!@#%……&");
    }
}
