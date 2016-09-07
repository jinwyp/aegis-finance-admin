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
    selector: 'audit-salesman',
    moduleId: __moduleName || module.id,
    templateUrl: 'audit-salesman.html'
})
export class AuditSalesmanComponent {

    private sub: Subscription;

    currentUserSession : User = new User();

    taskId : string = '';
    currentTask : Task = new Task();
    currentOrder : Task = new Task();
    isApprovedRadio : boolean ;

    css = {
        isSubmitted : false,
        ajaxSuccessHidden : true,
        ajaxErrorHidden : true,
    };
    errorMsg : string ='';


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
                console.log(result.data);
                this.currentTask = result.data;
                this.task.getOrderInfoById(this.currentTask.financeId, 'salesman').then((result)=>{
                    if (result.success && result.data){
                        console.log(result.data);
                        this.currentOrder = result.data;
                    }else{

                    }
                });
            }else{

            }
        });
    }


    audit (isAudit : boolean){

        this.css.isSubmitted = true;
        this.css.ajaxErrorHidden = true;
        this.css.ajaxSuccessHidden = true;

        let isApproved : boolean = false;
        if (isAudit) {
            isApproved = this.isApprovedRadio;
        }

        let auditType : string = '';
        let body : any = {
            t : {
                submit : isAudit === true ? 1 : 0,
                pass : isApproved === true ? 1 : 0,
                need : 0,
                need2 : 0
            },
            u : this.currentOrder
        };
        if (this.currentTask.taskDefinitionKey === TaskStatus.salesmanAudit) auditType = 'salesman'; // 业务员审核并填写材料

        if (this.currentTask.taskDefinitionKey && auditType) {
            this.task.audit(this.taskId, this.currentTask.applyType, auditType, body).then((result)=>{
                if (result.success){
                    if(!isAudit){
                        this.css.isSubmitted = false;
                    }
                    this.css.ajaxSuccessHidden=false;
                    setTimeout(() => this.css.ajaxSuccessHidden = true, 3000);
                }else{
                    this.css.isSubmitted = false;
                    this.css.ajaxErrorHidden=false;
                    this.errorMsg = JSON.parse(result).error.message;
                }

            });
        }

    }
    // changeNoticeApplyUserStatus(){
    //     if(this.currentOrder.noticeApplyUser===0){
    //         this.currentOrder.noticeApplyUser=1;
    //     }else{
    //         this.currentOrder.noticeApplyUser=0;
    //     }
    // }

    changeNeedSupplyMaterialStatus(){
        if(this.currentOrder.needSupplyMaterial===0){
            this.currentOrder.needSupplyMaterial=1;
        }else{
            this.currentOrder.needSupplyMaterial=0;
        }
    }
}

