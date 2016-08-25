/**
 * Created by liushengbin on 16/8/15.
 */


import {Component} from '@angular/core';
import {ActivatedRoute}      from '@angular/router';
import {Subscription} from 'rxjs/Subscription';

import {User, UserService, UserGroupService} from '../../service/user';


declare var __moduleName:string;

@Component({
    selector :    'add-user',
    moduleId :    __moduleName || module.id,
    templateUrl : 'add-user.html'
})


export class AddUserComponent {

    constructor(private activatedRoute:ActivatedRoute,
                private userService:UserService,
                private groupService:UserGroupService) {
    }

    css = {
        activeForRefresh : true,
        isSubmitted :      false,
        ajaxErrorHidden :  true
    };

    isAddStatus:boolean = false;
    addUserTitle        = '添加用户';

    private sub:Subscription;
            currentUser = new User();

    groups       = [];
    departments  = [];
    selectedItem = {name : this.currentUser.department};

    ngOnInit() {

        if (this.activatedRoute.routeConfig.path.indexOf('add') > -1) {
            this.isAddStatus  = true;
            this.addUserTitle = '添加用户';
            this.getGroupList();
        } else {
            this.sub = this.activatedRoute.params.subscribe(params => {
                this.getUserInfo(params['id']);
                this.addUserTitle = '编辑用户';
            });
        }
        this.getDepartmentList();
    }


    ngOnDestroy() {
        if (this.activatedRoute.routeConfig.path.indexOf('add') > -1) {

        } else {
            this.sub.unsubscribe();
        }
    }

    getGroupList() {

        this.groupService.getList().then((result)=> {
            if (result.success) {
                this.groups = result.data;

                this.groups.forEach((group)=> {
                    if (this.currentUser.groupIds.indexOf(group.id) > -1) {
                        group.selected = true;
                    }
                });

            } else {

            }
        });
    }

    getDepartmentList() {
        this.userService.getDepartmentList().then((result)=> {
            if (result.success) {
                this.departments = result.data;
                console.log(result);
            } else {

            }
        });
    }

    getUserInfo(id) {

        this.userService.getUserById(id).then((result)=> {
            if (result.success) {
                this.currentUser = result.data;
                console.log(this.currentUser);
                this.getGroupList();
            } else {

            }
        });
    }

    selectGroup(group) {
        if (this.currentUser.groupIds.indexOf(group.id) === -1) {
            this.currentUser.groupIds.push(group.id);
            group.selected = true;
            // 最多只能同时拥有3个角色
            // if (this.currentUser.groupIds.length < 3){
            //     this.currentUser.groupIds.push(group.id);
            //     group.selected = true;
            // }
        } else {
            this.currentUser.groupIds.splice(this.currentUser.groupIds.indexOf(group.id), 1);
            group.selected = false;
        }
    }


    addUser() {
        this.css.ajaxErrorHidden = true;
        this.css.isSubmitted     = true;
        if (this.isAddStatus) {
            this.userService.add(this.currentUser).then((result)=> {
                if (result.success) {
                    window.location.href = '/finance/admin/home/users';
                } else {
                    this.css.ajaxErrorHidden = false;
                }
                console.log(result)
            });
        } else {
            this.userService.save(this.currentUser).then((result)=> {
                if (result.success) {
                    window.location.href = '/finance/admin/home/users';
                } else {
                    this.css.ajaxErrorHidden = false;
                }
                console.log(result)
            });
        }
    }

    selectChange($event) {
        this.currentUser.department = $event.name;
        this.selectedItem.name      = $event.name;
        console.log(this.currentUser);
    }

}

