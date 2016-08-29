/**
 * Created by liushengbin on 16/8/15.
 */


import {Component, Input ViewChild} from '@angular/core';
import {ModalDirective} from 'ng2-bootstrap/components/modal/modal.component';


declare var __moduleName:string;

@Component({
    selector :    'custom-modal',
    moduleId :    __moduleName || module.id,
    templateUrl : 'custom-modal.html'
})
export class CustomModalComponent {

    modalTitle='提示';

    @Input()
    modalStatus : boolean = true;

    @ViewChild('childModal') public childModal:ModalDirective;

    public showChildModal():void {
        this.childModal.show();
    }

    public hideChildModal():void {
        this.childModal.hide();
    }

}

