package com.yimei.finance.service.admin;

import com.yimei.finance.config.AdminSession;
import com.yimei.finance.repository.admin.user.EnumAdminUserError;
import com.yimei.finance.repository.common.result.Result;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by liuxinjie on 16/8/16.
 */
@Service
public class AdminUserServiceImpl {
    @Autowired
    private AdminSession adminSession;
    @Autowired
    private IdentityService identityService;

    /**
     * 登陆方法
     * @param userName              用户名
     * @param password              密码
     */
    public Result login(String userName, String password) {
        User user = identityService.createUserQuery().userEmail(userName).singleResult();
        if (user != null) {
            if (identityService.checkPassword(user.getId(), securePassword(password))) {
                adminSession.login(user);
                return Result.success().setData(user);
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
