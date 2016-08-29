/**
 * Created by liushengbin on 16/8/15.
 */


import { Component } from '@angular/core';
import { User, UserService } from '../../service/user';


declare var __moduleName: string;

@Component({
    selector: 'user-detail',
    moduleId: __moduleName || module.id,
    templateUrl: 'user-detail.html'
})
export class UserDetailComponent {

    constructor(
        private userService: UserService
    ) {}

    css = {
        activeForRefresh : true,
        isSubmitted :      false,
        ajaxErrorHidden :  true
    };

    currentUserSession : User = new User();
    departments  = [];
    selectedItem = '请选择';

    ngOnInit() {
        this.getCurrentUser();
        this.getDepartmentList();
    }

    getCurrentUser() {

        this.userService.getUserSessionObservable.subscribe(
            result => {
                if (result && result.success) {
                    this.currentUserSession = result.data;
                    this.selectedItem = this.currentUserSession.department;
                } else {

                }
            },
            error => console.error(error)
        )
    }

    saveUser() {
        this.css.ajaxErrorHidden = true;
        this.css.isSubmitted     = true;

        this.currentUserSession.department = this.selectedItem;

        this.userService.save(this.currentUserSession).then((result)=> {
            if (result.success) {
                window.location.href = '/finance/admin/home/user/detail';
            } else {
                this.css.ajaxErrorHidden = false;
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


}

