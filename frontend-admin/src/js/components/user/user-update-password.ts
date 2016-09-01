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
        ajaxSuccessHidden : true,
        comfirmPwdError : true
    };

    user = {
        oldPassword : '',
        newPassword : '',
        confirmPassword : ''
    };


    checkPwd(){
        console.log(this.user);
        if(this.user.confirmPassword===this.user.newPassword){
            this.css.comfirmPwdError = true;
        }else{
            this.css.comfirmPwdError = false;
        }
    }

    changePwd() {
        this.css.ajaxErrorHidden = true;
        this.css.ajaxSuccessHidden = true;
        this.css.isSubmitted     = true;
        this.userService.updatePassword(this.user).then((result)=> {
            if (result.success) {
                this.css.ajaxSuccessHidden = false;
                setTimeout(() => this.css.ajaxSuccessHidden = true, 3000);
            } else {
                this.css.ajaxErrorHidden = false;
            }
            this.css.isSubmitted = false;
        });
    }

}

