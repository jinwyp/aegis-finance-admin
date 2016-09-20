/**
 * Created by liushengbin on 16/8/15.
 */


import {Input, Component } from '@angular/core';

import { Task, TaskService, TaskStatus } from '../../service/task';

declare var __moduleName: string;

@Component({
    selector: 'process-tab',
    moduleId: module.id,
    templateUrl: 'process-tab.html'
})
export class ProcessTabComponent {

    css = {
        currentIndex : 2
    };


    _financeId :number = -1;

    get financeId() { return this._financeId; }

    @Input()
    set financeId(id: number) {

        if (id && id > 0){
            this._financeId = id;
            this.getTaskList(this._financeId);
        }
    }

    @Input()
    processInstanceId : string = '0';

    processList = [];


    constructor(
        private task: TaskService
    ) {}



    changeTab = (currentTab)=>{
        this.css.currentIndex = currentTab;
    };


    getTaskList (financeId:number) {
        this.task.getTaskListByOrderId(financeId).then((result)=>{
            if (result.success && result.data){
                this.processList = result.data;

            }else{

            }
        });
    }
}

