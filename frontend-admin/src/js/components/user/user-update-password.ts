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
    user={
        oldPassword : '',
        newPassword : ''
    };
    comfirmPassword='';

    checkPwd(){
        // this.oldPassword===this.newPassword;
    }

    changePwd() {
        console.log(this.user);
        this.userService.changePwd(this.user).then((result)=> {
            if (result.success) {
                window.location.href = '/finance/admin/home/users';
            } else {

            }
            console.log(result);
        });
    }

}

