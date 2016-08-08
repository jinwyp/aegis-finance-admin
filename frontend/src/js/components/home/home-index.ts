import { Component } from '@angular/core';
import { ROUTER_DIRECTIVES } from '@angular/router';
import { NavComponent } from '../navbar/navbar';
import { OrderService } from '../../service/order';




declare var __moduleName: string;

@Component({
    selector: 'page-admin',
    moduleId: __moduleName || module.id,
    templateUrl: 'home-index.html',
    directives  : [ROUTER_DIRECTIVES, NavComponent],
    providers   : [OrderService]
})



export class HomeComponent {

    title = '管理员首页11';

}

