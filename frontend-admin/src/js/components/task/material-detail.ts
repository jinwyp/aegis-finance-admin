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
        // this.getCurrentUser();
    }

    // getCurrentUser() {
    //     this.user.getUserSessionObservable.subscribe(
    //         result => {
    //             if (result && result.success) {
    //                 this.currentUserSession = result.data;
    //             } else {
    //
    //             }
    //         },
    //         error => console.error(error)
    //     )
    // }

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


}

