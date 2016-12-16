/**
 * Created by liushengbin on 16/8/15.
 */


import {Input, Output, EventEmitter, Component, forwardRef} from '@angular/core';
import { NG_VALUE_ACCESSOR, ControlValueAccessor } from '@angular/forms';


declare var __moduleName:string;




export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
    provide: NG_VALUE_ACCESSOR,
    useExisting: forwardRef(() => CustomSelectComponent),
    multi: true
};


@Component({
    selector :    'custom-select',
    moduleId :    module.id,
    templateUrl : 'custom-select.html',
    providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR]
})
export class CustomSelectComponent implements ControlValueAccessor{


    private innerSelectedItem: any = {} ; // The internal data model

    //Placeholders for the callbacks which are later provided by the Control Value Accessor
    private onTouchedCallback: () => {};
    private onChangeCallback: (_: any) => {};


    ngOnInit() {

    }

    //get accessor
    get value(): any {
        return this.innerSelectedItem;
    };

    //set accessor including call the onchange callback
    set value(v: any) {
        if (v !== this.innerSelectedItem) {
            this.innerSelectedItem = v;
            this.onChangeCallback(v);
        }
    }

    //From ControlValueAccessor interface
    writeValue(value: any) {
        if (value !== this.innerSelectedItem) {
            this.innerSelectedItem = value;
        }
    }

    //From ControlValueAccessor interface
    registerOnChange(fn: any) {
        this.onChangeCallback = fn;
    }

    //From ControlValueAccessor interface
    registerOnTouched(fn: any) {
        this.onTouchedCallback = fn;
    }

    defaultText = "请选择";

    isClose:boolean = true;

    @Input()
    optionList = [];

    @Input()
    outputName = '';

    @Input()
    hasDefaultItem :boolean = true;

    toggleSelect() {
        this.isClose = !this.isClose;
    }

    showSelect() {
        this.isClose = false;
    }

    hideSelect() {
        this.isClose = true;
    }

    hideClick(){
        this.isClose = !this.isClose;
        this.value = {name : null, id : -1};
        if (this.outputName){
            this.onChangeCallback(this.value[this.outputName]);
        }else{
            this.onChangeCallback(this.value);
        }
    }

    @Output()
    itemChange:any = new EventEmitter();

    itemClick(item) {
        this.isClose = !this.isClose;
        this.value = item;
        if(item)
            this.defaultText = item.name;
        else
            this.defaultText = '请选择';
        this.itemChange.emit();

        if (this.outputName){
            this.onChangeCallback(this.value[this.outputName]);
        }else{
            this.onChangeCallback(this.value);
        }
    }

}

