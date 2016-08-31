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
        isHiddenSaveText : true,
        isHiddenModal : true,
        isAddStatus :  false
    };

    private sub:Subscription;
            currentUser = new User();

    groups       = [];
    departments  = [];
    selectedItem = '';
    modalShowText : string ='';

    ngOnInit() {

        if (this.activatedRoute.routeConfig.path.indexOf('add') > -1) {
            this.css.isAddStatus  = true;
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
                this.setCheckBoxStatus();
            } else {

            }
        });
    }

    setCheckBoxStatus(){
        this.groups.forEach((group)=> {
            if (this.currentUser.groupIds.indexOf(group.id) > -1) {
                group.selected = true;
            }else{
                group.selected = false;
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
        console.log(this.currentUser.groupIds);
    }


    addUser() {
        this.css.isSubmitted     = true;
        this.css.activeForRefresh = false;
        this.currentUser.department = this.selectedItem;
        console.log(this.currentUser);
        if (this.css.isAddStatus) {
            this.userService.add(this.currentUser).then((result)=> {
                this.css.isSubmitted     = false;
                this.css.activeForRefresh = true;
                if (result.success) {
                    this.css.isHiddenSaveText=false;
                    this.clear();
                    setTimeout(() => this.css.isHiddenSaveText = true, 6000);
                } else {
                    this.css.isHiddenModal=false;
                    this.modalShowText=result.error.message;
                    // alert(result.error.message);
                }
            });
        } else {
            this.userService.update(this.currentUser).then((result)=> {
                this.css.isSubmitted     = false;
                this.css.activeForRefresh = true;
                if (result.success) {
                    this.css.isHiddenSaveText=false;
                    setTimeout(() => this.css.isHiddenSaveText = true, 0);
                } else {
                    this.css.isHiddenModal=false;
                    this.modalShowText=result.error.message;
                    // alert(result.error.message);
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
        this.setCheckBoxStatus();
        this.selectedItem     = '请选择';
        console.log('--------clear--------');
        console.log(this.groups);
        console.log(this.currentUser);
    }


}

