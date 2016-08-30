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

    constructor(
        private activatedRoute: ActivatedRoute,
        private userService: UserService,
        private groupService:UserGroupService
    ) {}

    css = {
        activeForRefresh : true,
        isSubmitted :      false,
        ajaxErrorHidden :  true
    };


    isAddStatus : boolean = false;

    private sub:Subscription;
            currentUser = new User();

    groups       = [];
    departments  = [];
    selectedItem = '请选择';

    ngOnInit() {

        if (this.activatedRoute.routeConfig.path.indexOf('add') > -1) {
            this.isAddStatus  = true;
            this.getGroupList();
        } else {
            this.sub = this.activatedRoute.params.subscribe(params => {
                this.getUserInfo(params['id']);
            });
        }
        this.getDepartmentList();
    }


    ngOnDestroy() {
        if (this.sub) {
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
            } else {

            }
        });
    }

    getUserInfo(id) {

        this.userService.getUserById(id).then((result)=> {
            if (result.success) {
                this.currentUser = result.data;
                this.selectedItem = this.currentUser.department;
                this.getGroupList();

            } else {

            }
        });
    }

    changeSelectGroup (event){
        this.currentUser.groupIds = event.selectedIds;
    }


    addUser() {
        this.css.ajaxErrorHidden = true;
        this.css.isSubmitted     = true;
        this.currentUser.department = this.selectedItem;
        if (this.isAddStatus) {

            this.userService.add(this.currentUser).then((result)=> {
                if (result.success) {
                    alert('添加用户成功');
                    this.clear();
                } else {
                    alert(result.error.message);
                    this.css.ajaxErrorHidden = false;
                }
            });
        } else {
            this.userService.update(this.currentUser).then((result)=> {
                if (result.success) {
                    alert('保存用户成功');
                } else {
                    alert(result.error.message);
                    this.css.ajaxErrorHidden = false;
                }
            });
        }
    }


    clear() {
        this.currentUser.username   = '';
        this.currentUser.name       = '';
        this.currentUser.email      = '';
        this.currentUser.phone      = '';
        this.currentUser.department = '';
        this.currentUser.groupIds   = [];
        this.selectedItem     = '请选择';
    }


}

