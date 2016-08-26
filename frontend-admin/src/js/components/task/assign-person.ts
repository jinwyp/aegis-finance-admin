/**
 * Created by liushengbin on 16/8/15.
 */


import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Subscription } from 'rxjs/Subscription';

import { Task, TaskService } from '../../service/task';
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

    taskId : number = 0;
    taskStatus : string = '';


    constructor(
        private activatedRoute: ActivatedRoute,
        private task: TaskService,
        private user: UserService,
        private group: UserGroupService
    ) {}

    ngOnInit(){
        this.sub = this.activatedRoute.params.subscribe(params => {
            this.taskId = params['id'];
        });

        this.activatedRoute.queryParams.subscribe(params => {
            this.taskStatus = params['status'];
            this.getUserList();
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

    getUserList() {
        let groupId : string = '';

        if (this.taskStatus === '分配线上交易员') groupId = GroupId.trader;

        if (this.taskStatus && groupId) {
            this.group.getUserListByGroupId(groupId).then((result)=>{
                if (result.success){
                    this.userList = result.data;

                }else{

                }
            });
        }

    }

}

