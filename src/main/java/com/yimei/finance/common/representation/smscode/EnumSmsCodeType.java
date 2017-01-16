package com.yimei.finance.common.representation.smscode;

public enum EnumSmsCodeType {
    Warehouse_MYD_FundProvider_Pay_Trafficker(1),              //仓押-MYD-资金方付款给贸易商
    Warehouse_MYD_Trafficker_Pay_Customer(2),                  //仓押-MYD-贸易商付款给融资方
    Warehouse_MYD_Customer_Pay_Trafficker(3),                  //仓押-MYD-融资方回款给贸易商
    Warehouse_MYD_Trafficker_Pay_FundProvider(4),              //仓押-MYD-贸易商回款给资金方

    ;

    public int id;

    EnumSmsCodeType(int id) {
        this.id = id;
    }
}
