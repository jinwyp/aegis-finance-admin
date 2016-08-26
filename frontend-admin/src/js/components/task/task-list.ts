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

    isAssignPerson : boolean = false;  //是否到分配人的步骤

    taskList : Task[] = [];
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


    getPendingTaskList () {
        this.task.getAdminTaskList().then((result)=>{
            if (result.success){
                this.taskList = result.data;

            }else{

            }
        });
    }

    getCurrentUser() {
        this.user.getUserSessionObservable.subscribe(
            result => {
                if (result && result.success) {
                    this.currentUserSession = result.data;
                    if (this.currentUserSession.groupIds.indexOf('GROUP00001') > -1){
                        this.isAssignPerson = true;
                    }
                } else {

                }
            },
            error => console.error(error)
        )
    }
}

