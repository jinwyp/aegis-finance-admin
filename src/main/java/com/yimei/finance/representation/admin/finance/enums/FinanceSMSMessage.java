package com.yimei.finance.representation.admin.finance.enums;

public class FinanceSMSMessage {

    public static String getUserAuditNotPassMessage(String code) {
        return "您好: 您的金融申请单: " + code + " 审核没有通过. 欢迎您再次申请.";
    }

    public static String getUserNeedSupplyMaterialMessage(String code, String type) {
        return "您好: 您的金融申请单: " + code + " 正在 审核中, 需要补充" + type + "材料. 我们的业务员会给您联系.";
    }

    public static String getAdminNeedSupplyMaterialMessage(String admin, String code, String type) {
        return "您好: " + admin + "通知你, 金融单: " + code + "需要补充" + type + "材料, 请尽快和客户联系.";
    }

    public static String getUserAuditPassMessage(String code) {
        return "您好: 您的金融申请单: " + code + " 已经审核通过.";
    }
}
