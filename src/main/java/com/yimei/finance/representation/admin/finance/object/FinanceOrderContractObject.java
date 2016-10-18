package com.yimei.finance.representation.admin.finance.object;

import com.yimei.finance.representation.common.base.BaseObject;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(description = "金融单-合同内容")
@Data
@NoArgsConstructor
public class FinanceOrderContractObject extends BaseObject implements Serializable {
    private Long id;                                                 //主键  
    private Long financeId;                                          //金融单id  

    private String contractNo;                                       //合同编号  

    private int type;

    private String signPlace;                                        //签订地点  

    private Date signDate;                                           //签订时间  

    private String shipName;                                         //船名  

    private String shipNo;                                           //船次  

    private String purchasePlace;                                    //购货地点  

    private String unloadedPlace;                                    //卸货地点  

    private String unloadedPlaceShort;                               //卸货地点简称  

    private String deliveryPlace;                                    //交货地点  

    private BigDecimal coalTon;                                      //煤炭吨数  

    private BigDecimal coalAmount;                                   //煤炭数量  

    private String coalType;                                         //煤炭品种/品类  

    private String quantityRemark;                                   //数量备注/备注说明  

    private String coalIndex;                                        //煤炭指标  

    private String qualityRemark;                                    //质量备注/质量说明  

    private String quantityAcceptanceCriteria;                       //数量验收标准  

    private String qualityAcceptanceCriteria;                        //质量验收标准  

    private int paymentPeriod;                                       //付款提货期限

    private BigDecimal settlementPrice;                              //结算价格  

    private BigDecimal settlementAmount;                             //结算吨数

    private BigDecimal cashDeposit;                                  //保证金

    private BigDecimal sellerReceiptPrice;                           //卖家开票价格  

    private BigDecimal sellerReceiptAmount;                          //卖家开票吨数  

    private BigDecimal sellerReceiptMoney;                           //卖家开票金额  

    private BigDecimal buyerSettlementMoney;                         //买家已经结清金额  

    private String specialRemark;                                    //特别约定/特殊说明  

    private int attachmentNumber;                                    //附件个数  

    private String attachmentNames;                                  //附件名称  

    private String buyerCompanyName;                                 //买家公司名称  

    private String buyerLinkmanName;                                 //买家联系人姓名  

    private String buyerLinkmanPhone;                                //买家联系人手机  

    private String buyerLinkmanEmail;                                //买家联系人邮箱  

    private String buyerCompanyAddress;                              //买家公司地址  

    private String buyerLegalPerson;                                 //买家法人  

    private String buyerBankName;                                    //买家开户银行  

    private String buyerBankAccount;                                 //买家银行账号    

    private String sellerCompanyName;                                //卖家公司名称  

    private String sellerLinkmanName;                                //卖家联系人姓名  

    private String sellerLinkmanPhone;                               //卖家联系人手机  

    private String sellerLinkmanEmail;                               //卖家联系人邮箱  

    private String sellerCompanyAddress;                             //卖家公司地址  

    private String sellerLegalPerson;                                //卖家法人  

    private String sellerBankName;                                   //卖家开户银行  

    private String sellerBankAccount;                                //卖家银行账号    

}
