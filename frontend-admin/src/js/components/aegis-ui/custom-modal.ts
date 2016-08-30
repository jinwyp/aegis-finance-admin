/**
 * Created by liushengbin on 16/8/15.
 */


import {Component, Output, EventEmitter, forwardRef} from '@angular/core';
import { NG_VALUE_ACCESSOR, ControlValueAccessor } from '@angular/forms';

declare var __moduleName:string;



export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
    provide: NG_VALUE_ACCESSOR,
    useExisting: forwardRef(() => CustomModalComponent),
    multi: true
};



@Component({
    selector :    'custom-modal',
    moduleId :    __moduleName || module.id,
    templateUrl : 'custom-modal.html',
    providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR]

})
export class CustomModalComponent implements ControlValueAccessor{

    private isHidden : boolean = true;

    //Placeholders for the callbacks which are later provided by the Control Value Accessor
    private onTouchedCallback: () => {};
    private onChangeCallback: (_: any) => {};


    //get accessor
    get value(): any {
        return this.isHidden;
    };

    //set accessor including call the onchange callback
    set value(v: any) {
        if (v !== this.isHidden) {
            this.isHidden = v;
            this.onChangeCallback(v);
        }
    }

    @Output()
    confirm:any = new EventEmitter();

    @Output()
    cancel:any = new EventEmitter();

    public cancelClick() {
        this.value = true;
        this.cancel.emit();
    }

    public confirmClick() {
        this.value = true;
        this.confirm.emit();
    }


    //From ControlValueAccessor interface
    writeValue(value: any) {
        if (value !== this.isHidden) {
            this.isHidden = value;
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


}

