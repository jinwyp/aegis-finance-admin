/**
 * Created by JinWYP on 8/8/16.
 */


import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Order } from '../../model/order';
import { OrderService } from '../../service/order';


declare var __moduleName: string;

@Component({
    selector    : 'home-dashboard',
    moduleId    : __moduleName || module.id,
    templateUrl : 'home-dashboard.html'
})

export class HomeDashboardComponent implements OnInit {
    heroes: Order[] = [];

    constructor(
        private router: Router,
        private orderService: OrderService
    ) { }

    ngOnInit() {
        this.getHeroes();
    }

    getHeroes() {
        this.orderService.getOrderList().then(heroes => this.heroes = heroes.slice(1, 5));
    }

    gotoDetail(hero: Order) {
        console.log(hero);
        let link = ['/orders', hero.id];
        this.router.navigate(link);
    }
}