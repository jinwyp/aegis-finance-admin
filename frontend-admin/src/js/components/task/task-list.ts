/**
 * Created by liushengbin on 16/8/15.
 */


import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Task, TaskService, TaskStatus } from '../../service/task';
// import { Task, Page, TaskService, TaskStatus } from '../../service/task';
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
    // pageObj : Page = new Page();
    taskAssignList : Task[] = [];
    taskPendingList : Task[] = [];
    taskHistoryList : Task[] = [];
    taskStatusStep : any = TaskStatus;
    currentUserSession : User = new User();

    isPending : boolean = true;

    ngOnInit(){
        this.activatedRoute.data.subscribe( data => {
            this.routeData = data;
            if (this.routeData.routeType === 'pending'){
                this.getAssignTaskList();
                this.getAllTaskList();
            }else{
                this.getAllTaskList();
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
                this.getPendingTaskList();
                // this.pageObj.page=result.meta.page;
                // this.pageObj.count=result.meta.count;
                // this.pageObj.total=result.meta.total;
            }else{

            }
        });
    }

    getPendingTaskList () {
        this.task.getTaskList().then((result)=>{
            if (result.success){
                this.taskPendingList = result.data;
                this.task.setPendingTaskLengthObservable(result.data.length + this.taskAssignList.length);
            }else{

            }
        });
    }

    getAllTaskList () {
        this.task.getTaskHistoryList().then((result)=>{
            if (result.success){
                if (this.routeData.routeType === 'all'){
                    this.taskHistoryList = result.data;
                }
                this.task.setAllTaskLengthObservable(result.data.length);
            }else{

            }
        });
    }

}

