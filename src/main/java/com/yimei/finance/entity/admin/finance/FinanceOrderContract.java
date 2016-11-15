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
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Long id;                                                 //主键  

    @Column(name = "finance_id")
    private Long financeId;                                          //金融单id  

    @Column(name = "contract_no")
    private String contractNo;                                       //合同编号  

    @Column(name = "finance_source_id")
    private String financeSourceId;                                  //流水号，编号

    @Column(name = "apply_user_id")
    private Long applyUserId;                                        //申请人用户id

    @Column(name = "apply_user_name")
    private String applyUserName;                                    //申请人姓名

    @Column(name = "apply_user_phone")
    private String applyUserPhone;                                   //申请人手机号

    @Column(name = "apply_company_id")
    private Long applyCompanyId;                                     //申请人公司id

    @Column(name = "apply_company_name")
    private String applyCompanyName;                                 //申请公司名称

    @Column(name = "finance_type")
    private String financeType;                                      //金融类型(煤易融：MYR 煤易贷: MYD 煤易购: MYG)

    @Column(name = "type")
    private Integer type;                                                //合同类型

    @Column(name = "type_name")
    private String typeName;                                         //合同类型名称

    @Column(name = "sign_place")
    private String signPlace;                                        //签订地点  

    @Column(name = "sign_date")
    private Date signDate;                                           //签订时间  

    @Column(name = "ship_name")
    private String shipName;                                         //船名  

    @Column(name = "ship_no")
    private String shipNo;                                           //船次  

    @Column(name = "purchase_place")
    private String purchasePlace;                                    //购货地点  

    @Column(name = "unload_place")
    private String unloadedPlace;                                    //卸货地点  

    @Column(name = "unload_place_short")
    private String unloadedPlaceShort;                               //卸货地点简称  

    @Column(name = "delivery_place")
    private String deliveryPlace;                                    //交货地点  

    @Column(name = "coal_ton")
    private BigDecimal coalTon;                                      //煤炭吨数  

    @Column(name = "coal_amount")
    private BigDecimal coalAmount;                                   //煤炭数量  

    @Column(name = "coal_type")
    private String coalType;                                         //煤炭品种/品类  

    @Column(name = "quantity_remark")
    private String quantityRemark;                                   //数量备注/备注说明  

    @Column(name = "coal_index_ncv")
    private Integer coalIndex_NCV;                                  //煤炭指标   - 热值

    @Column(name = "coal_index_rs")
    private BigDecimal coalIndex_RS;                                 //煤炭指标   - 硫分

    @Column(name = "coal_index")
    private String coalIndex;                                        //煤炭指标  

    @Column(name = "quality_remark")
    private String qualityRemark;                                    //质量备注/质量说明  

    @Column(name = "quantity_acceptance_criteria")
    private String quantityAcceptanceCriteria;                       //数量验收标准  

    @Column(name = "quality_acceptance_criteria")
    private String qualityAcceptanceCriteria;                        //质量验收标准  

    @Column(name = "payment_period")
    private int paymentPeriod;                                       //付款提货期限

    @Column(name = "settlement_price")
    private BigDecimal settlementPrice;                              //结算价格  

    @Column(name = "settlement_amount")
    private BigDecimal settlementAmount;                             //结算吨数

    @Column(name = "cash_deposit")
    private BigDecimal cashDeposit;                                  //保证金

    @Column(name = "seller_receipt_price")
    private BigDecimal sellerReceiptPrice;                           //卖家开票价格  

    @Column(name = "seller_receipt_amount")
    private BigDecimal sellerReceiptAmount;                          //卖家开票吨数  

    @Column(name = "seller_receipt_money")
    private BigDecimal sellerReceiptMoney;                           //卖家开票金额  

    @Column(name = "buyer_settlement_money")
    private BigDecimal buyerSettlementMoney;                         //买家已经结清金额  

    @Column(name = "special_remark")
    private String specialRemark;                                    //特别约定/特殊说明  

    @Column(name = "attachment_number")
    private int attachmentNumber;                                    //附件个数  

    @Column(name = "attachment_names")
    private String attachmentNames;                                  //附件名称  

    @Column(name = "buyer_company_name")
    private String buyerCompanyName;                                 //买家公司名称  

    @Column(name = "buyer_linkman_name")
    private String buyerLinkmanName;                                 //买家联系人姓名  

    @Column(name = "buyer_linkman_phone")
    private String buyerLinkmanPhone;                                //买家联系人手机  

    @Column(name = "buyer_linkman_email")
    private String buyerLinkmanEmail;                                //买家联系人邮箱  

    @Column(name = "buyer_company_address")
    private String buyerCompanyAddress;                              //买家公司地址  

    @Column(name = "buyer_legal_person")
    private String buyerLegalPerson;                                 //买家法人  

    @Column(name = "buyer_bank_name")
    private String buyerBankName;                                    //买家开户银行  

    @Column(name = "buyer_bank_account")
    private String buyerBankAccount;                                 //买家银行账号    

    @Column(name = "seller_company_name")
    private String sellerCompanyName;                                //卖家公司名称  

    @Column(name = "seller_linkman_name")
    private String sellerLinkmanName;                                //卖家联系人姓名  

    @Column(name = "seller_linkman_phone")
    private String sellerLinkmanPhone;                               //卖家联系人手机  

    @Column(name = "seller_linkman_email")
    private String sellerLinkmanEmail;                               //卖家联系人邮箱  

    @Column(name = "seller_company_address")
    private String sellerCompanyAddress;                             //卖家公司地址  

    @Column(name = "seller_legal_person")
    private String sellerLegalPerson;                                //卖家法人  

    @Column(name = "seller_bank_name")
    private String sellerBankName;                                   //卖家开户银行  

    @Column(name = "seller_bank_account")
    private String sellerBankAccount;                                //卖家银行账号    

}
