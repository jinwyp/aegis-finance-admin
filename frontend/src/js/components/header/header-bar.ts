/**
 * Created by JinWYP on 8/8/16.
 */
import { Component } from '@angular/core';
import { ROUTER_DIRECTIVES } from '@angular/router';

declare var __moduleName: string;


@Component({
    selector: 'header-bar',
    moduleId: __moduleName || module.id,
    templateUrl: 'header-bar.html',
    directives  : [ROUTER_DIRECTIVES],
})
export class headerBarComponent {


}