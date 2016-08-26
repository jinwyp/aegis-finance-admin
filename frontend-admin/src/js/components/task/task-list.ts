/**
 * Created by liushengbin on 16/8/15.
 */


import { Component } from '@angular/core';
import { Router, ActivatedRoute, NavigationEnd } from '@angular/router';

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
        private router: Router,
        private activatedRoute: ActivatedRoute,
        private task: TaskService,
        private user: UserService
    ) {}

    routeData :any = {
        routetype : '',
        title : ''
    };

    taskList : Task[] = [];
    taskPendingList : Task[] = [];
    currentUserSession : User = new User();

    ngOnInit(){
        this.activatedRoute.data.subscribe( data => {
            this.routeData = data;
            if (this.routeData.routetype === 'pending'){
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

    getPendingTaskList () {
        this.task.getAdminTaskList().then((result)=>{
            if (result.success){
                this.taskList = result.data;
            }else{

            }
        });
    }


}

