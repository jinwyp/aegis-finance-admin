/**
 * Created by liushengbin on 16/8/15.
 */


import { Input, Output, EventEmitter, Component } from '@angular/core';


declare var __moduleName: string;

@Component({
    selector: 'custom-select',
    moduleId: __moduleName || module.id,
    templateUrl: 'custom-select.html'
})



export class CustomSelectComponent {
    isOpen : boolean = true;

    selectedItem = {
        id: '-1',
        value: '请选择'
    };

    @Input()
    optionList = [];

    toggleSelect() {
        this.isOpen = !this.isOpen;
    }

    @Output()
    onChange : any = new EventEmitter();

    itemClick(item){
        this.selectedItem = item;
        this.isOpen = !this.isOpen;
        this.onChange.emit(this.selectedItem);
    }

}

