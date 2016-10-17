package com.yimei.finance.entity.admin.finance;

import com.yimei.finance.entity.common.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "t_finance_order_contract")
@Data
@NoArgsConstructor
public class FinanceOrderContract extends BaseEntity implements Serializable {
    @Id
    @Column(name = "id", unique = true, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;                                                 //主键  

    @Column(name = "finance_id", nullable = false, updatable = false)
    private Long financeId;                                          //金融单id  

    @Column(name = "contract_no", length = 200, nullable = false, updatable = false, unique = true)
    private String contractNo;                                       //合同编号  

    @Column(name = "type", length = 4, nullable = false)
    private int type;

    @Column(name = "sign_place", length = 220)
    private String signPlace;                                        //签订地点  

    @Column(name = "sign_date", columnDefinition = "DATE")
    private Date signDate;                                           //签订时间  

    @Column(name = "ship_name", length = 120)
    private String shipName;                                         //船名  

    @Column(name = "ship_no", length = 120)
    private String shipNo;                                           //船次  

    @Column(name = "purchase_place", length = 220)
    private String purchasePlace;                                    //购货地点  

    @Column(name = "unload_place", length = 220)
    private String unloadedPlace;                                    //卸货地点  

    @Column(name = "unload_place_short", length = 220)
    private String unloadedPlaceShort;                               //卸货地点简称  

    @Column(name = "delivery_place", length = 220)
    private String deliveryPlace;                                    //交货地点  

    @Column(name = "coal_ton", precision = 12, scale = 2)
    private BigDecimal coalTon;                                      //煤炭吨数  

    @Column(name = "coal_amount", precision = 12, scale = 2)
    private BigDecimal coalAmount;                                   //煤炭数量  

    @Column(name = "coal_type", length = 120)
    private String coalType;                                         //煤炭品种/品类  

    @Column(name = "quantity_remark", length = 5020, columnDefinition = "TEXT")
    private String quantityRemark;                                   //数量备注/备注说明  

    @Column(name = "coal_index", length = 5020, columnDefinition = "TEXT")
    private String coalIndex;                                        //煤炭指标  

    @Column(name = "quality_remark", length = 5020, columnDefinition = "TEXT")
    private String qualityRemark;                                    //质量备注/质量说明  

    @Column(name = "quantity_acceptance_criteria", length = 5020, columnDefinition = "TEXT")
    private String quantityAcceptanceCriteria;                       //数量验收标准  

    @Column(name = "quality_acceptance_criteria", length = 5020, columnDefinition = "TEXT")
    private String qualityAcceptanceCriteria;                        //质量验收标准  

    @Column(name = "payment_period", length = 5)
    private int paymentPeriod;                                       //付款提货期限

    @Column(name = "settlement_price", precision = 10, scale = 2)
    private BigDecimal settlementPrice;                              //结算价格  

    @Column(name = "settlement_amount", precision = 12, scale = 2)
    private BigDecimal settlementAmount;                             //结算吨数

    @Column(name = "cash_deposit", precision = 20, scale = 2)
    private BigDecimal cashDeposit;                                  //保证金

    @Column(name = "seller_receipt_price", precision = 10, scale = 2)
    private BigDecimal sellerReceiptPrice;                           //卖家开票价格  

    @Column(name = "seller_receipt_amount", precision = 12, scale = 2)
    private BigDecimal sellerReceiptAmount;                          //卖家开票吨数  

    @Column(name = "seller_receipt_money", precision = 20, scale = 2)
    private BigDecimal sellerReceiptMoney;                           //卖家开票金额  

    @Column(name = "buyer_settlement_money", precision = 20, scale = 2)
    private BigDecimal buyerSettlementMoney;                         //买家已经结清金额  

    @Column(name = "special_remark", length = 5020, columnDefinition = "TEXT")
    private String specialRemark;                                    //特别约定/特殊说明  

    @Column(name = "attachment_number", length = 4)
    private int attachmentNumber;                                    //附件个数  

    @Column(name = "attachment_names", length = 520)
    private String attachmentNames;                                  //附件名称  

    @Column(name = "buyer_company_name", length = 120)
    private String buyerCompanyName;                                 //买家公司名称  

    @Column(name = "buyer_linkman_name", length = 60)
    private String buyerLinkmanName;                                 //买家联系人姓名  

    @Column(name = "buyer_linkman_phone", length = 50)
    private String buyerLinkmanPhone;                                //买家联系人手机  

    @Column(name = "buyer_linkman_email", length = 120)
    private String buyerLinkmanEmail;                                //买家联系人邮箱  

    @Column(name = "buyer_company_address", length = 220)
    private String buyerCompanyAddress;                              //买家公司地址  

    @Column(name = "buyer_legal_person", length = 60)
    private String buyerLegalPerson;                                 //买家法人  

    @Column(name = "buyer_bank_name", length = 120)
    private String buyerBankName;                                    //买家开户银行  

    @Column(name = "buyer_bank_account", length = 120)
    private String buyerBankAccount;                                 //买家银行账号    

    @Column(name = "seller_company_name", length = 120)
    private String sellerCompanyName;                                //卖家公司名称  

    @Column(name = "seller_linkman_name", length = 60)
    private String sellerLinkmanName;                                //卖家联系人姓名  

    @Column(name = "seller_linkman_phone", length = 50)
    private String sellerLinkmanPhone;                               //卖家联系人手机  

    @Column(name = "seller_linkman_email", length = 120)
    private String sellerLinkmanEmail;                               //卖家联系人邮箱  

    @Column(name = "seller_company_address", length = 220)
    private String sellerCompanyAddress;                             //卖家公司地址  

    @Column(name = "seller_legal_person", length = 60)
    private String sellerLegalPerson;                                //卖家法人  

    @Column(name = "seller_bank_name", length = 120)
    private String sellerBankName;                                   //卖家开户银行  

    @Column(name = "seller_bank_account", length = 120)
    private String sellerBankAccount;                                //卖家银行账号    

}
