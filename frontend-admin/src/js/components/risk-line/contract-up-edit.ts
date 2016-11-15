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
        disableUntil: {year: this.currentDate.getFullYear(), month: this.currentDate.getMonth()+1, day: this.currentDate.getDate()-1},
        selectionTxtFontSize: '14px'
    };

    taskId : number = 0;
    contract : Contract = new Contract();
    sellerReceiptMoneyCN : string ='';
    buyerSettlementMoneyCN : string ='';


    private sub: Subscription;

    constructor(
        private location : Location,
        private activatedRoute : ActivatedRoute,
        private contractService : ContractService,
    ){}

    ngOnInit(){
        this.sub = this.activatedRoute.params.subscribe(param=>{
           console.log(param);
            this.contract.financeId = Number(param['financeId']);
            this.taskId = Number(param['taskId']);
            this.getContractById(this.contract.financeId);
        });
    }

    getContractById(financeId : number){
        this.contractService.getContractById(financeId, 1).then(result=>{
            if(result.success&&result.data){
                this.selectedDateInline = result.data.signDate||'';
                this.contract = result.data;
                this.sellerReceiptMoneyCN = this.contractService.parserMoneyCN(this.contract.sellerReceiptMoney);
                this.buyerSettlementMoneyCN = this.contractService.parserMoneyCN(this.contract.buyerSettlementMoney);
                console.log(this.contract);
            }
        });
    }

    save(type : number){
        this.contract.signDate = this.selectedDateInline;
        this.contract.type = 1;
        console.log(this.contract);
        this.contractService.add(this.contract, this.taskId, type).then(result=>{
            if(result.success){
                this.goBack();
            }
        });
    }

    saveAndCommitContract(type : number){
        this.contract.signDate = this.selectedDateInline;
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
        if(this.contract.sellerReceiptPrice==''||this.contract.sellerReceiptAmount==''){
            money = '';
            this.contract.sellerReceiptMoney = '';
        }else{
            money = this.contract.sellerReceiptPrice*100*this.contract.sellerReceiptAmount*100;
            this.contract.sellerReceiptMoney = money/(100*100);
        }
        console.log(money);
        this.sellerReceiptMoneyCN=this.contractService.parserMoneyCN(money);
    }

    setBuyerSettlementMoneyCN(money){
        this.buyerSettlementMoneyCN=this.contractService.parserMoneyCN(money);
    }

    goBack(){
        this.location.back();
    }

}

