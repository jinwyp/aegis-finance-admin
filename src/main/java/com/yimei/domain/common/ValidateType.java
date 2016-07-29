package com.yimei.domain.common;

import java.io.Serializable;

/**
 * Created by fanjun on 14-11-7.
 */
public enum ValidateType implements Serializable {
  register("注册"),
  registerWeixin("微信注册"),
  resetpassword("修改密码"),
  resetpasswordWeixin("微信修改密码"),
  manualsell("委托人工"),
  forgetPassword("忘记密码"),
  forgetPasswordWeixin("微信忘记密码"),
  updatePhoneNum("更换手机号"),
  updatePhoneNumWeixin("微信更换手机号"),
  quickTrade("快速找货"),
  quickTradeWeixin("微信快速找货"),
  cooperation("我要合作"),
  cooperationWeixin("微信我要合作"),
  changeemail("更换邮箱"),
  changeemailWeixin("微信更换邮箱"),
  logistics("添加物流单"),
  avtivateEmail("激活邮箱"),
  paymentMallOrder("支付"),
  legalpersonname("验证法人手机号");

  public String value;

  ValidateType(String value) {
    this.value=value;
  }
  ValidateType(){}

  public String value() {
    return this.value;
  }

  public static ValidateType getEnumFromString(String type) {
    if (type != null) {
      return Enum.valueOf(ValidateType.class,type.trim());
    }
    return null;
  }
}
