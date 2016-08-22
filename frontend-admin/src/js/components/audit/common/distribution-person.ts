/**
 * Created by liushengbin on 16/8/15.
 */


import {Component, Input} from '@angular/core';


declare var __moduleName: string;

@Component({
    selector: 'distribution-person',
    moduleId: __moduleName || module.id,
    templateUrl: 'distribution-person.html'
})



export class DistributionPersonComponent {

    @Input()
    data = {}
}

