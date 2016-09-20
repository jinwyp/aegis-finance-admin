/**
 * Created by JinWYP on 8/8/16.
 */


import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import {Order, OrderService } from '../../service/order';



@Component({
    selector    : 'home-dashboard',
    moduleId    : module.id,
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
        let link = ['/orders', hero.id];
        this.router.navigate(link);
    }
}