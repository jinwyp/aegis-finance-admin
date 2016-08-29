/**
 * Created by JinWYP on 8/11/16.
 */

import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { HttpResponse, API } from './http';


var taskStatus = [
    {
        "id": "2",
        "name": "客户发起申请"
    },
    {
        "id": "4",
        "name": "待分配线上交易员"
    },
    {
        "id": "6",
        "name": "待线上交易员审核"
    },
    {
        "id": "8",
        "name": "线上交易员审核不通过"
    },
    {
        "id": "10",
        "name": "待分配业务员"
    },
    {
        "id": "12",
        "name": "待业务员审核"
    },
    {
        "id": "14",
        "name": "业务员审核不通过"
    },
    {
        "id": "16",
        "name": "待分配尽调员"
    },
    {
        "id": "18",
        "name": "待尽调员审核"
    },
    {
        "id": "20",
        "name": "待业务员补充尽调材料"
    },
    {
        "id": "22",
        "name": "尽调员审核不通过"
    },
    {
        "id": "24",
        "name": "待分配风控人员"
    },
    {
        "id": "26",
        "name": "待尽调员补充风控材料"
    },
    {
        "id": "28",
        "name": "风控人员审核不通过"
    },
    {
        "id": "30",
        "name": "风控人员填写合同模板并通知用户"
    },
    {
        "id": "32",
        "name": "审核通过,流程完成"
    }
];




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

        return this.http.post(API.tasks + '/claim/' + taskId, {}).toPromise()
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

    audit(taskId : string, auditType : string, isApproved: number = 0, isNeedFile : number = 0) {

        let auditStep = {
            onlinetrader : 'onlinetrader',
            salesman : 'salesman',
            investigator : 'investigator',
            riskmanager : 'riskmanager'

        };

        let url = `${API.tasksMYR}/${auditStep[auditType]}/audit/${taskId}?pass=${isApproved}&need=${isNeedFile}`;

        return this.http.put(url, {} ).toPromise()
            .then(response => response.json() as HttpResponse)
            .catch(this.handleError);
    }

}





export {Task, TaskService}