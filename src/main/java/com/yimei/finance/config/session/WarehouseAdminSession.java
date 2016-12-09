package com.yimei.finance.config.session;

import com.yimei.finance.warehouse.representation.user.object.WarehouseAdminUserObject;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class WarehouseAdminSession implements Serializable {
    protected WarehouseAdminUserObject user;

    public WarehouseAdminUserObject getUser() {
        return user;
    }

    public boolean login(WarehouseAdminUserObject user) {
        this.user = user;
        return true;
    }

    public boolean isLogined() {
        return this.user != null;
    }

    public void logout() {
        this.user = null;
    }

}
