/**
 * Created by liushengbin on 16/8/15.
 */


import { Component } from '@angular/core';
import { Task, TaskService, TaskStatus } from '../../service/task';


declare var __moduleName: string;

@Component({
    selector: 'supervise-report',
    moduleId: __moduleName || module.id,
    templateUrl: 'supervise-report.html'
})
export class SuperviseReportComponent {

    currentTask : Task = new Task();


}

