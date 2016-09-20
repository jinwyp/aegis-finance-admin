/**
 * Created by liushengbin on 16/8/15.
 */


import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { Subscription } from 'rxjs/Subscription';

import { Task, TaskService, TaskStatus } from '../../service/task';
import { User, UserService } from '../../service/user';


declare var __moduleName: string;

@Component({
    selector: 'audit-trader',
    moduleId: module.id,
    templateUrl: 'audit-trader.html'
})
export class AuditTraderComponent {

    private sub: Subscription;

    css = {
        isReadOnly : false,
        isSubmitted : false,
        ajaxErrorHidden : true,
        ajaxSuccessHidden : true
    };
    routeData :any = {
        routeType : '',
        title : ''
    };
    errorMsg : string ='';

    currentUserSession : User = new User();

    taskId : string = '';
    currentTask : Task = new Task();
    currentOrder : Task = new Task();
    isApprovedRadio : number =-1;


    constructor(
        private location: Location,
        private activatedRoute: ActivatedRoute,
        private task: TaskService,
        private user: UserService
    ) {}


    ngOnInit(){
        this.sub = this.activatedRoute.params.subscribe(params => {
            this.taskId = params['id'];
            this.getTaskInfo(params['id']);
        });
        this.activatedRoute.data.subscribe( data => {
            this.routeData = data;
            if (this.routeData.routeType === 'info') {
                this.css.isReadOnly = true;
            }
        });

        this.getCurrentUser();
    }


    getCurrentUser() {
        this.user.getUserSessionObservable.subscribe(
            result => {
                if (result && result.success) {
                    this.currentUserSession = result.data;
                } else {

                }
            },
            error => console.error(error)
        )
    }

    getTaskInfo (id) {
        this.task.getTaskInfoById(id).then((result)=>{
            if (result.success){
                this.currentTask = result.data;

                this.task.getOrderInfoById(this.currentTask.financeId, 'onlinetrader').then((result)=>{
                    if (result.success){
                        this.currentOrder = result.data;
                        if(this.currentOrder.expectDate===0){
                            this.currentOrder.expectDate=null;
                        }
                    }else{

                    }
                });

            }else{

            }
        });
    }




    audit (isAudit : boolean){

        this.css.ajaxErrorHidden = true;
        this.css.ajaxSuccessHidden = true;
        this.css.isSubmitted = true;

        if(isNaN(Number(this.currentOrder.financingAmount))){
            this.css.ajaxErrorHidden = false;
            this.errorMsg = '拟融资金额应输入1-100000之间的数字';
            this.css.isSubmitted = false;
            return;
        }
        if(isNaN(Number(this.currentOrder.expectDate))||(Number(this.currentOrder.expectDate)<0||Number(this.currentOrder.expectDate)>365)){
            this.css.ajaxErrorHidden = false;
            this.errorMsg = '拟使用资金时间应输入1-365之间的整数';
            this.css.isSubmitted = false;
            return;
        }
        if(isNaN(Number(this.currentOrder.businessAmount))){
            this.css.ajaxErrorHidden = false;
            this.errorMsg = '预期此笔业务量应输入1-100000之间的数字';
            this.css.isSubmitted = false;
            return;
        }
        if(this.currentOrder.applyType==='MYR'){
            if(isNaN(Number(this.currentOrder.sellingPrice))){
                this.css.ajaxErrorHidden = false;
                this.errorMsg = '预计单吨销售价应输入大于0的数字';
                this.css.isSubmitted = false;
                return;
            }
        }else if(this.currentOrder.applyType==='MYD'){
            if(isNaN(Number(this.currentOrder.marketPrice))){
                this.css.ajaxErrorHidden = false;
                this.errorMsg = '单吨市场报价应输入大于0的数字';
                this.css.isSubmitted = false;
                return;
            }
        }else if(this.currentOrder.applyType==='MYG'){
            if(isNaN(Number(this.currentOrder.procurementPrice))){
                this.css.ajaxErrorHidden = false;
                this.errorMsg = '单吨采购价应输入大于0的数字';
                this.css.isSubmitted = false;
                return;
            }
        }

        let auditType : string = '';
        let body : any = {
            t : {
                pass : this.isApprovedRadio,
                need : 0,
                need2 : 0
            },
            u : Object.assign({}, this.currentOrder)
        };


        if (this.currentTask.taskDefinitionKey === TaskStatus.onlineTraderAudit) auditType = 'onlinetrader'; //线上交易员审核并填写材料

        if (this.currentTask.taskDefinitionKey && auditType) {

            this.task.audit(this.taskId, this.currentTask.applyType, auditType, isAudit, body).then((result)=>{
                if (result.success){
                    if(!isAudit){
                        this.css.isSubmitted = false;
                    }

                    this.css.ajaxSuccessHidden = false;
                    setTimeout(() => this.css.ajaxSuccessHidden = true, 5000);
                }else{
                    this.css.isSubmitted = false;
                    this.css.ajaxErrorHidden = false;
                    this.errorMsg = result.error.message;
                }

            });
        }
    }


    finishedUpload (event) {
        this.currentOrder.attachmentList1.push({
            "url": event.value.url,
            "name": event.value.name,
            "type": event.value.type,
            "processInstanceId": this.currentTask.processInstanceId,
            "taskId": this.currentTask.id
        })
    }

    delAttachmentList1(file){
        let index = this.currentOrder.attachmentList1.indexOf(file);
        if (index > -1){
            this.currentOrder.attachmentList1.splice(index, 1);
        }
    }


    goBack() {
        this.location.back();
    }

}

