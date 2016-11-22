/**
 * Created by liushengbin on 16/8/15.
 */


import {Component} from '@angular/core';

import { Page } from '../../service/task';
import {RiskService,RiskLine} from "../../service/risk";


declare var __moduleName:string;

@Component({
    selector :    'risk-line-list',
    moduleId :    module.id,
    templateUrl : 'risk-line-list.html'
})
export class RiskLineListComponent {

    constructor(
        private riskService:RiskService
    ) {}

    pageObj : Page;
    riskLine = new RiskLine();
    riskLineList : RiskLine[];
    riskLineId : number;


    isHiddenDelModal : boolean = true;
    modalShowText : string       = '';


    ngOnInit() {
        this.pageObj = new Page();
        this.getRiskLineList();
    }

    getRiskLineList(){
        this.riskService.getRiskLineList(this.riskLine.name, this.riskLine.adminName, this.pageObj.page).then((result)=>{
            if(result.success){
                this.riskLineList = result.data;
            }
        });
    }


    showDelModal(id : number, modalShowText : string) {
        this.isHiddenDelModal = false;
        this.modalShowText = modalShowText;
        this.riskLineId = id;
    }

    hiddenModal() {
        this.riskLineId = -1;
    }

    delRiskLine(){
        if (this.riskLineId){
            this.riskService.del(this.riskLineId).then((result)=> {
                if (result.success) {
                    this.pageObj=new Page();
                    this.getRiskLineList();
                } else {

                }
            });
        }
    }


}

