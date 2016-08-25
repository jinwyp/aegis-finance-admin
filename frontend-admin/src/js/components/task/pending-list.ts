/**
 * Created by liushengbin on 16/8/15.
 */


import { Component } from '@angular/core';
import { Router, ActivatedRoute, NavigationEnd } from '@angular/router';

import { Task, TaskService } from '../../service/task';

declare var __moduleName: string;


@Component({
    selector: 'pending-list',
    moduleId: __moduleName || module.id,
    templateUrl: 'pending-list.html'
})
export class PendingListComponent {

    constructor(
        private router: Router,
        private activatedRoute: ActivatedRoute,
        private task: TaskService
    ) {}

    routeData :any = {
        routetype : '',
        title : ''
    };

    taskList : Task[] = [];


    ngOnInit(){

        this.activatedRoute.data.subscribe( data => {
            this.routeData = data;
            if (this.routeData.routetype === 'pending'){
                this.getPendingTaskList();
            }
        });

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

