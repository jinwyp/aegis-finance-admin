/**
 * Created by JinWYP on 8/11/16.
 */

import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';

import {GlobalPromiseHttpCatch, HttpResponse, API } from './http';

var GroupId = {
    'trader' : 'GROUP00002', //线上交易员组
    'salesman' : 'GROUP00004', //业务员组
    'investigator' : 'GROUP00006', //尽调员组
    'supervisor' : 'GROUP00008', //监管员组
    'riskmanager' : 'GROUP00010'  //风控员组
};


var permisson = {
    GROUP00010 : ['新增用户', '修改用户']
};


class User {

    id :string;
    username : string;
    name : string;
    password : string;
    phone : string;
    email : string;
    department : string;
    lastLoginTime : string;
    operateAuthority : boolean;
    groupIds : string[];
    groupList : UserGroup[];

    constructor() {
        this.id  = '';
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
            .catch(GlobalPromiseHttpCatch);
    }


    getList(name:string, username:string, groupName:string) {
        let url = `${API.users}?name=${name||''}&username=${username||''}&groupName=${groupName||''}`;
        return this.http.get(url).toPromise()
            .then(response => response.json() as HttpResponse)
            .catch(GlobalPromiseHttpCatch);
    }

    getDepartmentList() {
        return this.http.get(API.users + '/departments').toPromise()
            .then(response => response.json() as HttpResponse)
            .catch(GlobalPromiseHttpCatch);
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
            .catch(GlobalPromiseHttpCatch);
    }


    add(user: User) {
        let headers = new Headers({'Content-Type': 'application/json'});

        return this.http.post(API.users, JSON.stringify(user), {headers: headers}).toPromise()
            .then(res => res.json() as HttpResponse )
            .catch(GlobalPromiseHttpCatch);
    }

    update(user: User) {
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');

        let url = `${API.users}/${user.id}`;

        return this.http.put(url, JSON.stringify(user), {headers: headers}).toPromise()
            .then(res => res.json() as HttpResponse )
            .catch(GlobalPromiseHttpCatch);
    }

    resetPassword(id:string) {
        let headers = new Headers({'Content-Type': 'application/json'});
        return this.http.post(API.users + '/' + id + '/password', {headers: headers}).toPromise()
            .then(res => res.json() as HttpResponse )
            .catch(GlobalPromiseHttpCatch);
    }

    del(id: string) {
        let url = `${API.users}/${id}`;
        return this.http.delete(url).toPromise()
            .then(res => res.json() as HttpResponse )
            .catch(GlobalPromiseHttpCatch);
    }

    save(user: User)  {
        if (user.id) {
            return this.update(user);
        }
        return this.add(user);
    }



    updateCurrentUserPassword(user) {
        let headers = new Headers({'Content-Type': 'application/json'});
        return this.http.put(API.users + '/self/password' , JSON.stringify(user), {headers: headers}).toPromise()
        // return this.http.post(API.users + '/changepwd' , {oldPassword: user.oldPassword, newPassword:user.newPassword}, {headers: headers}).toPromise()
            .then(res => res.json() as HttpResponse )
            .catch(GlobalPromiseHttpCatch);
    }

    updateCurrentUserInfo(user: User) {
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');

        return this.http.put(API.users + '/self', JSON.stringify(user), {headers: headers}).toPromise()
            .then(res => res.json() as HttpResponse )
            .catch(GlobalPromiseHttpCatch);
    }



    joinGroup(userId:number, groupId:string) {
        let url = `${API.groups}/${groupId}/${userId}`;
        return this.http.post(url, {}).toPromise()
            .then(res => res.json() as HttpResponse )
            .catch(GlobalPromiseHttpCatch);
    }

    leaveGroup(userId:number, groupId:string) {
        let url = `${API.groups}/${groupId}/${userId}`;
        return this.http.delete(url).toPromise()
            .then(res => res.json() as HttpResponse )
            .catch(GlobalPromiseHttpCatch);
    }

}



@Injectable()
class UserGroupService {

    constructor(
        private http: Http
    ) { }


    getList() {
        return this.http.get(API.users+'/self/groups').toPromise()
            .then( response => {
                var result = response.json() as HttpResponse;
                if (result.data ){
                    result.data.forEach( group => {
                        group.selected = false;
                        if (!group.type) {group.type =''}

                    })
                }
                return result;
            })
            .catch(GlobalPromiseHttpCatch);
    }

    getGroupById(id: string) {
        return this.http.get(API.groups + '/' + id).toPromise()
            .then(response => response.json() as HttpResponse)
            .catch(GlobalPromiseHttpCatch);
    }

    getUserListByGroupId(id: string) {
        return this.http.get(API.groups + '/' + id + '/users').toPromise()
            .then(response => response.json() as HttpResponse)
            .catch(GlobalPromiseHttpCatch);
    }

}



export {UserLoginService, User, UserService, UserGroup, UserGroupService, GroupId}