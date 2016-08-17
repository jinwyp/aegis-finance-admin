package com.yimei.finance.config;

import com.yimei.finance.entity.site.user.User;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by joe on 10/26/14.
 */
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserSession implements Serializable{
    protected User user;

    public User getUser() {
        return user;
    }

    public boolean login(User user){
        this.user = user;
        return true;
    }

    public boolean isLogined(){
        return this.user!=null;
    }
    public void logout(){
        this.user = null;
    }

}
