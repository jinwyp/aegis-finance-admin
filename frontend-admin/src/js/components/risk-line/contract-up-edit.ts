/**
 * Created by liushengbin on 16/8/15.
 */


import { Component } from '@angular/core';
import { Location } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';

import { Contract, ContractService } from '../../service/contract';

declare var __moduleName: string;

@Component({
    selector: 'contract-up-edit',
    moduleId: module.id,
    templateUrl: 'contract-up-edit.html'
})
export class ContractUpEditComponent {

    currentDate=new Date();
    private selectedDateInline : string = '';
    myDatePickerOptions = {
        todayBtnTxt: 'Today',
        dateFormat: 'yyyy-mm-dd',
        firstDayOfWeek: 'su',
        sunHighlight: true,
        height: '31px',
        width: '200px',
        inline: false,
        // disableUntil: {year: this.currentDate.getFullYear(), month: this.currentDate.getMonth()+1, day: this.currentDate.getDate()-1},
        disableUntil: {year: 1900, month: 1, day: 1},
        selectionTxtFontSize: '14px'
    };

    taskId : number = 0;
    status : number = 0;
    contract : Contract = new Contract();

    errorMsg : string = '';
    css = {
        ajaxSuccessHidden : true,
        ajaxErrorHidden : true,
        isSubmitted : false
    };


    private sub: Subscription;

    constructor(
        private location : Location,
        private activatedRoute : ActivatedRoute,
        private contractService : ContractService,
    ){}

    ngOnInit(){
        this.sub = this.activatedRoute.params.subscribe(param=>{
           console.log(param);
            this.contract.financeId = Number(param['financeId'])===NaN ? 0:Number(param['financeId']);
            this.taskId = Number(param['taskId'])===NaN ? 0:Number(param['taskId']);
            this.status = Number(param['status'])===NaN ? 0:Number(param['status']);
            this.getContractById(this.contract.financeId);
        });
    }

    getContractById(financeId : number){
        this.contractService.getContractById(financeId, 1).then(result=>{
            if(result.success&&result.data){
                this.selectedDateInline = result.data.signDate||'';
                this.contract = result.data;
                this.contract.sellerReceiptMoneyCapital = this.contractService.parserMoneyCN(this.contract.sellerReceiptMoney);
                this.contract.buyerSettlementMoneyCapital = this.contractService.parserMoneyCN(this.contract.buyerSettlementMoney);
                this.contract.attachmentNumber = this.contract.attachmentNumber===0 ? null : this.contract.attachmentNumber;
                console.log(this.contract);
            }
        });
    }

    save(type : number){
        console.log(this.contract);
        if(this.contractService.isEmpty(this.contract.buyerCompanyName)){
            this.errorMsg = '请填写买家公司名称';
            this.css.ajaxErrorHidden = false;
            return;
        }
        if(this.contractService.isEmpty(this.contract.sellerCompanyName)){
            this.errorMsg = '请填写卖家公司名称';
            this.css.ajaxErrorHidden = false;
            return;
        }
        if(type ===1 && this.contractService.isEmpty(this.selectedDateInline)){
            this.errorMsg = '请选择合同签订时间';
            this.css.ajaxErrorHidden = false;
            return;
        }
        if(!this.contractService.isEmpty(this.contract.coalIndex_NCV) && this.contract.coalIndex_NCV<1||this.contract.coalIndex_NCV>7500){
            this.errorMsg = '热值为1-7500之间的整数';
            this.css.ajaxErrorHidden = false;
            return;
        }
        if(!this.contractService.isEmpty(this.contract.coalIndex_RS) && this.contract.coalIndex_RS<0||this.contract.coalIndex_RS>=10){
            this.errorMsg = '硫分为0-10之间的值';
            this.css.ajaxErrorHidden = false;
            return;
        }
        if(!this.contractService.isEmpty(this.contract.buyerLinkmanPhone) && !this.contractService.checkPhone(this.contract.buyerLinkmanPhone)){
            this.errorMsg = '买家联系手机号码格式错误';
            this.css.ajaxErrorHidden = false;
            return;
        }
        if(!this.contractService.isEmpty(this.contract.buyerLinkmanEmail) && !this.contractService.checkEmail(this.contract.buyerLinkmanEmail)){
            this.errorMsg = '买家邮箱格式错误';
            this.css.ajaxErrorHidden = false;
            return;
        }
        if(!this.contractService.isEmpty(this.contract.sellerLinkmanPhone) && !this.contractService.checkPhone(this.contract.sellerLinkmanPhone)){
            this.errorMsg = '卖家联系手机号码格式错误';
            this.css.ajaxErrorHidden = false;
            return;
        }
        if(!this.contractService.isEmpty(this.contract.sellerLinkmanEmail) && !this.contractService.checkEmail(this.contract.sellerLinkmanEmail)){
            this.errorMsg = '卖家邮箱格式错误';
            this.css.ajaxErrorHidden = false;
            return;
        }

        this.contract.signDate = this.selectedDateInline;
        this.contract.type = 1;
        console.log(this.contract);
        this.contractService.add(this.contract, this.taskId, type).then(result=>{
            if(result.success){
                this.goBack();
            }
        });
    }


    onDateChanged(event:any) {
        this.contract.signDate = event.formatted;
        this.selectedDateInline = event.formatted;
    }

    setSellerReceiptMoneyCN(){
        let money;
        if(this.contract.sellerReceiptPrice==''||this.contract.sellerReceiptPrice==null||this.contract.sellerReceiptAmount==''||this.contract.sellerReceiptAmount==null){
            money = null;
            this.contract.sellerReceiptMoney = null;
        }else{
            money = this.contractService.floatMul(this.contract.sellerReceiptPrice, this.contract.sellerReceiptAmount);
            this.contract.sellerReceiptMoney = money;
        }
        console.log(money);
        this.contract.sellerReceiptMoneyCapital=this.contractService.parserMoneyCN(money);
    }

    setBuyerSettlementMoneyCN(money){
        this.contract.buyerSettlementMoneyCapital=this.contractService.parserMoneyCN(money);
    }

    goBack(){
        this.location.back();
    }

}

