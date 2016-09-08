/**
 * Created by liushengbin on 16/8/15.
 */


import { Component } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { ActivatedRoute } from '@angular/router';

import { Task, TaskService } from '../../service/task';

declare var __moduleName: string;


@Component({
    selector: 'material-detail',
    moduleId: __moduleName || module.id,
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

        this.task.addMaterial(this.taskId, this.currentTask.applyType, 'salesman1', this.currentOrder.attachmentList1).then((result)=>{
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
        this.currentOrder.attachmentList1.push({
            "url": event.value.url,
            "name": event.value.name,
            "type": event.value.type,
            "processInstanceId": this.currentTask.processInstanceId,
            "taskId": this.currentTask.id
        })
    }

}

