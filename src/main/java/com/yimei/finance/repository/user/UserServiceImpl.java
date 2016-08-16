package com.yimei.finance.repository.user;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

/**
 * Created by liuxinjie on 16/8/16.
 */
@Service
public class UserServiceImpl {

    public String securePassword(String password) {
        return DigestUtils.md5Hex("$&*" + DigestUtils.md5Hex("@." + password + "$*************") + "!@#%……&");
    }
}
