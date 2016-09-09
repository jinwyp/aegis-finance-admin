/**
 * Created by liushengbin on 16/8/15.
 */


import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { Subscription } from 'rxjs/Subscription';

import { Task, TaskService, TaskStatus } from '../../service/task';
import { User, UserService } from '../../service/user';
import {isNumber} from "@angular/core/testing/facade/lang";




declare var __moduleName: string;

@Component({
    selector: 'audit-investigator',
    moduleId: __moduleName || module.id,
    templateUrl: 'audit-investigator.html'
})
export class AuditInvestigatorComponent {

    private sub: Subscription;

    css = {
        isSubmitted : false,
        ajaxErrorHidden : true,
        ajaxSuccessHidden : true,
        isReadOnly : false
    };

    routeData :any = {
        routeType : '',
        title : ''
    };

    errorMsg = '';

    currentUserSession : User = new User();

    taskId : string = '';
    currentTask : Task = new Task();
    currentOrder : Task = new Task();

    isApprovedRadio : number = -1;

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
            if (this.routeData.routeType === 'info') {this.css.isReadOnly = true;}


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
                this.task.getOrderInfoById(this.currentTask.financeId, 'investigator').then((result)=>{
                    if (result.success && result.data ){
                        this.currentOrder = result.data;
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
            this.errorMsg = '融资金额应输入1-100000之间的数字';
            this.css.isSubmitted = false;
            return;
        }
        if(isNaN(Number(this.currentOrder.financingPeriod))||(Number(this.currentOrder.financingPeriod)<0||Number(this.currentOrder.financingPeriod)>365)){
            this.css.ajaxErrorHidden = false;
            this.errorMsg = '融资期限应输入1-365之间的整数';
            this.css.isSubmitted = false;
            return;
        }
        if(isNaN(Number(this.currentOrder.interestRate))){
            this.css.ajaxErrorHidden = false;
            this.errorMsg = '利率应输入大于0的数字';
            this.css.isSubmitted = false;
            return;
        }

        let body : any = {
            t : {
                pass : this.isApprovedRadio,
                need : this.isApprovedRadio === 2 ? 1 : 0
            },
            u : this.currentOrder
        };

        if (this.isApprovedRadio === 2) {
            this.currentOrder.needSupplyMaterial = 1;
            body.t.pass = 0;
        }

        let auditType : string = '';
        if (this.currentTask.taskDefinitionKey === TaskStatus.investigatorAudit) auditType = 'investigator'; // 尽调员审核

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
                    this.css.ajaxErrorHidden=false;
                    this.errorMsg = result.error.message;
                }
            });
        }

    }

    finishedUpload (event) {
        if (!this.currentOrder.attachmentList1) {this.currentOrder.attachmentList1 = []}
        this.currentOrder.attachmentList1.push({
            "url": event.value.url,
            "name": event.value.name,
            "type": event.value.type,
            "processInstanceId": this.currentTask.processInstanceId,
            "taskId": this.currentTask.id
        })
    }

    delAttachmentList1(file, isAttachmentList2 : boolean = false){
        let index = this.currentOrder.attachmentList1.indexOf(file);

        if (isAttachmentList2) { index = this.currentOrder.attachmentList2.indexOf(file);}
        if (index > -1){
            if (isAttachmentList2){
                this.currentOrder.attachmentList2.splice(index, 1);
            }else {
                this.currentOrder.attachmentList1.splice(index, 1);
            }
        }
    }

    goBack() {
        this.location.back();
    }

}

