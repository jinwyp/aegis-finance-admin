/**
 * Created by liushengbin on 16/8/15.
 */


import {ViewChild, Component} from '@angular/core';
import {ModalDirective} from 'ng2-bootstrap/ng2-bootstrap';


declare var __moduleName:string;

@Component({
    selector :    'custom-modal',
    moduleId :    __moduleName || module.id,
    templateUrl : 'custom-modal.html'
})


export class CustomModalComponent {

    @ViewChild('childModal') public childModal:ModalDirective;

    public showChildModal():void {
        this.childModal.show();
    }

    public hideChildModal():void {
        this.childModal.hide();
    }

}

