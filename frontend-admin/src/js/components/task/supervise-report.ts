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
    selector: 'supervise-report',
    moduleId: __moduleName || module.id,
    templateUrl: 'supervise-report.html'
})
export class SuperviseReportComponent {

    private sub: Subscription;

    css = {
        isSubmitted : false,
        isCommitted : false,
        ajaxErrorHidden : true,
        ajaxSuccessHidden : true
    };
    errorMsg : string ='';
    currentUserSession:User = new User();
    currentOrder : Task = new Task();
    taskId : string = '';
    currentTask : Task = new Task();
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


        if (this.currentTask.taskDefinitionKey === TaskStatus.supervisorAudit) auditType = 'supervisor'; //监管员审核并填写材料

        if (this.currentTask.taskDefinitionKey && auditType) {

            this.task.audit(this.taskId, this.currentOrder.applyType, auditType, body).then((result)=>{
                if (result.success){
                    if(isAudit){
                        this.css.isCommitted = true;
                    }
                    // this.getTaskInfo(this.taskId);
                    this.css.ajaxSuccessHidden = false;
                    setTimeout(() => this.css.ajaxSuccessHidden = true, 5000);
                }else{
                    this.css.ajaxErrorHidden = false;
                    this.errorMsg = result.error.message;
                }
                this.css.isSubmitted = false;
            });
        }
    }


}

