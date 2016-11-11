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
    selector: 'audit-riskmanager',
    moduleId: module.id,
    templateUrl: 'audit-riskmanager.html'
})
export class AuditRiskManagerComponent {

    private sub: Subscription;

    currentUserSession : User = new User();

    isApprovedRadio : number;

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

    taskId : string = '';
    currentTask : Task = new Task();
    currentOrder : Task = new Task();

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
                this.task.getOrderInfoById(this.currentTask.financeId, 'riskmanager').then((result)=>{
                    if (result.success && result.data){
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
        if (this.currentTask.taskDefinitionKey === TaskStatus.riskManagerAudit) auditType = 'riskmanager'; // 风控人员审核

        if (this.currentTask.taskDefinitionKey && auditType) {
            this.task.audit(this.taskId, this.currentTask.applyType, auditType, isAudit, body).then((result)=>{
                if (result.success){
                    if(!isAudit){
                        this.css.isSubmitted = false;
                    }
                    this.css.ajaxSuccessHidden=false;
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

    previewContract(){
        // window.open('http://finance-local.yimei180.com:8002/finance/admin/home/contract/10/edit');
        window.open('http://finance-local.yimei180.com:8002/finance/admin/contract');
    }

    editUp(){
        window.open('http://finance-local.yimei180.com:8002/finance/admin/home/contractup/'+this.taskId+'/edit');
    }

    editDown(){
        window.open('http://finance-local.yimei180.com:8002/finance/admin/home/contractdown/'+this.taskId+'/edit');
    }

}

