/**
 * Created by JinWYP on 8/11/16.
 */

import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';

import { HttpResponse, API } from './http';


var permisson = {
    GROUP00010 : ['新增用户', '修改用户']
};


class User {

    id :number;
    username : string;
    name : string;
    password : string;
    phone : string;
    email : string;
    department : string;
    groupIds : string[];
    groupList : UserGroup[];

    constructor() {
        this.id  = 0;
        this.username  = '';
        this.password  = '';
        this.groupIds = [];
        this.groupList = [];
    }
}



class UserGroup {

    id :number;
    name : string;
    type : string;
    memberNums : number;


    userList : User[];



    constructor() {
        this.id  = 0;
        this.name  = '';
        this.type  = '';
    }
}


@Injectable()
class UserLoginService {

    constructor(
        private http: Http
    ) {}

    login(user) {
        return this.http.post(API.login, user).toPromise()
            .then(response => response.json() as HttpResponse);
    }

}



@Injectable()
class UserService {

    private handleError(error: any): Promise<any> {
        console.error('Http 用户 请求发生错误!! ', error);
        return Promise.reject(error.message || error);
    }

    private userSession = new BehaviorSubject<HttpResponse>(null);
    getUserSessionObservable = this.userSession.asObservable();

    constructor(
        private http: Http
    ) {
        this.http.get(API.session).subscribe((response) => {
            var result = response.json();
            if (result.data ){
                result.data.groupIds = result.data.groupList.map( group => { return group.id})
            }
            this.userSession.next(result)
        });
    }




    logout() {
        return this.http.get(API.logout).toPromise()
            .then(response => response.json() as HttpResponse)
            .catch(this.handleError);
    }



    getTaskList() {
        return this.http.get(API.task).toPromise()
            .then(response => response.json() as HttpResponse)
            .catch(this.handleError);
    }



    getList() {
        return this.http.get(API.users).toPromise()
            .then(response => response.json() as HttpResponse)
            .catch(this.handleError);
    }

    getDepartmentList() {
        return this.http.get(API.users + '/departments').toPromise()
            .then(response => response.json() as HttpResponse)
            .catch(this.handleError);
    }

    getUserById(id: number) {
        return this.http.get(API.users + '/' + id).toPromise()
            .then(response => {
                var result = response.json() as HttpResponse;
                if (result.data ){
                    result.data.groupIds = result.data.groupList.map( group => { return group.id})
                }

                return result;
            })
            .catch(this.handleError);
    }


    add(user: User) {
        let headers = new Headers({'Content-Type': 'application/json'});

        return this.http.post(API.users, JSON.stringify(user), {headers: headers}).toPromise()
            .then(res => res.json() as HttpResponse )
            .catch(this.handleError);
    }

    update(user: User) {
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');

        let url = `${API.users}/${user.id}`;

        return this.http.put(url, JSON.stringify(user), {headers: headers}).toPromise()
            .then(res => res.json() as HttpResponse )
            .catch(this.handleError);
    }

    del(user: User) {
        let headers = new Headers({'Content-Type': 'application/json'});

        let url = `${API.users}/${user.id}`;

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
        let url = `${API.groups}/${groupId}/${userId}`;
        return this.http.post(url, {}).toPromise()
            .then(res => res.json() as HttpResponse )
            .catch(this.handleError);
    }

    leaveGroup(userId:number, groupId:string) {
        let url = `${API.groups}/${groupId}/${userId}`;
        return this.http.delete(url).toPromise()
            .then(res => res.json() as HttpResponse )
            .catch(this.handleError);
    }

}



@Injectable()
class UserGroupService {

    private handleError(error: any) : Promise<any> {
        console.error('Http 用户组 请求发生错误!! ', error);
        return Promise.reject(error.message || error);
    }

    constructor(
        private http: Http
    ) { }


    getList() {
        return this.http.get(API.groups).toPromise()
            .then( response => {
                var result = response.json() as HttpResponse;
                if (result.data ){
                    result.data.forEach( group => {
                        if (!group.type) {group.type =''}
                        group.selected = false;
                    })
                }
                return result;
            })
            .catch(this.handleError);
    }

    getGroupById(id: string) {
        return this.http.get(API.groups + '/' + id).toPromise()
            .then(response => response.json() as HttpResponse)
            .catch(this.handleError);
    }

    getUserListByGroupId(id: string) {
        return this.http.get(API.groups + '/' + id + '/users').toPromise()
            .then(response => response.json() as HttpResponse)
            .catch(this.handleError);
    }

}



export {UserLoginService, User, UserService, UserGroup, UserGroupService}