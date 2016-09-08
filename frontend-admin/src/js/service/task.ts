/**
 * Created by JinWYP on 8/11/16.
 */

import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';

import {GlobalPromiseHttpCatch, GlobalHeaders, HttpResponse, API } from './http';


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
        'taskDefinitionKey' : 'EndByOnlineTrader'
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
        'taskDefinitionKey' : 'EndBySalesman'
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
        'taskDefinitionKey' : 'salesmanSupplyInvestigationMaterial'
    },
    {
        'id': '22',
        'name': '尽调员审核不通过',
        'taskDefinitionKey' : 'assignOnlineTrader'
    },

    {
        'id': '50',
        'name': '待分配监管员',
        'taskDefinitionKey' : 'assignSupervisor'
    },
    {
        'id': '52',
        'name': '待监管员审核',
        'taskDefinitionKey' : 'supervisorAudit'
    },
    {
        'id': '54',
        'name': '待业务员补充监管材料',
        'taskDefinitionKey' : 'salesmanSupplySupervisionMaterial'
    },
    {
        'id': '56',
        'name': '监管员审核不通过',
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
        'name': '待业务员补充风控材料',
        'taskDefinitionKey' : 'salesmanSupplyRiskManagerMaterial'
    },
    {
        'id': '27',
        'name': '待业务员补充风控材料',
        'taskDefinitionKey' : 'salesmanSupplyRiskManagerMaterial'
    },
    {
        'id': '28',
        'name': '风控人员审核不通过',
        'taskDefinitionKey' : 'EndByRiskManager'
    },
    {
        'id': '30',
        'name': '风控人员填写合同模板并通知用户',
        'taskDefinitionKey' : 'riskManagerAuditSuccess'
    },
    {
        'id': '32',
        'name': '审核通过,流程完成',
        'taskDefinitionKey' : 'completeWorkFlowSuccess'
    }
];

var TaskStatus : any = {};
taskStatusList.forEach( (status) => { TaskStatus[status.taskDefinitionKey] = status.taskDefinitionKey });






class Task {

    // Task 字段
    id :string;
    financeId :number;
    name :string;
    sourceId :string;

    processInstanceId : string;
    taskDefinitionKey : string;
    startTime : string;
    endTime : string;

    assignee : string;
    assigneeDepartment : string;
    assigneeName : string;

    currentAssignee : string;
    currentAssigneeDepartment : string;
    currentAssigneeName : string;
    currentName : string;
    currentTaskDefinitionKey : string;


    // finance Order 字段
    applyType : string;
    applyTypeName :string;
    applyCompanyName : string;
    createTime : string;
    lastUpdateTime : string;
    taskList : Task[] ;
    attachmentList : Array<any>;

    //公用字段
    financingAmount : number;               //拟融资金额（单位：万元）
    expectDate : string;                    //拟使用资金时间（单位：天）
    businessAmount : number;                //预期此笔业务量（单位：万吨）
    transportMode : string;                 //运输方式：海运\汽运\火运\其他

    //煤易融
    sellingPrice : number;                  //预计单吨销售价 (元/吨)
    // contractor : string;                    //签约单位全称
    // downstreamContractor : string;          //下游签约单位全称
    terminalServer : string;                //用煤终端

    //煤易贷
    storageLocation : string;               //煤炭仓储地
    coalSource : string;                    //煤炭来源
    marketPrice : number;                   //单吨市场报价（元／吨）
    coalQuantityIndex : string;             //主要煤质指标

    //煤易购
    procurementPrice : number;              //单吨采购价 (元/吨)
    upstreamResource : string;              //上游资源方全称
    transferPort : string;                  //中转港口全称

    comments : string;                      //备注说明




    //业务员表单字段
    contractCompaniesInfoSupply : string;       //上下游签约单位信息补充
    businessModelIntroduce : string;            //业务操作模式介绍
    logisticsStorageInfoSupply : string;        //物流仓储信息补充
    otherInfoSupply : string;                   //其它补充说明
    supplyMaterialIntroduce : string;           //补充材料说明

    //尽调员表单字段
    financingParty : string;                    //融资方
    ourContractCompany : string;                //我方签约公司
    upstreamContractCompany : string;           //上游签约单位
    downstreamContractCompany : string;         //下游签约单位
    // endUser : string;                           //终端用户
    transportParty : string;                    //运输方
    transitPort : string;                       //中转港口
    qualityInspectionUnit : string;             //质量检验单位
    quantityInspectionUnit : string;            //数量检验单位
    // financingAmount : number;
    financingPeriod : number;                   //融资期限
    interestRate : number;                      //利率
    businessStartTime : string;                 //业务开始时间

    historicalCooperationDetail : string;       //历史合作情况
    mainBusinessInfo : string;                  //业务主要信息
    businessTransferInfo : string;              //业务流转信息
    businessRiskPoint : string;                 //业务风险点
    performanceCreditAbilityEval:string;        //履约信用及能力评估
    finalConclusion:string;                     //综合意见/最终结论
    // supplyMaterialIntroduce : string;

    //监管员表单字段
    // storagePlaceName : string;                  //仓储地名称
    storageProperty : string;                   //仓储性质
    storageAddress : string;                    //仓储地地址
    // historicalCooperationDetail : string;       //历史合作情况
    operatingStorageDetail : string;            //经营及堆存情况
    portStandardDegree : string;                //保管及进出库流程规范程度
    supervisionCooperateDetail : string;        //监管配合情况
    supervisionScheme : string;                 //监管方案
    // finalConclusion : string;                   //最终结论/综合意见
    needSupplyMaterial : number;               //需要补充材料 true: 需要, false: 不需要
    // supplyMaterialIntroduce : string;           //补充材料说明
    noticeApplyUser : number;                  //通知申请用户 true: 通知, false: 不通知
    noticeSalesman : boolean;                   //通知业务员   true: 通知, false: 不通知

    distributionAbilityEval : string;           //分销能力评估
    paymentSituationEval : string;              //预计回款情况
    // businessRiskPoint : string;                 //业务风险点
    riskControlScheme : string;                 //风险控制方案
    // finalConclusion : string;                   //风控结论/最终结论/综合意见
    // needSupplyMaterial : boolean;               //需要补充材料 true: 需要, false: 不需要
    // supplyMaterialIntroduce : string;           //补充材料说明
    // noticeApplyUser : boolean;                  //通知申请用户 true: 通知, false: 不通知
    // noticeSalesman : boolean;                   //通知业务员   true: 通知, false: 不通知
    editContract : boolean;                     //编辑合同     true: 需要编辑, false: 不需要编辑


    constructor() {
        this.id  = '';
        this.processInstanceId  = '';
    }
}





@Injectable()
class TaskService {

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
            .catch(GlobalPromiseHttpCatch);
    }

    getTaskHistoryList() {
        return this.http.get(API.tasks + '/history').toPromise()
            .then(response => response.json() as HttpResponse)
            .catch(GlobalPromiseHttpCatch);
    }

    getAdminTaskList() {
        return this.http.get(API.tasks + '/unclaimed').toPromise()
            .then(response => response.json() as HttpResponse)
            .catch(GlobalPromiseHttpCatch);
    }

    getTaskInfoById(taskId : string) {
        return this.http.get(API.tasks + '/' + taskId).toPromise()
            .then(response => response.json() as HttpResponse)
            .catch(GlobalPromiseHttpCatch);
    }

    getOrderStepsById(orderId : string) {
        return this.http.get(API.orders + '/' + orderId + '/tasks').toPromise()
            .then(response => response.json() as HttpResponse)
            .catch(GlobalPromiseHttpCatch);
    }

    getOrderInfoById(orderId : number, taskStep : string = 'onlinetrader') {

        let auditStep = {
            onlinetrader : '',
            salesman : '/salesman',
            investigator : '/investigator',
            supervisor : '/supervisor',
            riskmanager : '/riskmanager'
        };

        return this.http.get(API.orders + '/' + orderId + auditStep[taskStep]).toPromise().then(response => {
            var result = response.json() as HttpResponse;

            return this.http.get(API.orders + '/' + orderId + '/tasks').toPromise().then( response2 => {
                var result2 = response2.json() as HttpResponse;
                if (!result.data) {result.data = {taskList:[]}}
                result.data.taskList = result2.data;
                return result;
            }).catch(GlobalPromiseHttpCatch);

        })
        .catch(GlobalPromiseHttpCatch);
    }

    getOrder2InfoById(orderId : number, taskStep : string = 'onlinetrader') {

        let auditStep = {
            onlinetrader : '',
            salesman : '/salesman',
            investigator : '/investigator',
            supervisor : '/supervisor',
            riskmanager : '/riskmanager'
        };

        return this.http.get(API.orders + '/' + orderId + auditStep[taskStep]).toPromise()
            .then(response => response.json() as HttpResponse)
            .catch(GlobalPromiseHttpCatch);
    }


    assignPerson(taskId : string, userId : string) {

        return this.http.post(API.tasks + '/' + taskId + '/claim', {}).toPromise()
            .then<HttpResponse>( (response) => {
                var result : HttpResponse = response.json();
                if (result.success ){
                    return this.http.put(API.tasks + '/' + taskId + '/person/' + userId, {}).toPromise()
                        .then(response => response.json() as HttpResponse)
                }else{
                    return result
                }
            })
            .catch(GlobalPromiseHttpCatch);
    }


    audit(taskId : string, taskType : string, taskStep : string, isSubmit : boolean, body : any) {

        let auditStep = {
            onlinetrader : 'onlinetrader',
            salesman : 'salesman',
            investigator : 'investigator',
            supervisor : 'supervisor',
            riskmanager : 'riskmanager'
        };

        let auditType = {
            MYR : API.tasksMYR,
            MYD : API.tasksMYD,
            MYG : API.tasksMYG
        };

        let type :number = isSubmit ? 1 : 0;

        let url = auditType[taskType] + '/' + auditStep[taskStep] + '/audit/' + taskId +'?type=' + type;

        let sendData : any = {};

        if (isSubmit){
            sendData = body;
        }else{
            sendData = body.u;
        }

        return this.http.post(url, JSON.stringify(sendData), {headers: GlobalHeaders}).toPromise()
            .then(response => response.json() as HttpResponse)
            .catch(GlobalPromiseHttpCatch);
    }



    addMaterial (taskId : string, taskType : string, taskStep : string, attachmentList : any){

        let auditType = {
            MYR : API.tasksMYR,
            MYD : API.tasksMYD,
            MYG : API.tasksMYG
        };

        let auditStep = {
            salesman1 : '/salesman/supply/investigation/material/',
            salesman2 : '/salesman/supply/supervision/material/',
            investigator : '/investigator/supply/riskmanager/material/',
            supervisor : '/supervisor/supply/riskmanager/material/'
        };

        let url = auditType[taskType] + auditStep[taskStep] + taskId;


        return this.http.post(url, JSON.stringify(attachmentList), {headers: GlobalHeaders}).toPromise()
            .then(response => response.json() as HttpResponse)
            .catch(GlobalPromiseHttpCatch);
    }

}





export {Task, TaskService, TaskStatus}