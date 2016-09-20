/**
 * Created by JinWYP on 8/8/16.
 */
import { Component } from '@angular/core';

import { Task, TaskService, TaskStatus } from '../../service/task';

declare var __moduleName: string;


@Component({
    selector: 'left-menu',
    moduleId: module.id,
    templateUrl: 'left-menu.html'
})
export class LeftMenuComponent {

    css = {
        currentTab : 1,
        currentMenu : 1,
        allTaskListInfo : 0,
        pendingTaskListInfo : 0,
    };


    constructor(
        private task: TaskService
    ) {}


    ngOnInit() {
        this.getTaskInfo();
    }

    getTaskInfo() {

        this.task.getTaskObservable.subscribe(
            result => {
                if (result.allTaskList.length > 0 ) {
                    this.css.pendingTaskListInfo = result.assignTaskList.length + result.pendingTaskList.length;
                    this.css.allTaskListInfo = result.allTaskList.length;
                }else{
                    Promise.all([
                        this.task.getAdminAssignTaskList(),
                        this.task.getPendingTaskList(),
                        this.task.getHistoryTaskList()

                    ]).then(resultList => {
                        if (resultList.length === 3){
                            if (resultList[0].success && resultList[1].success && resultList[2].success ){

                                if (!resultList[0].data){resultList[0].data = []}
                                this.css.pendingTaskListInfo = resultList[0].data.length + resultList[1].data.length;
                                this.css.allTaskListInfo = resultList[2].data.length;
                            }
                        }
                    });
                }
            },
            error => console.error(error)
        );

    }

    changeMenu = (menu)=>{
        this.css.currentMenu = menu;
        this.css.currentTab = Math.floor(menu/10);
    }
}