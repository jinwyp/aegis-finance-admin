/**
 * Created by helue on 16/8/30.
 */

import {Component, Input, Output, EventEmitter} from '@angular/core';

declare var __moduleName:string;


@Component({
    selector :    'custom-checkbox',
    moduleId :    __moduleName || module.id,
    templateUrl : 'custom-checkbox.html'
})
export class CustomCheckboxComponent{

    @Input()
    sourceData = [];

    @Input()
    limitLength : number = 0;

    @Output()
    change:any = new EventEmitter();

    selectedIds :string[] = [];

    click (group){

        if (this.selectedIds.indexOf(group.id) > -1 ){
            this.selectedIds.splice(this.selectedIds.indexOf(group.id), 1);
            group.selected = false;
        }else{
            if (this.selectedIds.length < this.limitLength) {
                this.selectedIds.push(group.id);
                group.selected = true;
            }
        }
        this.change.emit({selectedIds:this.selectedIds});

        console.log(this.selectedIds)
    }

    isDisable (group){
        return this.selectedIds.length === this.limitLength && !group.selected
    }
}