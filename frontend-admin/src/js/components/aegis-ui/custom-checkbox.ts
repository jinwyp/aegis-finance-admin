/**
 * Created by helue on 16/8/30.
 */

import {Component, Input, Output, EventEmitter, forwardRef} from '@angular/core';
import { NG_VALUE_ACCESSOR, ControlValueAccessor } from '@angular/forms';

declare var __moduleName:string;

export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
    provide: NG_VALUE_ACCESSOR,
    useExisting: forwardRef(() => CustomCheckboxComponent),
    multi: true
};


@Component({
    selector :    'custom-checkbox',
    moduleId :    __moduleName || module.id,
    templateUrl : 'custom-checkbox.html',
    providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR]
})
export class CustomCheckboxComponent implements ControlValueAccessor{

    @Input()
    sourceData = [];

    @Input()
    limitLength : number = 0;

    @Output()
    onChange:any = new EventEmitter();

    private selectedIds :string[] = [];

    //Placeholders for the callbacks which are later provided by the Control Value Accessor
    private onTouchedCallback: () => {};
    private onChangeCallback: (_: any) => {};

    //From ControlValueAccessor interface
    writeValue(value: any) {
        if (value !== this.selectedIds) {
            this.selectedIds = value;
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