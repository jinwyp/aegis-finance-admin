/**
 * Created by liushengbin on 16/8/15.
 */


import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Subscription } from 'rxjs/Subscription';

import { TaskService } from '../../service/task';
import { User, UserService } from '../../service/user';




declare var __moduleName: string;

@Component({
    selector: 'audit-investigator',
    moduleId: __moduleName || module.id,
    templateUrl: 'audit-investigator.html'
})



export class AuditInvestigatorComponent {

    private sub: Subscription;

    currentUserSession : User = new User();

    taskId : string = '';
    taskStatus : string = '';
    taskProcessInstanceId : string = '';

    constructor(
        private activatedRoute: ActivatedRoute,
        private task: TaskService,
        private user: UserService
    ) {}


    ngOnInit(){
        this.sub = this.activatedRoute.params.subscribe(params => {
            this.taskId = params['id'];
        });

        this.activatedRoute.queryParams.subscribe(params => {
            this.taskStatus = params['status'];
            this.taskProcessInstanceId = params['processInstanceId'];
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

    audit (){

        let auditType : string = '';

        if (this.taskStatus === '尽调员审核') auditType = 'investigator';

        if (this.taskStatus && auditType) {
            this.task.audit(this.taskId, auditType, 1).then((result)=>{
                if (result.success){
                    alert('保存成功!!')

                }else{
                    alert('保存失败!')
                }
            });
        }

    }


    reportType=1;

}

