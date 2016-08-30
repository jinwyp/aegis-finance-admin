/**
 * Created by JinWYP on 8/8/16.
 */
import { Component } from '@angular/core';

import { Task, TaskService, TaskStatus } from '../../service/task';


declare var __moduleName: string;


@Component({
    selector: 'left-menu',
    moduleId: __moduleName || module.id,
    templateUrl: 'left-menu.html'
})
export class LeftMenuComponent {

    constructor(
        private task: TaskService
    ) {}

    css = {
        currentTab : 1,
        currentMenu : 1,
        allTaskListInfo : 0,
        pendingTaskListInfo : 0,
    };

    ngOnInit() {
        this.getTaskInfo();
    }

    getTaskInfo() {

        this.task.getAllTaskLengthObservable.subscribe(
            result => { if (result) {this.css.allTaskListInfo = result.allTaskLength} },
            error => console.error(error)
        );

        this.task.getPendingTaskLengthObservable.subscribe(
            result => { if (result) {this.css.pendingTaskListInfo = result.pendingTaskLength} },
            error => console.error(error)
        )
    }

    changeMenu = (menu)=>{
        this.css.currentMenu = menu;
        this.css.currentTab = Math.floor(menu/10);
    }
}