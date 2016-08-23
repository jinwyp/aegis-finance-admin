/**
 * Created by JinWYP on 8/11/16.
 */

import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { HttpResponse } from './http';



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



class UserGroup {

    id :number;
    name : string;
    type : string;

    userList : User[];

    constructor() {
        this.id  = 0;
        this.name  = '';
        this.type  = '';
    }
}



@Injectable()
class UserService {

    private apiUrl = {
        login : '/api/financing/admin/login',
        list : '/api/financing/admin/user',
        group : '/api/financing/admin/group'
    };

    private handleError(error: any): Promise<any> {
        console.error('Http 用户 请求发生错误!! ', error);
        return Promise.reject(error.message || error);
    }

    constructor(
        private http: Http
    ) { }


    login(user) {
        return this.http.post(this.apiUrl.login, user).toPromise()
            .then(response => response.json() as HttpResponse)
            .catch(this.handleError);
    }


    getList() {
        return this.http.get(this.apiUrl.list).toPromise()
            .then(response => response.json() as HttpResponse)
            .catch(this.handleError);
    }

    getUserById(id: number) {
        return this.http.get(this.apiUrl.list + '/' + id).toPromise()
            .then(response => response.json() as HttpResponse)
            .catch(this.handleError);
    }


    add(user: User) {
        let headers = new Headers({'Content-Type': 'application/json'});

        return this.http.post(this.apiUrl.list, JSON.stringify(user), {headers: headers}).toPromise()
            .then(res => res.json() as HttpResponse )
            .catch(this.handleError);
    }

    update(user: User) {
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');

        let url = `${this.apiUrl.login}/${user.id}`;

        return this.http.put(url, JSON.stringify(user), {headers: headers}).toPromise()
            .then(res => res.json() as HttpResponse )
            .catch(this.handleError);
    }

    del(user: User) {
        let headers = new Headers({'Content-Type': 'application/json'});

        let url = `${this.apiUrl.login}/${user.id}`;

        return this.http.delete(url, {headers: headers}).toPromise()
            .then(res => res.json() as HttpResponse )
            .catch(this.handleError);
    }

    save(user: User)  {
        if (user.id) {
            return this.update(user);
        }
        return this.add(user);
    }

    joinGroup(userId:number, groupId:string) {
        let url = `${this.apiUrl.group}/${groupId}/${userId}`;
        return this.http.post(url, {}).toPromise()
            .then(res => res.json() as HttpResponse )
            .catch(this.handleError);
    }

    leaveGroup(userId:number, groupId:string) {
        let url = `${this.apiUrl.group}/${groupId}/${userId}`;
        return this.http.delete(url).toPromise()
            .then(res => res.json() as HttpResponse )
            .catch(this.handleError);
    }

}



@Injectable()
class UserGroupService {

    private apiUrl = {
        list : '/api/financing/admin/group'
    };

    private handleError(error: any) : Promise<any> {
        console.error('Http 用户组 请求发生错误!! ', error);
        return Promise.reject(error.message || error);
    }

    constructor(
        private http: Http
    ) { }


    getList() {
        return this.http.get(this.apiUrl.list).toPromise()
            .then(response => response.json() as HttpResponse)
            .catch(this.handleError);
    }

    getGroupById(id: string) {
        return this.http.get(this.apiUrl.list + '/' + id).toPromise()
            .then(response => response.json() as HttpResponse)
            .catch(this.handleError);
    }

    getUserListByGroupId(id: string) {
        return this.http.get(this.apiUrl.list + '/' + id + '/users').toPromise()
            .then(response => response.json() as HttpResponse)
            .catch(this.handleError);
    }



}



export {User, UserService, UserGroup, UserGroupService}