/**
 * Created by JinWYP on 8/11/16.
 */

import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import {GlobalPromiseHttpCatch, HttpResponse, API } from './http';

class Contract {

    id : number;                                                //主键  
    financeId : number;                                         //金融单id  
    contractNo : string;                                        //合同编号  
    type : number;                                              //合同类型
    typeName : string;                                          //合同类型名称
    signPlace : string;                                         //签订地点  
    signDate : string;                                          //签订时间  
    shipName : string;                                          //船名  
    shipNo : string;                                            //船次  
    purchasePlace : string;                                     //购货地点  
    unloadedPlace : string;                                     //卸货地点  
    unloadedPlaceShort : string;                                //卸货地点简称  
    deliveryPlace : string;                                     //交货地点  
    coalTon : number;                                           //煤炭吨数  
    coalAmount : number;                                        //煤炭数量  
    coalType : string;                                          //煤炭品种/品类  
    quantityRemark : string;                                    //数量备注,备注说明  
    coalIndex : string;                                         //煤炭指标  
    qualityRemark : string;                                     //质量备注/质量说明  
    quantityAcceptanceCriteria : string;                        //数量验收标准  
    qualityAcceptanceCriteria : string;                         //质量验收标准  
    paymentPeriod : number;                                     //付款提货期限
    settlementPrice : number;                                   //结算价格  
    settlementAmount : number;                                  //结算吨数
    cashDeposit : number;                                       //保证金
    sellerReceiptPrice : number;                                //卖家开票价格  
    sellerReceiptAmount : number;                               //卖家开票吨数  
    sellerReceiptMoney : number;                                //卖家开票金额  
    buyerSettlementMoney : number;                              //买家已经结清金额  
    specialRemark : string;                                     //特别约定/特殊说明  
    attachmentNumber : number;                                  //附件个数  
    attachmentNames : string;                                   //附件名称  

    buyerCompanyName : string;                                  //买家公司名称  
    buyerLinkmanName : string;                                  //买家联系人姓名  
    buyerLinkmanPhone : string;                                 //买家联系人手机  
    buyerLinkmanEmail : string;                                 //买家联系人邮箱  
    buyerCompanyAddress : string;                               //买家公司地址  
    buyerLegalPerson : string;                                  //买家法人  
    buyerBankName : string;                                     //买家开户银行  
    buyerBankAccount : string;                                  //买家银行账号    

    sellerCompanyName : string;                                 //卖家公司名称  
    sellerLinkmanName : string;                                 //卖家联系人姓名  
    sellerLinkmanPhone : string;                                //卖家联系人手机  
    sellerLinkmanEmail : string;                                //卖家联系人邮箱  
    sellerCompanyAddress : string;                              //卖家公司地址  
    sellerLegalPerson : string;                                 //卖家法人  
    sellerBankName : string;                                    //卖家开户银行  
    sellerBankAccount : string;                                 //卖家银行账号    


    constructor() {
    }
}

@Injectable()
class ContractService {

    constructor(
        private http: Http
    ) { }

    getContractById(id: number) {
        return this.http.get(API.risk + '/' + id).toPromise()
            .then(response => response.json() as HttpResponse)
            .catch(GlobalPromiseHttpCatch);
    }

    add(contract: Contract) {
        let headers = new Headers({'Content-Type': 'application/json'});

        return this.http.post(API.tasksMYD + '/riskmanager/audit/contract', JSON.stringify(contract), {headers: headers}).toPromise()
            .then(res => res.json() as HttpResponse )
            .catch(GlobalPromiseHttpCatch);
    }

}



export { ContractService, Contract }