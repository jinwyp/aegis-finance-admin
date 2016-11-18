/**
 * Created by JinWYP on 8/11/16.
 */

import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import {GlobalPromiseHttpCatch, HttpResponse, API } from './http';

class Contract {

    id : number = 1;                                                //主键  
    financeId : number = 1;                                         //金融单id  
    contractNo : string = 'aaa';                                        //合同编号  
    type : number = 1;                                              //合同类型
    signPlace : string = 'aaa';                                         //签订地点  
    signDate : string = 'aaa';                                          //签订时间  
    signDate_Year : any = '2016';
    signDate_Month : any = '11';
    signDate_Day : any = '21';
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
    coalIndex_NCV : any = 1000;                                         //煤炭指标  
    coalIndex_RS : any = 5;                                         //煤炭指标  
    qualityRemark : string = 'aaa';                                     //质量备注/质量说明  
    quantityAcceptanceCriteria : string = 'aaa';                        //数量验收标准  
    qualityAcceptanceCriteria : string = 'aaa';                         //质量验收标准  
    paymentPeriod : number = 1;                                     //付款提货期限
    settlementPrice : number = 1;                                   //结算价格  
    settlementAmount : number = 1;                                  //结算吨数
    cashDeposit : number = 1;                                       //保证金
    cashDepositCapital : string ='壹万元整';                            //中文保证金
    sellerReceiptPrice : any = 1;                                //卖家开票价格  
    sellerReceiptAmount : any = 1;                               //卖家开票吨数  
    sellerReceiptMoney : any = 1;                                //卖家开票金额  
    sellerReceiptMoneyCapital : string ='壹万元整';                            //中文保证金
    buyerSettlementMoney : number = 1;                              //买家已经结清金额
    buyerSettlementMoneyCapital : string ='壹万元整';                            //中文保证金//   
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


    coalSupplier : string = '供应商';                                 //供应商    
    upstreamContractNo : string = '上游合同编号';                                 //上游合同编号    

    quantityAcceptanceBasis : any = '2000';                              //数量验收依据    


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

    parserMoneyCN(money) {
        let moneyStr = '';
        if (money === null || money === '' || money.length === 0) {
            moneyStr = '';
        } else if (/^(0|[1-9]\d*)(\.\d{1,2})?$/.test(money)) {
            if (Number(money) === 0) {
                moneyStr = '零元整';
            } else {
                var unit = "千百拾亿千百拾万千百拾元角分", str = "";
                money += "00";
                var p    = money.indexOf('.');
                if (p >= 0) {
                    money = money.substring(0, p) + money.substr(p + 1, 2);
                }
                unit = unit.substr(unit.length - money.length);
                for (var i = 0; i < money.length; i++)
                    str += '零壹贰叁肆伍陆柒捌玖'.charAt(money.charAt(i)) + unit.charAt(i);

                moneyStr = str.replace(/零(千|百|拾|角)/g, "零").replace(/(零)+/g, "零").replace(/零(万|亿|元)/g, "$1").replace(/(亿)万|壹(拾)/g, "$1$2").replace(/^元零?|零分/g, "").replace(/元$/g, "元整");
            }
        } else {
            moneyStr = '金额格式错误';
        }
        return moneyStr;
    }

    checkPhone(phone : string){
        phone = phone.replace(/\s+/g,"");
        if(/^0?(13[0-9]|15[0-9]|17[0-9]|18[0-9]|14[57])[0-9]{8}$/.test(phone)){
            return true;
        }else{
            return false;
        }
    }

    checkEmail(email : string){
        email = email.replace(/\s+/g,"");
        if(/^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/.test(email)){
            return true;
        }else{
            return false;
        }
    }

    floatMul(arg1, arg2) {
        let m=0,s1=arg1.toString(),s2=arg2.toString();
        try{m+=s1.split(".")[1].length}catch(e){}
        try{m+=s2.split(".")[1].length}catch(e){}
        return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)

    }

}



export { ContractService, Contract }