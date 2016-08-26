/**
 * Created by liushengbin on 16/8/15.
 */


import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Task, TaskService } from '../../service/task';
import { User, UserService } from '../../service/user';

declare var __moduleName: string;


@Component({
    selector: 'pending-list',
    moduleId: __moduleName || module.id,
    templateUrl: 'task-list.html'
})
export class TaskListComponent {

    constructor(
        private activatedRoute: ActivatedRoute,
        private task: TaskService,
        private user: UserService
    ) {}

    routeData :any = {
        routetype : '',
        title : ''
    };

    taskAssignList : Task[] = [];
    taskPendingList : Task[] = [];
    currentUserSession : User = new User();

    ngOnInit(){
        this.activatedRoute.data.subscribe( data => {
            this.routeData = data;
            if (this.routeData.routetype === 'pending'){
                this.getAssignTaskList();
                this.getPendingTaskList();
            }
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

    getAssignTaskList () {
        this.task.getAdminTaskList().then((result)=>{
            if (result.success){
                this.taskAssignList = result.data;
            }else{

            }
        });
    }

    getPendingTaskList () {
        this.task.getTaskList().then((result)=>{
            if (result.success){
                this.taskPendingList = result.data;
            }else{

            }
        });
    }


}

