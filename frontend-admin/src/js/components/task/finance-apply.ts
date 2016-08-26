/**
 * Created by liushengbin on 16/8/15.
 */


import { Component } from '@angular/core';


declare var __moduleName: string;

@Component({
    selector: 'finance-apply',
    moduleId: __moduleName || module.id,
    templateUrl: 'finance-apply.html'
})



export class FinanceApplyComponent {

    financeType=1;
    changeType = (typeIndex)=>{
        this.financeType=typeIndex;
    }

}

