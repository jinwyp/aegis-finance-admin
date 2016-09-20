/**
 * Created by liushengbin on 16/8/15.
 */


import { Component } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { ActivatedRoute } from '@angular/router';

import { Task, TaskService, TaskStatus } from '../../service/task';

declare var __moduleName: string;


@Component({
    selector: 'material-detail',
    moduleId: module.id,
    templateUrl: 'material-detail.html'
})
export class MaterialDetailComponent {

    private sub: Subscription;

    css = {
        isSubmitted : false,
        ajaxErrorHidden : true,
        ajaxSuccessHidden : true,
        isReadOnly : false
    };

    errorMsg = '';

    taskId : string = '';

    currentTask : Task = new Task();
    currentOrder : Task = new Task();
    attachmentList : Array<any> = [];

    constructor(
        private activatedRoute: ActivatedRoute,
        private task: TaskService
    ) {}


    ngOnInit(){
        this.sub = this.activatedRoute.params.subscribe(params => {
            this.taskId = params['id'];
            this.getTaskInfo(params['id']);
        });
    }

    getTaskInfo (id) {
        this.task.getTaskInfoById(id).then((result)=>{
            if (result.success){
                this.currentTask = result.data;

                this.task.getOrderInfoById(this.currentTask.financeId).then((result)=>{
                    if (result.success) {
                        this.currentOrder = result.data;
                    }else{

                    }
                });

            }else{

            }
        });
    }



    save ( isAudit : boolean) {

        this.css.ajaxErrorHidden = true;
        this.css.ajaxSuccessHidden = true;
        this.css.isSubmitted = true;

        let auditType : string = '';
        if (this.currentTask.taskDefinitionKey === TaskStatus.salesmanSupplyInvestigationMaterial) auditType = 'salesmanInvestigation'; // 业务员补充尽调资料
        if (this.currentTask.taskDefinitionKey === TaskStatus.salesmanSupplySupervisionMaterial) auditType = 'salesmanSupervision'; // 业务员补充监管资料
        if (this.currentTask.taskDefinitionKey === TaskStatus.salesmanSupplyRiskManagerMaterial) auditType = 'salesmanRiskmanager'; // 业务员补充风控资料


        this.task.addMaterial(this.taskId, this.currentTask.applyType, auditType, this.attachmentList).then((result)=>{
            if (result.success){
                this.css.ajaxSuccessHidden = false;
                setTimeout(() => this.css.ajaxSuccessHidden = true, 5000);
            }else{

                this.css.isSubmitted = false;
                this.css.ajaxErrorHidden = false;
                this.errorMsg = result.error.message;
            }

        });

    }


    finishedUpload (event) {
        if (!this.attachmentList) {this.attachmentList = []}
        this.attachmentList.push({
            "url": event.value.url,
            "name": event.value.name,
            "type": event.value.type,
            "processInstanceId": this.currentTask.processInstanceId,
            "taskId": this.currentTask.id
        })
    }

    delAttachmentList1(file){
        let index = this.attachmentList.indexOf(file);
        if (index > -1){
            this.attachmentList.splice(index, 1);
        }
    }

}

