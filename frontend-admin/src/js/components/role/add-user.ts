/**
 * Created by liushengbin on 16/8/15.
 */


import { Component } from '@angular/core';
import { User, UserService, UserGroup, UserGroupService, groupList } from '../../service/user';


declare var __moduleName: string;

@Component({
    selector: 'add-user',
    moduleId: __moduleName || module.id,
    templateUrl: 'add-user.html'
})



export class AddUserComponent {

    constructor(
        private userService: UserService,
        private groupService:UserGroupService
    ) {}

    currentUser=new User();
    // currentUser = {
    //     "id": 1,
    //     "username": "",
    //     "name": "liushengbin",
    //     "phone": "18621266707",
    //     "email": "liushengbin@yimei180.com",
    //     "department": "技术部",
    //     "password": "string",
    //     "groupIds":["10001","10005"]
    // };
    groups=groupList;

    ngOnInit(){
        this.groups.forEach( (group)=> {
            if (this.currentUser.groupIds.indexOf(group.id) > -1) {
                group.selected = true;
            }
        })
    }

    css = {
        activeForRefresh : true,
        isSubmitted : false,
        ajaxErrorHidden : true
    };


    selectGroup(group){
        if (this.currentUser.groupIds.indexOf(group.id) === -1 ){
            this.currentUser.groupIds.push(group.id);
        }else{
            this.currentUser.groupIds.splice(this.currentUser.groupIds.indexOf(group.id), 1);
        }

        console.log(this.currentUser.groupIds);
    }
    addUser(form) {
        this.css.ajaxErrorHidden = true;
        this.css.isSubmitted = true;
        this.userService.add(this.currentUser).then((result)=>{
            if (result.success){
                window.location.href = '/finance/admin/home';
            }else{
                this.css.ajaxErrorHidden = false;
            }
            console.log(result)
        });
    }

}

