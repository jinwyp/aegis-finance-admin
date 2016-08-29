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
    moduleId: __moduleName || module.id,
    templateUrl: 'assign-person.html'
})
export class AssignPersonComponent {

    private sub: Subscription;

    currentUserSession : User = new User();
    selectedUser : User = new User();
    userList : User[] = [];

    taskId : string = '';
    currentTask : Task = new Task();


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

        if (this.currentTask.taskDefinitionKey === TaskStatus.assignOnlineTrader) groupId = GroupId.trader;  //分配线上交易员
        if (this.currentTask.taskDefinitionKey === '分配业务员') groupId = GroupId.salesman;
        if (this.currentTask.taskDefinitionKey === '分配尽调员') groupId = GroupId.investigator;
        if (this.currentTask.taskDefinitionKey === '分配风控人员') groupId = GroupId.riskmanager;

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
        this.task.assignPerson(this.taskId, this.selectedUser.id).then((result)=>{
            if (result.success){
                alert('分配成功!!')

            }else{
                alert('分配失败!')
            }
        });
    }

}

