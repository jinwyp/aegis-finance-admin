/**
 * Created by JinWYP on 8/11/16.
 */

import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { HttpResponse, API } from './http';



class Task {

    id :string;
    applyType : string;
    applyTime : string;

    companyName : string;

    createTime : number;

    constructor() {
        this.id  = '';
    }
}





@Injectable()
class TaskService {

    private handleError(error: any): Promise<any> {
        console.error('Http 任务 请求发生错误!! ', error);
        return Promise.reject(error.message || error);
    }

    constructor(
        private http: Http
    ) { }


    getTaskList() {
        return this.http.get(API.tasks).toPromise()
            .then(response => response.json() as HttpResponse)
            .catch(this.handleError);
    }

    getAdminTaskList() {
        return this.http.get(API.tasks + '/unclaimed').toPromise()
            .then(response => response.json() as HttpResponse)
            .catch(this.handleError);
    }


    assignPerson(taskId : string, userId : string) {

        return this.http.post(API.tasks + '/' + taskId, {}).toPromise()
            .then( (response) => {
                var result = response.json();
                if (result.data ){
                    return this.http.put(API.tasks + '/' + taskId + '/person/' + userId, {}).toPromise()
                }else{
                    Promise.reject('管理员领取任务失败!')
                }
            })
            .then(response => response.json() as HttpResponse)
            .catch(this.handleError);
    }

}





export {Task, TaskService}