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
    taskId : string = '';
    currentTask : Task = new Task();

    constructor(
        private activatedRoute: ActivatedRoute,
        private task: TaskService,
        private user: UserService
    ) {

    }

    ngOnInit(){
        this.sub = this.activatedRoute.params.subscribe(params => {
            this.taskId = params['id'];
            // this.getTaskInfo(params['id']);
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


}

