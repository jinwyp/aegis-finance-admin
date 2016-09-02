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
                this.task.getOrderInfoById(this.currentTask.financeId).then((result)=>{
                    if (result.success){
                        this.currentOrder = result.data;
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
            u : this.currentTask
        };

        if (this.currentTask.taskDefinitionKey === TaskStatus.salesmanAudit) auditType = 'salesman'; // 业务员审核并填写材料

        if (this.currentTask.taskDefinitionKey && auditType) {
            this.task.audit(this.taskId, this.currentTask.applyType, auditType, body).then((result)=>{
                if (result.success){
                    alert('保存成功!!')
                }else{
                    alert('保存失败!')
                }
            });
        }

    }
}

