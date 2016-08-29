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
    modalStatus : boolean = false;

    @Output()
    onChange:any = new EventEmitter();
    public hideModal():void {
        this.modalStatus=true;
        this.onChange.emit(this.modalStatus);
    }

}

