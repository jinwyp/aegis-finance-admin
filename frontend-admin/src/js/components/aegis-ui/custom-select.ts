/**
 * Created by liushengbin on 16/8/15.
 */


import {Input, Output, EventEmitter, Component} from '@angular/core';


declare var __moduleName:string;

@Component({
    selector :    'custom-select',
    moduleId :    __moduleName || module.id,
    templateUrl : 'custom-select.html'
})


export class CustomSelectComponent {

    isClose:boolean = true;

    @Input()
    optionList = [];

    @Input()
    selectedItem = {};


    toggleSelect() {
        this.isClose = !this.isClose;
        console.log(this.selectedItem);
    }

    @Output()
    onChange:any = new EventEmitter();

    itemClick(item) {
        this.selectedItem = item;
        this.isClose      = !this.isClose;
        this.onChange.emit(this.selectedItem);
    }

}

