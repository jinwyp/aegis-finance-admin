package com.yimei.finance.representation.admin.finance;

public enum EnumAdminFinanceSMS {

    ;
    private String code;
    private String type;

    public static String getUserNeedSupplyMaterialMessage(String code, String type) {
        return "您好, 您的金融申请单: " + code + " 在 审核中, 需要补充" + type + "材料. 我们的业务员会给您联系.";
    }

}
