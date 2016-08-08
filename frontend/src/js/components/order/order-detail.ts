/**
 * Created by JinWYP on 8/8/16.
 */


import { Component, EventEmitter, Input, OnInit, OnDestroy, Output } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Order } from '../../model/order';
import { OrderService } from '../../service/order';


declare var __moduleName: string;

@Component({
    selector: 'order-detail',
    moduleId: __moduleName || module.id,
    templateUrl: 'order-detail.html'
})



export class OrderDetailComponent implements OnInit, OnDestroy {

    @Input()
    hero: Order;

    @Output()
    close = new EventEmitter();

    error: any;
    sub: any;
    navigated = false; // true if navigated here


    constructor(
        private orderService: OrderService,
        private route: ActivatedRoute
    ) {
    }



    ngOnInit() {

        this.sub = this.route.params.subscribe(params => {
            if (params['id'] !== undefined) {
                let id = +params['id'];
                this.navigated = true;
                this.orderService.getOrderById(id).then(hero => this.hero = hero);
            } else {
                this.navigated = false;
                this.hero = new Order();
            }
        });
    }


    ngOnDestroy() {
        this.sub.unsubscribe();
    }

    goBack(savedHero: Order = null) {
        this.close.emit(savedHero);
        if (this.navigated) { window.history.back(); }
    }


    save() {
        this.orderService.save(this.hero)
            .then(hero => {
                this.hero = hero; // saved hero, w/ id if new
                this.goBack(hero);
            })
            .catch(error => this.error = error); // TODO: Display error message
    }
}
