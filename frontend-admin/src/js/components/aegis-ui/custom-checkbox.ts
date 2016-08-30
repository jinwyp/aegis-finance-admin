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
    onChange:any = new EventEmitter();

    selectedIds :string[] = [];


    ngOnChanges() {
        this.sourceData.forEach(group => {
            if (group.selected ) {this.selectedIds.push(group.id) }
        })
    }

    click (group){

        if (this.selectedIds.indexOf(group.id) === -1 ){
            if (this.selectedIds.length < this.limitLength) {
                this.selectedIds.push(group.id);
                group.selected = true;
            }
        }else{
            this.selectedIds.splice(this.selectedIds.indexOf(group.id), 1);
            group.selected = false;
        }
        this.onChange.emit({selectedIds:this.selectedIds});
    }


    isDisable (group){
        return this.selectedIds.length === this.limitLength && !group.selected
    }
}