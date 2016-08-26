/**
 * Created by liushengbin on 16/8/15.
 */


import {Component, ViewChild} from '@angular/core';
import {CORE_DIRECTIVES} from '@angular/common';

import {MODAL_DIRECTIVES, BS_VIEW_PROVIDERS} from 'ng2-bootstrap/ng2-bootstrap';
import {ModalDirective} from 'ng2-bootstrap/components/modal/modal.component';


declare var __moduleName:string;

@Component({
    selector :    'custom-modal',
    directives: [MODAL_DIRECTIVES, CORE_DIRECTIVES],
    moduleId :    __moduleName || module.id,
    providers:[BS_VIEW_PROVIDERS],
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

