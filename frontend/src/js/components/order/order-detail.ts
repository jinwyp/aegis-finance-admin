/**
 * Created by JinWYP on 8/8/16.
 */


import { Component, Input } from '@angular/core';
import { Order } from '../../model/order';

declare var __moduleName: string;

@Component({
    selector: 'order-detail',
    moduleId: __moduleName || module.id,
    templateUrl: 'order-detail.html'
})



export class OrderDetailComponent {

    @Input()
    hero: Order;

}
