/**
 * Created by liushengbin on 16/8/15.
 */


import {Input, Component } from '@angular/core';

declare var __moduleName: string;

@Component({
    selector: 'process-tab',
    moduleId: __moduleName || module.id,
    templateUrl: 'process-tab.html'
})
export class ProcessTabComponent {

    currentTab = {
        index : 2
    };

    @Input()
    processInstanceId : string = '0';

    @Input()
    processList = [];

    changeTab = (currentTab)=>{
        this.currentTab.index = currentTab;
    }
}

