/**
 * Created by liushengbin on 16/8/15.
 */


import {Component} from '@angular/core';
import {ActivatedRoute}      from '@angular/router';
import { Location } from '@angular/common';
import {Subscription} from 'rxjs/Subscription';

import {User, UserService, UserGroupService} from '../../service/user';
import { RiskService } from '../../service/risk'


declare var __moduleName:string;


@Component({
    selector :    'add-user',
    moduleId :    module.id,
    templateUrl : 'add-user.html'
})
export class AddUserComponent {

    private sub:Subscription;

    constructor(
        private location: Location,
        private activatedRoute: ActivatedRoute,
        private userService: UserService,
        private riskService: RiskService,
        private groupService:UserGroupService
    ) {}

    css = {
        formActiveForRefresh : true,
        ajaxSuccessHidden : true,
        ajaxErrorHidden : true,
        isSubmitted :      false,
        isHiddenResetModal : true,
        isHiddenMsgModal : true,
        isAddStatus :  false,
        isAdminUser :  false
    };
    errorMsg = '';

    userId : string ='';
    currentUser = new User();

    groups       = [];
    departments  = [];
    riskLines = [];
    partSelectedItem = {name : null};
    riskSelectedItem = {name : null, id : -1};
    modalShowText : string ='';

    ngOnInit() {

        this.userService.getUserSessionObservable.subscribe(
            result => {
                if (result && result.success) {
                    if(result.data.level==1){
                        this.css.isAdminUser = true;
                    }else{
                        this.css.isAdminUser = false;
                    }
                }
            },
            error => console.error(error)
        )


        this.sub = this.activatedRoute.params.subscribe(params => {
            this.currentUser.companyId = params['companyId'] || -1;
        });

        if (this.activatedRoute.routeConfig.path.indexOf('add') > -1) {
            this.css.isAddStatus  = true;
            this.getGroupList();
        } else {
            this.sub = this.activatedRoute.params.subscribe(params => {
                this.getUserInfo(params['id']);
            });
        }
        this.getDepartmentList();
        this.getRiskLineList();
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

    getRiskLineList() {
        this.riskService.getRiskSelectList().then((result)=> {
            if (result.success) {
                this.riskLines = result.data;
            }
        });
    }


    getDepartmentList() {
        this.userService.getDepartmentList().then((result)=> {
            if (result.success) {
                console.log(result.data);
                this.departments = result.data;
            } else {

            }
        });
    }

    getUserInfo(id) {

        this.userService.getUserById(id).then((result)=> {
            if (result.success) {
                this.currentUser = result.data;
                this.partSelectedItem = {name : this.currentUser.department};
                this.getGroupList();
            } else {

            }
        });
    }


    addUser() {
        this.css.isSubmitted     = true;
        this.css.ajaxErrorHidden     = true;
        this.css.ajaxSuccessHidden     = true;
        this.currentUser.department = this.partSelectedItem.name;
        this.currentUser.companyId = this.riskSelectedItem.id;

        if (this.css.isAddStatus) {
            this.userService.add(this.currentUser).then((result)=> {
                this.css.isSubmitted = false;
                if (result.success) {
                    this.css.ajaxSuccessHidden=false;
                    setTimeout(() => this.css.ajaxSuccessHidden = true, 5000);
                    this.clear();
                } else {
                    this.css.ajaxErrorHidden = false;
                    this.errorMsg = result.error.message;
                }
            });
        } else {
            this.userService.update(this.currentUser).then((result)=> {
                this.css.isSubmitted     = false;
                if (result.success) {
                    this.css.ajaxSuccessHidden=false;
                    this.back();
                } else {
                    this.css.ajaxErrorHidden = false;
                    this.errorMsg = result.error.message;
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
                    this.modalShowText = '重置密码成功!';
                } else {
                    this.modalShowText = result.error.message;
                }
                this.css.isHiddenMsgModal = false;
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
        this.partSelectedItem     = {name : null};
        this.css.formActiveForRefresh = false;
        setTimeout(() => this.css.formActiveForRefresh = true, 0);
    }

    back(){
        this.location.back();
    }


}

