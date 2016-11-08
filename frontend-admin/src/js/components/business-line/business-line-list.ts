/**
 * Created by liushengbin on 16/8/15.
 */


import {Component} from '@angular/core';

import { Page } from '../../service/task';
import {FundService,FundCompany} from "../../service/fund";


declare var __moduleName:string;

@Component({
    selector :    'business-line-list',
    moduleId :    module.id,
    templateUrl : 'business-line-list.html'
})
export class BusinessLiseListComponent {

    constructor(
        private fundService:FundService
    ) {}

    pageObj : Page;
    businessLine = new FundCompany();
    businessLineList : FundCompany[];
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
        this.getBusinessLineList(this.businessLine, this.pageObj)
    }

    getBusinessLineList(fundCompany : FundCompany, pageObj : Page){
        this.fundService.getBusinessLineList(fundCompany.name, fundCompany.adminName, pageObj.page).then((result)=>{
            if(result.success){
                this.businessLineList = result.data;
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

