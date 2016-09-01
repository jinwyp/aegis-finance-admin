/**
 * Created by liushengbin on 16/8/15.
 */


import {Component} from '@angular/core';

import {User, UserService, UserGroupService} from '../../service/user';


declare var __moduleName:string;

@Component({
    selector :    'user-list',
    moduleId :    __moduleName || module.id,
    templateUrl : 'user-list.html'
})
export class UserListComponent {

    constructor(
        private groupService:UserGroupService,
        private user:UserService
    ) {}

    name:string = '';
    username:string = '';
    groupName:string = '';
    selectedItem = '';
    groups       = [];
    userList : User[];
    isHiddenDelModal : boolean = true;
    isHiddenResetModal : boolean = true;
    isHiddenMsgModal : boolean = true;
    btnClick : boolean    = false;
    userId : string       = '';
    modalShowText : string       = '';


    ngOnInit() {
        this.getUserList();
        this.getGroupList();
    }


    getUserList() {
        this.groupName=this.selectedItem;
        this.user.getList(this.name,this.username,this.groupName).then((result)=> {
            if (result.success) {
                this.userList = result.data;
            } else {

            }
        });
    }

    showDelModal(id:string, modalShowText:string) {
        this.isHiddenDelModal = false;
        this.modalShowText = modalShowText;
        this.userId = id;
    }

    showResetModal(id:string, modalShowText:string) {
        this.isHiddenResetModal = false;
        this.modalShowText = modalShowText;
        this.userId = id;
    }

    hiddenModal() {
        this.userId = '';
    }

    delUser(){
        if (this.userId){
            this.user.del(this.userId).then((result)=> {
                if (result.success) {
                    this.getUserList();
                } else {

                }
            });
        }
    }

    getGroupList() {
        this.groupService.getList().then((result)=> {
            if (result.success) {
                this.groups = result.data;
            } else {

            }
        });
    }

    resetPwd(){
        if (this.userId){
            this.user.resetPassword(this.userId).then((result)=> {
                if (result.success) {
                    this.modalShowText='重置密码成功!';
                } else {
                    this.modalShowText=result.error.message;
                }
                this.isHiddenMsgModal=false;
            });
        }
    }

}

