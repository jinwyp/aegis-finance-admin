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
    isopen = true;

    selectedItem = {key: '-1', value: '请选择'}

    @Input()
    optionList = [
        {key: '10001', value: '风控部'},
        {key: '10002', value: '技术部'},
        {key: '10003', value: '测试部'},
        {key: '10004', value: '产品部'},
        {key: '10005', value: '运营部'},
    ]

    toggelSelect() {
        this.isopen = !this.isopen;
    }

    @Output()
    onChange = new EventEmitter();

    itemClick(obj){
        this.selectedItem=obj;
        this.isopen=!this.isopen;
        this.onChange.emit({value: this.selectedItem.value});
    }

}

