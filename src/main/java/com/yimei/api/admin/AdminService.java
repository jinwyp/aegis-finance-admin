package com.yimei.api.admin;

import com.yimei.api.common.Result;

/**
 * Created by liuxinjie on 16/7/30.
 */
public interface AdminService {

    /**
     * 登陆方法
     * @param userName              用户名
     * @param password              密码
     */
    Result login(String userName, String password);


}
