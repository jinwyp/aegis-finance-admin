package com.yimei.finance.config.session;

import com.yimei.finance.warehouse.representation.user.object.WarehouseUserObject;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class WarehouseAdminSession implements Serializable {
    protected WarehouseUserObject user;

    public WarehouseUserObject getUser() {
        return user;
    }

    public boolean login(WarehouseUserObject user) {
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
