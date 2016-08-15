/**
 * Created by JinWYP on 8/8/16.
 */


import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';



class Order {
    id: number;
    name: string;
}



@Injectable()
class OrderService {

    private ApiUrlOrder = 'app2/heroes';  // URL to web api

    private handleError(error: any) {
        console.error('Http 请求发生错误!! ', error);
        return Promise.reject(error.message || error);
    }

    constructor(
        private http: Http
    ) { }


    getOrderList() {
        return this.http.get(this.ApiUrlOrder).toPromise()
            .then(response => response.json().data as Order[])
            .catch(this.handleError);
    }

    getOrderById(id: number) {
        return this.getOrderList().then(heroes => heroes.find(hero => hero.id === id));
    }

    // Add new Hero
    private post(hero: Order): Promise<Order> {
        let headers = new Headers({'Content-Type': 'application/json'});

        return this.http.post(this.ApiUrlOrder, JSON.stringify(hero), {headers: headers}).toPromise()
            .then(res => res.json().data)
            .catch(this.handleError);
    }

    // Update existing Hero
    private put(hero: Order) {
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');

        let url = `${this.ApiUrlOrder}/${hero.id}`;

        return this.http.put(url, JSON.stringify(hero), {headers: headers}).toPromise()
            .then(() => hero)
            .catch(this.handleError);
    }

    delete(hero: Order) {
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');

        let url = `${this.ApiUrlOrder}/${hero.id}`;

        return this.http.delete(url, {headers: headers}).toPromise()
            .catch(this.handleError);
    }


    save(hero: Order): Promise<Order>  {
        if (hero.id) {
            return this.put(hero);
        }
        return this.post(hero);
    }

}



export {Order, OrderService}