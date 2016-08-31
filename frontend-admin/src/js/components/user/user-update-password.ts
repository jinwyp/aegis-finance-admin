/**
 * Created by liushengbin on 16/8/15.
 */


import { Component } from '@angular/core';
import { UserService } from '../../service/user';


declare var __moduleName: string;

@Component({
    selector: 'user-password',
    moduleId: __moduleName || module.id,
    templateUrl: 'user-update-password.html'
})
export class UserUpdatePasswordComponent {

    constructor(
        private userService: UserService
    ) {}

    css = {
        activeForRefresh : true,
        isSubmitted :      false,
        ajaxErrorHidden :  true,
        ajaxSuccessHidden : true
    };

    user = {
        oldPassword : '',
        newPassword : '',
        confirmPassword : ''
    };


    checkPwd(){
        // this.oldPassword===this.newPassword;
    }

    changePwd() {
        this.css.ajaxErrorHidden = true;
        this.css.ajaxSuccessHidden = true;
        this.css.isSubmitted     = true;

        this.userService.updatePassword(this.user).then((result)=> {
            if (result.success) {
                this.css.ajaxSuccessHidden = false;
            } else {
                this.css.ajaxErrorHidden = false;
            }
            this.css.isSubmitted = false;
        });
    }

}

