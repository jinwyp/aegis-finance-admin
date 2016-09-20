/**
 * Created by helue on 16/8/30.
 */

import {Component, Input, Output, EventEmitter, forwardRef} from '@angular/core';
import { NG_VALUE_ACCESSOR, ControlValueAccessor } from '@angular/forms';


export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
    provide: NG_VALUE_ACCESSOR,
    useExisting: forwardRef(() => CustomCheckboxComponent),
    multi: true
};


@Component({
    selector :    'custom-checkbox',
    moduleId :    module.id,
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
        if (value !== this.selectedIds ) {
            // console.log('外面往组件里面更改数据', value, this.sourceData);

            if (Array.isArray(value)){
                this.selectedIds = value;
            }else {
                this.selectedIds = [];
            }

            this.sourceData.forEach(group => {

                if (this.selectedIds.indexOf(group.id) > -1) {
                    group.selected = true;
                }else{
                    group.selected = false;
                }
            })
        }
    }

    //From ControlValueAccessor interface
    registerOnChange(fn: any) {
        // console.log('从组件里面往外面更改数据');
        this.onChangeCallback = fn;
    }

    //From ControlValueAccessor interface
    registerOnTouched(fn: any) {
        this.onTouchedCallback = fn;
    }



    ngOnChanges(changes) {
        for (let propName in changes) {
            // console.log(propName, changes[propName]);

            if (propName === 'sourceData'){
                this.sourceData.forEach(group => {

                    if (this.selectedIds.indexOf(group.id) > -1) {
                        group.selected = true;
                    }else{
                        group.selected = false;
                    }
                })
            }
        }
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
        this.onChange.emit();
        this.onChangeCallback(this.selectedIds);
    }


    isDisable (group){
        return this.selectedIds.length === this.limitLength && !group.selected
    }
}