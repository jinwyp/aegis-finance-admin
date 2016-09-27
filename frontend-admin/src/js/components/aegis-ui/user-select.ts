/**
 * Created by liushengbin on 16/8/15.
 */


import {Input, Component, forwardRef} from '@angular/core';
import { NG_VALUE_ACCESSOR, ControlValueAccessor } from '@angular/forms';
import { User } from '../../service/user';


declare var __moduleName:string;




export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
    provide: NG_VALUE_ACCESSOR,
    useExisting: forwardRef(() => UserSelectComponent),
    multi: true
};


@Component({
    selector :    'user-select',
    moduleId :    module.id,
    templateUrl : 'user-select.html',
    providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR]
})
export class UserSelectComponent implements ControlValueAccessor{


    private selectedUser; // The internal data model

    //Placeholders for the callbacks which are later provided by the Control Value Accessor
    private onTouchedCallback: () => {};
    private onChangeCallback: (_: any) => {};

    //get accessor
    get value(): any {
        return this.selectedUser;
    };

    //set accessor including call the onchange callback
    set value(v: any) {
        if (v !== this.selectedUser) {
            this.selectedUser = v;
            this.onChangeCallback(v);
        }
    }

    //From ControlValueAccessor interface
    writeValue(value: any) {
        if (value !== this.selectedUser) {
            this.selectedUser = value;
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
    optionList : User[] = [];


    toggleSelect() {
        this.isClose = !this.isClose;
    }

    hideClick(){
        this.isClose = !this.isClose;
        this.value = new User();
        this.onChangeCallback(this.value);
    }
    
    itemClick(item) {
        this.isClose = !this.isClose;
        this.value = item;
        this.onChangeCallback(this.value);
    }

}

