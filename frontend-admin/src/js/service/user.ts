/**
 * Created by JinWYP on 8/11/16.
 */

import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { HttpResult } from './http';



class User {

    id :number;
    username : string;
    password : string;

    constructor() {
        this.id  = 0;
        this.username  = '';
        this.password  = '';
    }
}




@Injectable()
class UserService {

    private apiUrl = {
        login : '/api/financing/admin/login'
    };

    private handleError(error: any) {
        console.error('Http 请求发生错误!! ', error);
        return Promise.reject(error.message || error);
    }

    constructor(
        private http: Http
    ) { }


    login(user) {
        return this.http.post(this.apiUrl.login, user).toPromise()
            .then(response => response.json() as HttpResult)
            .catch(this.handleError);
    }

    // getUserById(id: number) {
    //     return this.getOrderList().then(heroes => heroes.find(hero => hero.id === id));
    // }

    // Add new Hero
    private post(hero: User): Promise<User> {
        let headers = new Headers({'Content-Type': 'application/json'});

        return this.http.post(this.apiUrl.login, JSON.stringify(hero), {headers: headers}).toPromise()
            .then(res => res.json().data)
            .catch(this.handleError);
    }

    // Update existing Hero
    private put(hero: User) {
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');

        let url = `${this.apiUrl.login}/${hero.id}`;

        return this.http.put(url, JSON.stringify(hero), {headers: headers}).toPromise()
            .then(() => hero)
            .catch(this.handleError);
    }

    delete(hero: User) {
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');

        let url = `${this.apiUrl.login}/${hero.id}`;

        return this.http.delete(url, {headers: headers}).toPromise()
            .catch(this.handleError);
    }


    save(hero: User): Promise<User>  {
        if (hero.id) {
            return this.put(hero);
        }
        return this.post(hero);
    }

}


export {User, UserService}