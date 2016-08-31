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
        isHiddenSaveText : true,
        isSubmitted :      false,
        activeForRefresh : true
    };

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
                    console.log(result);
                } else {

                }
            },
            error => console.error(error)
        )
    }

    saveUser() {
        this.css.isSubmitted     = true;

        this.currentUserSession.department = this.selectedItem;
        this.userService.edit(this.currentUserSession).then((result)=> {
            if (result.success) {
                this.css.isHiddenSaveText = false;
                setTimeout(() => this.css.isHiddenSaveText = true, 3000);
            } else {

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

