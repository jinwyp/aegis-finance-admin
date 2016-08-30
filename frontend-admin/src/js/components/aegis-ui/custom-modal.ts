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

    @Input()
    btnClick : boolean = false;

    @Output()
    onChange:any = new EventEmitter();

    public hideModal() {
        this.modalStatus=true;
        this.onChange.emit({modalStatus:this.modalStatus,btnClick:false});
    }

    public sureClick() {
        this.modalStatus=true;
        this.btnClick=true;
        this.onChange.emit({modalStatus:this.modalStatus,btnClick:this.btnClick});
    }
}

