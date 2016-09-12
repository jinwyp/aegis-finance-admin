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

        Promise.all([
            this.task.getAdminAssignTaskList(),
            this.task.getPendingTaskList(),
            this.task.getHistoryTaskList()

        ]).then(resultList => {
            if (resultList.length === 3){
                if (resultList[0].success && resultList[1].success && resultList[2].success ){

                    this.taskAssignList = resultList[0].data;
                    this.taskPendingList = resultList[1].data;
                    if (this.routeData.routeType === 'all'){
                        this.taskHistoryList = resultList[2].data;
                    }

                    this.task.setTaskObservable(resultList[0].data, resultList[1].data, resultList[2].data);

                }
            }
        });
    }


}

