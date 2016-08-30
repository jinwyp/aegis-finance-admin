/**
 * Created by JinWYP on 8/11/16.
 */

import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';

import { HttpResponse, API } from './http';


var taskStatusList = [
    {
        'id': '2',
        'name': '客户发起申请',
        'taskDefinitionKey' : ''
    },
    {
        'id': '4',
        'name': '待分配线上交易员',
        'taskDefinitionKey' : 'assignOnlineTrader'
    },
    {
        'id': '6',
        'name': '待线上交易员审核',
        'taskDefinitionKey' : 'onlineTraderAudit'
    },
    {
        'id': '8',
        'name': '线上交易员审核不通过',
        'taskDefinitionKey' : 'assignOnlineTrader'
    },
    {
        'id': '10',
        'name': '待分配业务员',
        'taskDefinitionKey' : 'assignSalesman'
    },
    {
        'id': '12',
        'name': '待业务员审核',
        'taskDefinitionKey' : 'salesmanAudit'
    },
    {
        'id': '14',
        'name': '业务员审核不通过',
        'taskDefinitionKey' : 'assignOnlineTrader'
    },
    {
        'id': '16',
        'name': '待分配尽调员',
        'taskDefinitionKey' : 'assignInvestigator'
    },
    {
        'id': '18',
        'name': '待尽调员审核',
        'taskDefinitionKey' : 'investigatorAudit'
    },
    {
        'id': '20',
        'name': '待业务员补充尽调材料',
        'taskDefinitionKey' : 'assignOnlineTrader'
    },
    {
        'id': '22',
        'name': '尽调员审核不通过',
        'taskDefinitionKey' : 'assignOnlineTrader'
    },
    {
        'id': '24',
        'name': '待分配风控人员',
        'taskDefinitionKey' : 'assignRiskManager'
    },
    {
        'id': '25',
        'name': '待风控员审核',
        'taskDefinitionKey' : 'riskManagerAudit'
    },
    {
        'id': '26',
        'name': '待尽调员补充风控材料',
        'taskDefinitionKey' : 'assignOnlineTrader'
    },
    {
        'id': '28',
        'name': '风控人员审核不通过',
        'taskDefinitionKey' : 'assignOnlineTrader'
    },
    {
        'id': '30',
        'name': '风控人员填写合同模板并通知用户',
        'taskDefinitionKey' : 'riskManagerAuditSuccess'
    },
    {
        'id': '32',
        'name': '审核通过,流程完成',
        'taskDefinitionKey' : 'assignOnlineTrader'
    }
];

var TaskStatus : any = {};
taskStatusList.forEach( (status) => { TaskStatus[status.taskDefinitionKey] = status.taskDefinitionKey });






class Task {

    id :string;
    name :string;
    applyType : string;
    createTime : string;
    processInstanceId : string;
    taskDefinitionKey : string;


    financingAmount : number;
    expectDate : string;
    businessAmount : number;
    transportMode : string;

    applyCompanyName : string;
    assignee : string;
    assigneeDepartment : string;
    assigneeName : string;



    constructor() {
        this.id  = '';
        this.processInstanceId  = '';
    }
}





@Injectable()
class TaskService {

    private handleError(error: any): Promise<any> {
        console.error('Http 任务 请求发生错误!! ', error);
        return Promise.reject(error.message || error);
    }

    private AllTaskListInfo = new BehaviorSubject<any>(null);
    private PendingTaskListInfo = new BehaviorSubject<any>(null);
    getAllTaskLengthObservable = this.AllTaskListInfo.asObservable();
    getPendingTaskLengthObservable = this.PendingTaskListInfo.asObservable();
    setAllTaskLengthObservable (allTaskLength : number){ this.AllTaskListInfo.next({allTaskLength : allTaskLength }) };
    setPendingTaskLengthObservable (pendingTaskLength : number ){ this.PendingTaskListInfo.next({pendingTaskLength : pendingTaskLength}) };



    constructor(
        private http: Http
    ) { }


    getTaskList() {
        return this.http.get(API.tasks).toPromise()
            .then(response => response.json() as HttpResponse)
            .catch(this.handleError);
    }

    getTaskHistoryList() {
        return this.http.get(API.tasks + '/history').toPromise()
            .then(response => response.json() as HttpResponse)
            .catch(this.handleError);
    }

    getAdminTaskList() {
        return this.http.get(API.tasks + '/unclaimed').toPromise()
            .then(response => response.json() as HttpResponse)
            .catch(this.handleError);
    }

    getTaskInfoById(taskId : string) {
        return this.http.get(API.tasks + '/' + taskId).toPromise()
            .then(response => response.json() as HttpResponse)
            .catch(this.handleError);
    }

    assignPerson(taskId : string, userId : string) {

        return this.http.post(API.tasks + '/' + taskId + '/claim', {}).toPromise()
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

        return this.http.post(url, {} ).toPromise()
            .then(response => response.json() as HttpResponse)
            .catch(this.handleError);
    }

}





export {Task, TaskService, TaskStatus}