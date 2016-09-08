/**
 * Created by liushengbin on 16/8/15.
 */


import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Subscription } from 'rxjs/Subscription';

import { Task, TaskService, TaskStatus } from '../../service/task';
import { User, UserService } from '../../service/user';


declare var __moduleName: string;

@Component({
    selector: 'audit-trader',
    moduleId: __moduleName || module.id,
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
    isApprovedRadio : boolean ;


    constructor(
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
            if (this.routeData.routeType === 'traderInfo') {
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
                    }else{

                    }
                });

            }else{

            }
        });
    }


    finishedUpload (event) {
        this.currentOrder.attachmentList.push({
            "url": event.value.url,
            "name": event.value.name,
            "type": event.value.type,
            "processInstanceId": this.currentTask.processInstanceId,
            "taskId": this.currentTask.id
        })
    }

    audit (isAudit : boolean){

        this.css.ajaxErrorHidden = true;
        this.css.ajaxSuccessHidden = true;
        this.css.isSubmitted = true;

        let isApproved : boolean = false;
        if (isAudit) {
            isApproved = this.isApprovedRadio;
            if(!isApproved){
                this.css.ajaxErrorHidden = false;
                this.errorMsg = '请勾选审核意见'
            }
        }
        let auditType : string = '';
        let body : any = {
            t : {
                submit : isAudit === true ? 1 : 0,
                pass : isApproved === true ? 1 : 0,
                need : 0,
                need2 : 0
            },
            u : Object.assign({}, this.currentOrder)
        };


        if (this.currentTask.taskDefinitionKey === TaskStatus.onlineTraderAudit) auditType = 'onlinetrader'; //线上交易员审核并填写材料

        if (this.currentTask.taskDefinitionKey && auditType) {

            this.task.audit(this.taskId, this.currentOrder.applyType, auditType, body).then((result)=>{
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



}

