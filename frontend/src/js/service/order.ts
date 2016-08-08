/**
 * Created by JinWYP on 8/8/16.
 */


import { Injectable } from '@angular/core';

import { ORDERS } from '../mock/mock-orders';
import { Order } from '../model/order';


@Injectable()
export class OrderService {

    // getOrderList() {
    //     return Promise.resolve(ORDERS);
    // }


    //simulate a slow connection.
    getOrderList() {
        return new Promise<Order[]>(resolve =>{
            setTimeout(() => resolve(ORDERS), 2000); // 2 seconds
        });
    }

}
