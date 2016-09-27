/**
 * Created by liushengbin on 16/8/15.
 */


import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Subscription } from 'rxjs/Subscription';

import {TaskService, Task, TaskStatus} from '../../service/task';
import { GroupId, User, UserService, UserGroupService } from '../../service/user';

declare var __moduleName: string;

@Component({
    selector: 'assign-person',
    moduleId: module.id,
    templateUrl: 'assign-person.html'
})
export class AssignPersonComponent {

    private sub: Subscription;

    css = {
        isSubmitted : true,
        ajaxErrorHidden : true,
        ajaxSuccessHidden : true
    };
    errorMsg = '';

    currentUserSession : User = new User();
    selectedUser : User = new User();
    userList : User[] = [];

    taskId : string = '';
    currentTask : Task = new Task();
    taskStatusStep : any = TaskStatus;

    constructor(
        private activatedRoute: ActivatedRoute,
        private task: TaskService,
        private user: UserService,
        private group: UserGroupService
    ) {}

    ngOnInit(){
        this.sub = this.activatedRoute.params.subscribe(params => {
            this.taskId = params['id'];
            this.getTaskInfo(params['id']);
        });

        this.getCurrentUser();
    }

    getCurrentUser() {
        this.user.getUserSessionObservable.subscribe(
            result => {
                if (result && result.success) {
                    this.currentUserSession = result.data;
                } else {

                }
            },
            error => console.error(error)
        )
    }

    getTaskInfo (id) {
        this.task.getTaskInfoById(id).then((result)=>{
            if (result.success){
                this.currentTask = result.data;

                this.getUserList();
            }else{

            }
        });
    }

    getUserList() {
        let groupId : string = '';

        if (this.currentTask.taskDefinitionKey === TaskStatus.assignOnlineTrader) groupId = GroupId.trader;  // 分配线上交易员
        if (this.currentTask.taskDefinitionKey === TaskStatus.assignSalesman) groupId = GroupId.salesman;  // 分配业务员
        if (this.currentTask.taskDefinitionKey === TaskStatus.assignInvestigator) groupId = GroupId.investigator; // 分配尽调员
        if (this.currentTask.taskDefinitionKey === TaskStatus.assignSupervisor) groupId = GroupId.supervisor; // 分配尽调员
        if (this.currentTask.taskDefinitionKey === TaskStatus.assignRiskManager) groupId = GroupId.riskmanager; // 分配风控人员

        if (this.currentTask.taskDefinitionKey && groupId) {
            this.group.getUserListByGroupId(groupId).then((result)=>{
                if (result.success){
                    this.userList = result.data;

                }else{

                }
            });
        }
    }

    assignPerson (){

        this.css.ajaxErrorHidden = true;
        this.css.ajaxSuccessHidden = true;
        this.css.isSubmitted = true;

        if (this.selectedUser.id){
            this.task.assignPerson(this.taskId, this.selectedUser.id).then((result)=>{
                if (result.success){
                    this.css.ajaxSuccessHidden = false;
                }else{
                    this.errorMsg = result.error.message;
                    this.css.ajaxErrorHidden = false;
                    this.css.isSubmitted = false;
                }
            });
        }
    }

}

