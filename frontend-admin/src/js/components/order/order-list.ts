/**
 * Created by JinWYP on 8/8/16.
 */
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Order, OrderService } from '../../service/order';




declare var __moduleName: string;

@Component({
    selector: 'order-list',
    moduleId: __moduleName || module.id,
    templateUrl: 'order-list.html'
})



export class OrderListComponent implements OnInit {

    title = 'Tour of Heroes';
    heroes: Order[];
    selectedHero: Order;
    addingHero = false;
    error: any;

    constructor(
        private router: Router,
        private orderService: OrderService
    ) {}


    ngOnInit() {
        this.getHeroes();
    }

    onSelect(hero: Order) {
        this.selectedHero = hero;
    }

    getHeroes() {
        this.orderService.getOrderList().then(heroes => this.heroes = heroes);
    }

    gotoDetail() {
        this.router.navigate(['/orders', this.selectedHero.id]);
    }


    addHero() {
        this.addingHero = true;
        this.selectedHero = null;
    }

    close(savedHero: Order) {
        this.addingHero = false;
        if (savedHero) { this.getHeroes(); }
    }

    deleteHero(hero: Order, event: any) {
        event.stopPropagation();
        this.orderService.delete(hero)
            .then(res => {
                this.heroes = this.heroes.filter(h => h !== hero);
                if (this.selectedHero === hero) { this.selectedHero = null; }
            })
            .catch(error => this.error = error);
    }

}

