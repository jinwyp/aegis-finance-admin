/**
 * Created by liushengbin on 16/8/15.
 */


import {Input, Component, forwardRef} from '@angular/core';
import { NG_VALUE_ACCESSOR, ControlValueAccessor } from '@angular/forms';


declare var __moduleName:string;




export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
    provide: NG_VALUE_ACCESSOR,
    useExisting: forwardRef(() => CustomSelectComponent),
    multi: true
};


@Component({
    selector :    'custom-select',
    moduleId :    __moduleName || module.id,
    templateUrl : 'custom-select.html',
    providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR]
})
export class CustomSelectComponent implements ControlValueAccessor{


    private innerSelectedItem: any = '' ; // The internal data model

    //Placeholders for the callbacks which are later provided by the Control Value Accessor
    private onTouchedCallback: () => {};
    private onChangeCallback: (_: any) => {};

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


    isClose:boolean = true;

    @Input()
    optionList = [];


    toggleSelect() {
        this.isClose = !this.isClose;
    }

    hideClick(){
        this.isClose = !this.isClose;
        this.value = '';
        this.onChangeCallback(this.value);
    }

    itemClick(item) {
        this.isClose = !this.isClose;
        this.value = item.name;
        this.onChangeCallback(this.value);
    }

}

