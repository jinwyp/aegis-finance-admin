/**
 * Created by liushengbin on 16/8/15.
 */


import {Component} from '@angular/core';

import {User, UserService, UserGroup, UserGroupService} from '../../service/user';


declare var __moduleName:string;

@Component({
    selector :    'user-list',
    moduleId :    __moduleName || module.id,
    templateUrl : 'user-list.html'
})
export class UserListComponent {

    constructor(
        private user:UserService
    ) {}

    name:string = '';
    username:string = '';
    groupName:string = '';
    userList : User[];
    isHiddenDelModal : boolean = true;
    isHiddenResetModal : boolean = true;
    isHiddenMsgModal : boolean = true;
    btnClick : boolean    = false;
    userId : string       = '';
    modalShowText : string       = '';


    ngOnInit() {
        this.getUserList();
    }


    getUserList() {
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

