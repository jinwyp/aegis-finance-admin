/**
 * Created by liushengbin on 16/8/15.
 */


import { Input, Output, EventEmitter, Component } from '@angular/core';


declare var __moduleName: string;

@Component({
    selector: 'string-select',
    moduleId: __moduleName || module.id,
    templateUrl: 'string-select.html'
})



export class StringSelectComponent {
    isopen = true;

    @Input()
    selectedItem = '请选择';

    @Input()
    optionList = []

    toggelSelect() {
        this.isopen = !this.isopen;
    }

    @Output()
    onChange = new EventEmitter();

    itemClick(obj){
        this.selectedItem=obj;
        this.isopen=!this.isopen;
        this.onChange.emit({value: this.selectedItem});
    }

}

