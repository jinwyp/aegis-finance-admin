package com.yimei.service.admin;

import com.yimei.boot.ext.mvc.support.Session;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by liuxinjie on 16/7/30.
 */
@Service
public class AdminServiceImpl {
    @Autowired
    private Session session;

//    /**
//     * 登陆方法
//     * @param userName              用户名
//     * @param password              密码
//     */
//    @Override
//    public Result login(String userName, String password) {
//        Admin admin = adminMapper.getAdminByUserName(userName);
//        if (admin != null && admin.getActive()) {
//            if (admin.getPassword().equals(securePassword(userName, password))) {
//                Admin sessionAdmin = new Admin(admin.getId(), admin.getUserName(), admin.getName(), admin.getPhone(), admin.getJobNum());
//                session.login(sessionAdmin);
//                return Result.success();
//            } else {
//                return Result.error(EnumAdminError.用户名或者密码错误.toString());
//            }
//        } else {
//            return Result.error(EnumAdminError.该用户不存在或者已经被禁用.toString());
//        }
//    }


    /**
     * 加密密码
     */
    public String securePassword(String userName, String password) {
        return DigestUtils.md5Hex(userName + password);
    }
}
