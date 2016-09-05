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
    isApprovedRadio : boolean = false;
    isNoticeApplyUser : boolean = false;
    isNeedSupplyMaterial : boolean = false;

    css = {
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
                this.currentTask = result.data;
                console.log(this.currentTask);
                this.task.getSalesmanInfoById(this.currentTask.financeId).then((result)=>{
                    if (result.success){
                        this.currentOrder = result.data;
                        console.log(this.currentOrder);
                    }else{

                    }
                });
            }else{

            }
        });
    }


    audit (isAudit : boolean){

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
        this.currentOrder.noticeApplyUser = this.isNoticeApplyUser;
        this.currentOrder.needSupplyMaterial = this.isNeedSupplyMaterial;
        if (this.currentTask.taskDefinitionKey === TaskStatus.salesmanAudit) auditType = 'salesman'; // 业务员审核并填写材料

        if (this.currentTask.taskDefinitionKey && auditType) {
            console.log(this.currentTask);
            console.log(this.currentOrder);
            this.task.audit(this.taskId, this.currentTask.applyType, auditType, body).then((result)=>{
                if (result.success){
                    this.css.ajaxSuccessHidden=false;
                    setTimeout(() => this.css.ajaxSuccessHidden = true, 3000);
                }else{
                    this.css.ajaxErrorHidden=false;
                }
            });
        }

    }
    changeNoticeApplyUserStatus(){
        this.isNoticeApplyUser = !this.isNoticeApplyUser;
    }
    changeNeedSupplyMaterialStatus(){
        this.isNeedSupplyMaterial = !this.isNeedSupplyMaterial;
    }
}

