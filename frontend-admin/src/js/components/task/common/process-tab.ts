/**
 * Created by liushengbin on 16/8/15.
 */


import { Component } from '@angular/core';

// import { jQuery as $ } from '../../../jquery-plugin/bootstrap.js';
// import { jQuery as $ } from '../../../../../../frontend-site/src/js/jquery-plugin';

declare var __moduleName: string;

@Component({
    selector: 'process-tab',
    moduleId: __moduleName || module.id,
    templateUrl: 'process-tab.html'
})



export class ProcessTabComponent {

    tabObj = {
        index : 2
    }

    changeTab = (currentTab)=>{
        this.tabObj.index = currentTab;
    }
}
