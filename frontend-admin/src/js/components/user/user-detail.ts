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

    ngOnInit() {
        this.getCurrentUser();
    }

    getCurrentUser() {

        this.userService.getUserSessionObservable.subscribe(
            result => {
                if (result && result.success) {
                    this.currentUserSession = result.data;
                    console.log(this.currentUserSession)
                } else {

                }
            },
            error => console.error(error)
        )
    }

    saveUser() {
        this.css.ajaxErrorHidden = true;
        this.css.isSubmitted     = true;

        this.userService.save(this.currentUserSession).then((result)=> {
            if (result.success) {
                window.location.href = '/finance/admin/home/users';
            } else {
                this.css.ajaxErrorHidden = false;
            }
            console.log(result)
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

}

