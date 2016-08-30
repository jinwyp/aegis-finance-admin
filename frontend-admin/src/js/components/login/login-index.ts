import { Component } from '@angular/core';

import { User, UserLoginService } from '../../service/user';

declare var __moduleName: string;


@Component({
    selector    : 'page-login',
    moduleId    : __moduleName || module.id,
    templateUrl : 'login-index.html'
})
export class LoginComponent {

    constructor(
        private user: UserLoginService
    ) {}

    css = {
        activeForRefresh : true,
        ajaxErrorHidden : true
    };


    currentUser: User = new User();


    ngOnInit() {
    }


    login(form) {
        this.css.ajaxErrorHidden = true;
        this.user.login({
            username:this.currentUser.username,
            password:this.currentUser.password
        }).then((result)=>{
            if (result.success){
                window.location.href = '/finance/admin/home';
            }else{
                this.css.ajaxErrorHidden = false;
            }
        }).catch((error)=>{
            if (!error.success){
                this.css.ajaxErrorHidden = false;
            }
        });
    }


    clearInput() {

        this.currentUser = new User();
        this.css.activeForRefresh = false;
        setTimeout(() => this.css.activeForRefresh = true, 0);
    }

    get diagnostic() { return JSON.stringify(this.currentUser); }
}
