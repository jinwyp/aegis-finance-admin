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
    selector: 'contract-down-edit',
    moduleId: module.id,
    templateUrl: 'contract-down-edit.html'
})
export class ContractDownEditComponent {

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

    taskId : string = '';
    contract : Contract = new Contract();


    private sub: Subscription;

    constructor(
        private location : Location,
        private activatedRoute : ActivatedRoute,
        private contractService : ContractService,
    ){}

    ngOnInit(){
        this.sub = this.activatedRoute.params.subscribe(param=>{
            console.log(param);
            this.contract.financeId = param['financeId'];
            this.taskId = param['taskId'];
            this.getContractById(param['financeId']);
        });
    }

    getContractById(financeId : number){
        this.contractService.getContractById(financeId, 2).then(result=>{
            if(result.success&&result.data){
                this.contract = result.data;
                this.selectedDateInline = result.data.signDate||'';
            }
        });
    }

    save(type : number){
        this.contract.signDate = this.selectedDateInline;
        this.contract.type = 2;
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

    cashDepositCN = '';

    setMoneyCN(money){
        this.cashDepositCN=this.contractService.parserMoneyCN(money);
    }

    goBack(){
        this.location.back();
    }

}

