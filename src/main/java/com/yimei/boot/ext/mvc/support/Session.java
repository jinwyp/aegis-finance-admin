package com.yimei.boot.ext.mvc.support;

import com.yimei.entity.admin.Admin;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by joe on 10/26/14.
 */
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Session  implements Serializable{
    protected Admin user;

    public Admin getUser() {
        return user;
    }

    public boolean login(Admin user){
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
