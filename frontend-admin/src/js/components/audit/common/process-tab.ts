/**
 * Created by liushengbin on 16/8/15.
 */


import { Component } from '@angular/core';
import { jQuery as $ } from '../../../jquery-plugin/bootstrap.js';


declare var __moduleName: string;

@Component({
    selector: 'process-tab',
    moduleId: __moduleName || module.id,
    templateUrl: 'process-tab.html'
})



export class ProcessTabComponent {
    title = '流程导航';
    tabObj = {
        index : 1
    }

    changeTab = (currentTab)=>{
        this.tabObj.index = currentTab;
    }
}

