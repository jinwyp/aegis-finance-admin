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
    selector: 'audit-investigator',
    moduleId: __moduleName || module.id,
    templateUrl: 'audit-investigator.html'
})
export class AuditInvestigatorComponent {

    private sub: Subscription;

    css = {
        isSubmitted : false,
        isCommitted : false,
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
                console.log(result.data);
                this.currentTask = result.data;
                this.task.getOrderInfoById(this.currentTask.financeId, 'investigator').then((result)=>{
                    if (result.success){
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

        this.css.ajaxErrorHidden = true;
        this.css.ajaxSuccessHidden = true;
        this.css.isSubmitted = true;

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

        if (this.currentTask.taskDefinitionKey === TaskStatus.investigatorAudit) auditType = 'investigator'; // 尽调员审核

        if (this.currentTask.taskDefinitionKey && auditType) {
            this.task.audit(this.taskId, this.currentTask.applyType, auditType, body).then((result)=>{
                if (result.success){
                    if(isAudit){
                        this.css.isCommitted = true;
                    }
                    this.css.ajaxSuccessHidden=false;
                    setTimeout(() => this.css.ajaxSuccessHidden = true, 3000);
                }else{
                    this.css.ajaxErrorHidden=false;
                    this.errorMsg=result.error.message;
                }
                this.css.isSubmitted = false;
            });
        }

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

    changeNeedSupplyMaterialStatus(){
        if(this.currentOrder.needSupplyMaterial===0){
            this.currentOrder.needSupplyMaterial=1;
        }else{
            this.currentOrder.needSupplyMaterial=0;
        }
    }


    reportType=1;

}

