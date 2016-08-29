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
    modalStatus :boolean = true;


    ngOnInit() {
        this.getUserList();
    }


    getUserList() {

        this.user.getList().then((result)=>{
            if (result.success){
                this.userList = result.data;
                console.log(result);
            }else{

            }
        });
    }

    showModal(){
        this.modalStatus = false;
        console.log('showModal');
        console.log(this.modalStatus);
    }

    hideModal(){
        this.modalStatus = true;
        console.log('hideModal');
        console.log(this.modalStatus);
    }

    stateChange($event){
        this.modalStatus=$event.name;
    }

}

