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
    money : string = '';
    moneyZh : string = '';


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
            // console.log(typeof this.taskId);
            // console.log(this.taskId);
            // console.log(typeof this.contract.financeId);
            // console.log(this.contract.financeId);
        });
    }

    getContractById(financeId : number){
        this.contractService.getContractById(financeId, 1).then(result=>{
            if(result.success&&result.data){
                this.contract = result.data;
                this.selectedDateInline = result.data.signDate;
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

    parserString(n){
        console.log(n);
        if(n===''){
            return;
        }
        if (!/^(0|[1-9]\d*)(\.\d+)?$/.test(n)){
            this.moneyZh = '';
            console.log('1');
        }

        if(n==0||n==='0'){
            this.moneyZh = '零元整';
            console.log('2');
        }
        var unit = "千百拾亿千百拾万千百拾元角分", str = "";
        n += "00";
        var p    = n.indexOf('.');
        if (p >= 0){
            n = n.substring(0, p) + n.substr(p + 1, 2);
        }
        unit = unit.substr(unit.length - n.length);
        for (var i = 0; i < n.length; i++)
            str += '零壹贰叁肆伍陆柒捌玖'.charAt(n.charAt(i)) + unit.charAt(i);

        this.moneyZh = str.replace(/零(千|百|拾|角)/g, "零").replace(/(零)+/g, "零").replace(/零(万|亿|元)/g, "$1").replace(/(亿)万|壹(拾)/g, "$1$2").replace(/^元零?|零分/g, "").replace(/元$/g, "元整");
        // return str.replace(/零(千|百|拾|角)/g, "零").replace(/(零)+/g, "零").replace(/零(万|亿|元)/g, "$1").replace(/(亿)万|壹(拾)/g, "$1$2").replace(/^元零?|零分/g, "").replace(/元$/g, "元整");

        console.log(this.moneyZh);
    }

    goBack(){
        this.location.back();
    }

}

