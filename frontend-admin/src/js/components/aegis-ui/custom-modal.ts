/**
 * Created by liushengbin on 16/8/15.
 */


import {Component, Input, Output, EventEmitter} from '@angular/core';


declare var __moduleName:string;

@Component({
    selector :    'custom-modal',
    moduleId :    __moduleName || module.id,
    templateUrl : 'custom-modal.html'
})
export class CustomModalComponent {


    @Input()
    isHidden : boolean = true;


    @Output()
    confirm:any = new EventEmitter();

    @Output()
    cancel:any = new EventEmitter();

    public cancelClick() {
        this.isHidden = true;
        this.cancel.emit({isModalHidden:this.isHidden});
    }

    public confirmClick() {
        this.isHidden = true;
        this.confirm.emit({isModalHidden:this.isHidden});
    }
}

