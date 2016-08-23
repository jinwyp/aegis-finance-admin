/**
 * Created by liushengbin on 16/8/15.
 */


import { Component } from '@angular/core';

import { User, UserService, UserGroup, UserGroupService } from '../../service/user';


declare var __moduleName: string;

@Component({
    selector: 'role-list',
    moduleId: __moduleName || module.id,
    templateUrl: 'role-list.html'
})



export class RoleListComponent {

    constructor(
        private group: UserGroupService
    ) {}

    groupList : UserGroup[];


    ngOnInit() {
        this.getGroupList();
    }


    getGroupList() {

        this.group.getList().then((result)=>{
            if (result.success){
                this.groupList = result.data;
            }else{

            }
            console.log(result)
        });
    }
}

