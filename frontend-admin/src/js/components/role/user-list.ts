/**
 * Created by liushengbin on 16/8/15.
 */


import {Component} from '@angular/core';

import {User, UserService, UserGroup, UserGroupService} from '../../service/user';
import {window} from "@angular/platform-browser/src/facade/browser";


declare var __moduleName:string;

@Component({
    selector :    'user-list',
    moduleId :    __moduleName || module.id,
    templateUrl : 'user-list.html'
})
export class UserListComponent {

    constructor(private user:UserService) {
    }

    userList : User[];
    isHiddenModal : boolean = true;
    btnClick : boolean    = false;
    userId : string       = '';


    ngOnInit() {
        this.getUserList();
    }


    getUserList() {
        this.user.getList().then((result)=> {
            if (result.success) {
                this.userList = result.data;
                console.log(result);
            } else {

            }
        });
    }

    showModal(id:string) {
        this.isHiddenModal = false;
        this.userId = id;
        console.log(this.isHiddenModal);
    }

    hiddenModal() {
        this.isHiddenModal = true;
        this.userId = '';
        console.log(this.isHiddenModal);
    }


    delUser(){
        if (this.userId){
            this.user.del(this.userId).then((result)=> {
                // console.log(result);
                if (result.success) {
                    this.getUserList();
                } else {

                }
            });
        }
    }

}

