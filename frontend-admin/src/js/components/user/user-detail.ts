/**
 * Created by liushengbin on 16/8/15.
 */


import { Component } from '@angular/core';
import { User, UserService } from '../../service/user';


declare var __moduleName: string;

@Component({
    selector: 'user-detail',
    moduleId: module.id,
    templateUrl: 'user-detail.html'
})
export class UserDetailComponent {

    constructor(
        private userService: UserService
    ) {}

    css = {
        ajaxSuccessHidden : true,
        ajaxErrorHidden : true,
        isSubmitted :      false,
        activeForRefresh : true
    };

    errorMsg = '';
    currentUserSession : User = new User();
    departments  = [];
    selectedItem = '';

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
        this.css.isSubmitted     = true;
        this.css.ajaxSuccessHidden = true;
        this.css.ajaxErrorHidden = true;
        this.currentUserSession.department = this.selectedItem;
        this.userService.updateCurrentUserInfo(this.currentUserSession).then((result)=> {
            if (result.success) {
                this.css.ajaxSuccessHidden = false;
                setTimeout(() => this.css.ajaxSuccessHidden = true, 3000);
            } else {
                this.css.ajaxErrorHidden = false;
                this.errorMsg = result.error.message;
            }
            this.css.isSubmitted = false;
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

