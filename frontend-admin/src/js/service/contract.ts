/**
 * Created by JinWYP on 8/11/16.
 */

import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import {GlobalPromiseHttpCatch, HttpResponse, API } from './http';

class Contract {

    // id : number;                                                //主键  
    // financeId : number;                                         //金融单id  
    // contractNo : string;                                        //合同编号  
    // type : number;                                              //合同类型
    // signPlace : string;                                         //签订地点  
    // signDate : string;                                          //签订时间  
    // shipName : string;                                          //船名  
    // shipNo : string;                                            //船次  
    // purchasePlace : string;                                     //购货地点  
    // unloadedPlace : string;                                     //卸货地点  
    // unloadedPlaceShort : string;                                //卸货地点简称  
    // deliveryPlace : string;                                     //交货地点  
    // coalTon : number;                                           //煤炭吨数  
    // coalAmount : number;                                        //煤炭数量  
    // coalType : string;                                          //煤炭品种/品类  
    // quantityRemark : string;                                    //数量备注,备注说明  
    // CoalIndex_NCV : string;                                         //煤炭指标  
    // CoalIndex_RS : string;                                         //煤炭指标  
    // qualityRemark : string;                                     //质量备注/质量说明  
    // quantityAcceptanceCriteria : string;                        //数量验收标准  
    // qualityAcceptanceCriteria : string;                         //质量验收标准  
    // paymentPeriod : number;                                     //付款提货期限
    // settlementPrice : number;                                   //结算价格  
    // settlementAmount : number;                                  //结算吨数
    // cashDeposit : number;                                       //保证金
    // sellerReceiptPrice : number;                                //卖家开票价格  
    // sellerReceiptAmount : number;                               //卖家开票吨数  
    // sellerReceiptMoney : number;                                //卖家开票金额  
    // buyerSettlementMoney : number;                              //买家已经结清金额  
    // specialRemark : string;                                     //特别约定/特殊说明  
    // attachmentNumber : number;                                  //附件个数  
    // attachmentNames : string;                                   //附件名称  
    //
    // buyerCompanyName : string;                                  //买家公司名称  
    // buyerLinkmanName : string;                                  //买家联系人姓名  
    // buyerLinkmanPhone : string;                                 //买家联系人手机  
    // buyerLinkmanEmail : string;                                 //买家联系人邮箱  
    // buyerCompanyAddress : string;                               //买家公司地址  
    // buyerLegalPerson : string;                                  //买家法人  
    // buyerBankName : string;                                     //买家开户银行  
    // buyerBankAccount : string;                                  //买家银行账号    
    //
    // sellerCompanyName : string;                                 //卖家公司名称  
    // sellerLinkmanName : string;                                 //卖家联系人姓名  
    // sellerLinkmanPhone : string;                                //卖家联系人手机  
    // sellerLinkmanEmail : string;                                //卖家联系人邮箱  
    // sellerCompanyAddress : string;                              //卖家公司地址  
    // sellerLegalPerson : string;                                 //卖家法人  
    // sellerBankName : string;                                    //卖家开户银行  
    // sellerBankAccount : string;                                 //卖家银行账号    



    id : number = 1;                                                //主键  
    financeId : number = 1;                                         //金融单id  
    contractNo : string = 'aaa';                                        //合同编号  
    type : number = 1;                                              //合同类型
    signPlace : string = 'aaa';                                         //签订地点  
    signDate : string = 'aaa';                                          //签订时间  
    shipName : string = 'aaa';                                          //船名  
    shipNo : string = 'aaa';                                            //船次  
    purchasePlace : string = 'aaa';                                     //购货地点  
    unloadedPlace : string = 'aaa';                                     //卸货地点  
    unloadedPlaceShort : string = 'aaa';                                //卸货地点简称  
    deliveryPlace : string = 'aaa';                                     //交货地点  
    coalTon : number = 1;                                           //煤炭吨数  
    coalAmount : number = 1;                                        //煤炭数量  
    coalType : string = 'aaa';                                          //煤炭品种/品类  
    quantityRemark : string = 'aaa';                                    //数量备注,备注说明  
    coalIndex_NCV : string = '1000';                                         //煤炭指标  
    coalIndex_RS : string = '5';                                         //煤炭指标  
    qualityRemark : string = 'aaa';                                     //质量备注/质量说明  
    quantityAcceptanceCriteria : string = 'aaa';                        //数量验收标准  
    qualityAcceptanceCriteria : string = 'aaa';                         //质量验收标准  
    paymentPeriod : number = 1;                                     //付款提货期限
    settlementPrice : number = 1;                                   //结算价格  
    settlementAmount : number = 1;                                  //结算吨数
    cashDeposit : number = 1;                                       //保证金
    sellerReceiptPrice : number = 1;                                //卖家开票价格  
    sellerReceiptAmount : number = 1;                               //卖家开票吨数  
    sellerReceiptMoney : number = 1;                                //卖家开票金额  
    buyerSettlementMoney : number = 1;                              //买家已经结清金额  
    specialRemark : string = 'aaa';                                     //特别约定/特殊说明  
    attachmentNumber : number = 1;                                  //附件个数  
    attachmentNames : string = 'aaa';                                   //附件名称  

    buyerCompanyName : string = 'aaa';                                  //买家公司名称  
    buyerLinkmanName : string = 'aaa';                                  //买家联系人姓名  
    buyerLinkmanPhone : string = '18621266707';                                 //买家联系人手机  
    buyerLinkmanEmail : string = 'liushengbin@yimei180.com';                                 //买家联系人邮箱  
    buyerCompanyAddress : string = 'aaa';                               //买家公司地址  
    buyerLegalPerson : string = 'aaa';                                  //买家法人  
    buyerBankName : string = 'aaa';                                     //买家开户银行  
    buyerBankAccount : string = '6214 8301 1109 9991';                                  //买家银行账号    

    sellerCompanyName : string = 'aaa';                                 //卖家公司名称  
    sellerLinkmanName : string = 'aaa';                                 //卖家联系人姓名  
    sellerLinkmanPhone : string = '18621266707';                                //卖家联系人手机  
    sellerLinkmanEmail : string = 'liushengbin@yimei180.com';                                //卖家联系人邮箱  
    sellerCompanyAddress : string = 'aaa';                              //卖家公司地址  
    sellerLegalPerson : string = 'aaa';                                 //卖家法人  
    sellerBankName : string = 'aaa';                                    //卖家开户银行  
    sellerBankAccount : string = '6214 8301 1109 9991';                                 //卖家银行账号    


    constructor() {
    }
}

@Injectable()
class ContractService {

    constructor(
        private http: Http
    ) { }

    getContractById(financeId: number, type : number) {
        return this.http.get(`${API.orders}/${financeId}/riskmanager/contract/${type}`).toPromise()
            .then(response => response.json() as HttpResponse)
            .catch(GlobalPromiseHttpCatch);
    }

    add(contract: Contract, taskId, type : number) {
        let headers = new Headers({'Content-Type': 'application/json'});

        return this.http.post(`${API.tasksMYD}/riskmanager/audit/${taskId}/contract?type=${type}`, JSON.stringify(contract), {headers: headers}).toPromise()
            .then(res => res.json() as HttpResponse )
            .catch(GlobalPromiseHttpCatch);
    }

}



export { ContractService, Contract }