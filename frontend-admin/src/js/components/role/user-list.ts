/**
 * Created by liushengbin on 16/8/15.
 */


import { Component } from '@angular/core';

import { User, UserService, UserGroup, UserGroupService } from '../../service/user';


declare var __moduleName: string;

@Component({
    selector: 'user-list',
    moduleId: __moduleName || module.id,
    templateUrl: 'user-list.html'
})



export class UserListComponent {

    constructor(
        private user: UserService
    ) {}

    userList : User[];


    ngOnInit() {
        this.getUserList();
    }


    getUserList() {

        this.user.getList().then((result)=>{
            if (result.success){
                this.userList = result.data;
            }else{

            }
        });
    }

}

