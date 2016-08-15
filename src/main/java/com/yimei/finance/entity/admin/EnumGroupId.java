package com.yimei.finance.entity.admin;

/**
 * Created by liuxinjie on 16/8/15.
 */
public enum EnumGroupId {

    ManageTraderGroup("AAAAAA001"),
    TraderGroup("AAAAAA002");

    public String id;

    EnumGroupId(String id) {
        this.id=id;
    }
    EnumGroupId(){}
}
