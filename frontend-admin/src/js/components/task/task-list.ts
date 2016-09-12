/**
 * Created by liushengbin on 16/8/15.
 */


import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Task, TaskService, TaskStatus } from '../../service/task';
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
        routeType : '',
        title : ''
    };

    taskAssignList : Task[] = [];
    taskPendingList : Task[] = [];
    taskHistoryList : Task[] = [];
    taskStatusStep : any = TaskStatus;
    currentUserSession : User = new User();

    isPending : boolean = true;

    ngOnInit(){
        this.activatedRoute.data.subscribe( data => {
            this.routeData = data;
            this.getTaskList();
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

    getTaskList () {

        this.task.getTaskObservable.subscribe(
            result => {
                this.taskAssignList = result.assignTaskList;
                this.taskPendingList = result.pendingTaskList;
                if (this.routeData.routeType === 'all'){
                    this.taskHistoryList = result.allTaskList;
                }
            },
            error => console.error(error)
        );

    }

}

