package com.yimei.finance.kitt.representation.account;

import java.io.Serializable;

/**
 * Created by liuxinjie on 16/5/22.
 */
public enum EnumKittUserFundAccountStatus implements Serializable {

    OPENING(1),                             //正在开通
    SUCCESS(2),                             //状态正常
    DISABLED(3),                            //已被禁用
    LOCKED(4);                              //已被锁定

    public int id;

    EnumKittUserFundAccountStatus(int id) {
        this.id = id;
    }

    EnumKittUserFundAccountStatus(){}

}
