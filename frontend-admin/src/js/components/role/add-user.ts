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
        ajaxSuccessHidden : true,
        ajaxErrorHidden : true,
        activeForRefresh : true,
        isSubmitted :      false,
        isHiddenResetModal : true,
        isHiddenMsgModal : true,
        isAddStatus :  false
    };
    errorMsg = '';

    userId : string ='';

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


    addUser() {
        this.css.isSubmitted     = true;
        this.currentUser.department = this.selectedItem;

        if (this.css.isAddStatus) {
            this.userService.add(this.currentUser).then((result)=> {
                this.css.isSubmitted     = false;
                if (result.success) {
                    this.css.ajaxSuccessHidden=false;
                    setTimeout(() => this.css.ajaxSuccessHidden = true, 3000);
                    this.clear();
                } else {
                    this.errorMsg=result.error.message;
                }
            });
        } else {
            this.userService.update(this.currentUser).then((result)=> {
                this.css.isSubmitted     = false;
                if (result.success) {
                    this.css.ajaxSuccessHidden=false;
                    setTimeout(() => this.css.ajaxSuccessHidden = true, 3000);
                } else {
                    this.errorMsg=result.error.message;
                }
            });
        }
    }

    showResetModal(id:string, modalShowText:string) {
        this.css.isHiddenResetModal = false;
        this.modalShowText = modalShowText;
        this.userId = id;
    }

    hiddenModal() {
        this.userId = '';
    }

    resetPwd(){
        if (this.userId){
            this.userService.resetPassword(this.userId).then((result)=> {
                if (result.success) {
                    this.modalShowText='重置密码成功!';
                } else {
                    this.modalShowText=result.error.message;
                }
                this.css.isHiddenMsgModal=false;
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
        this.selectedItem     = '';
        // this.css.activeForRefresh = false;
        // setTimeout(() => this.css.activeForRefresh = true, 0);
    }


}

