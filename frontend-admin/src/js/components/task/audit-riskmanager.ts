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
    selector: 'audit-riskmanager',
    moduleId: __moduleName || module.id,
    templateUrl: 'audit-riskmanager.html'
})



export class AuditRiskManagerComponent {

    private sub: Subscription;

    currentUserSession : User = new User();

    taskId : string = '';
    currentTask : Task = new Task();

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
            }else{

            }
        });
    }


    audit (){

        let auditType : string = '';

        if (this.currentTask.taskDefinitionKey === TaskStatus.riskManagerAudit) auditType = 'riskmanager'; // 风控人员审核

        if (this.currentTask.taskDefinitionKey && auditType) {
            this.task.audit(this.taskId, auditType, 1).then((result)=>{
                if (result.success){
                    alert('保存成功!!')

                }else{
                    alert('保存失败!')
                }
            });
        }

    }


}

