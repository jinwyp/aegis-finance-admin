/**
 * Created by JinWYP on 8/11/16.
 */

import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { HttpResponse, API } from './http';



class Task {

    id :number;
    applyType : string;
    applyTime : string;

    companyName : string;

    createTime : number;

    constructor() {
        this.id  = 0;
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
        return this.http.get(API.task).toPromise()
            .then(response => response.json() as HttpResponse)
            .catch(this.handleError);
    }

    getAdminTaskList() {
        return this.http.get(API.task + '/unclaimed').toPromise()
            .then(response => response.json() as HttpResponse)
            .catch(this.handleError);
    }


    assignTrader(taskId : string, userId : string) {

        return this.http.post(API.users + '/' + taskId, {}).toPromise()
            .then(res => res.json() as HttpResponse )
            .catch(this.handleError);
    }

}





export {Task, TaskService}