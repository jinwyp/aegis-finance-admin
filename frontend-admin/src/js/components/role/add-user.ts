/**
 * Created by liushengbin on 16/8/15.
 */


import { Component } from '@angular/core';
import { Router, ActivatedRoute }      from '@angular/router';
import { User, UserService, UserGroup, UserGroupService } from '../../service/user';


declare var __moduleName: string;

@Component({
    selector: 'add-user',
    moduleId: __moduleName || module.id,
    templateUrl: 'add-user.html'
})



export class AddUserComponent {

    constructor(
        private route: ActivatedRoute,
        private userService: UserService,
        private groupService:UserGroupService
    ) {}

    isAddStatus : boolean = false;

    currentUser = new User();
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
    groups = [];

    ngOnInit(){
        this.getGroupList();

        console.log(this.route.data)

        // if (this.route.data.type === 'add') {
        //     this.isAddStatus = true;
        // }

    }

    css = {
        activeForRefresh : true,
        isSubmitted : false,
        ajaxErrorHidden : true
    };


    getGroupList() {

        this.groupService.getList().then((result)=>{
            if (result.success){
                this.groups = result.data;

                this.groups.forEach( (group)=> {
                    if (this.currentUser.groupIds.indexOf(group.id) > -1) {
                        group.selected = true;
                    }
                });

            }else{

            }
        });
    }

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

