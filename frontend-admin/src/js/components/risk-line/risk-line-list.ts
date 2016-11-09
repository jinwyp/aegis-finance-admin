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
    companyId : number;



    name:string = '';
    username:string = '';
    groupId:string = '';
    selectedItem = {id : null, name : null};
    groups       = [];
    isHiddenDelModal : boolean = true;
    isHiddenResetModal : boolean = true;
    isHiddenMsgModal : boolean = true;
    btnClick : boolean    = false;
    userId : string       = '';
    modalShowText : string       = '';


    ngOnInit() {
        this.pageObj = new Page();
        this.getRiskLineList(this.riskLine, this.pageObj)
    }

    getRiskLineList(riskLine : RiskLine, pageObj : Page){
        this.riskService.getRiskLineList(riskLine.name, riskLine.adminName, pageObj.page).then((result)=>{
            if(result.success){
                console.log(result.data);
                this.riskLineList = result.data;
            }
        });
    }


    // showDelModal(id:string, modalShowText:string) {
    //     this.isHiddenDelModal = false;
    //     this.modalShowText = modalShowText;
    //     this.userId = id;
    // }

    showResetModal(id:number, modalShowText:string) {
        this.isHiddenResetModal = false;
        this.modalShowText = modalShowText;
        this.companyId = id;
    }

    hiddenModal() {
        this.companyId = -1;
    }

    // delUser(){
    //     if (this.userId){
    //         this.user.del(this.userId).then((result)=> {
    //             if (result.success) {
    //                 // this.pageObj=new Page();
    //                 this.getUserList(this.pageObj.page);
    //             } else {
    //
    //             }
    //         });
    //     }
    // }
    //

    //
    // resetPwd(){
    //     if (this.userId){
    //         this.user.resetPassword(this.userId).then((result)=> {
    //             if (result.success) {
    //                 this.modalShowText='重置密码成功!';
    //             } else {
    //                 this.modalShowText=result.error.message;
    //             }
    //             this.isHiddenMsgModal=false;
    //         });
    //     }
    // }
    //

}

