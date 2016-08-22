import { Component } from '@angular/core';

import { User, UserService } from '../../service/user';

declare var __moduleName: string;


@Component({
    selector    : 'page-login',
    moduleId    : __moduleName || module.id,
    templateUrl : 'login-index.html'
})
export class LoginComponent {

    constructor(
        private user: UserService
    ) {}

    css = {
        activeForRefresh : true,
        isSubmitted : false,
        ajaxErrorHidden : true
    };


    currentUser: User = new User();


    ngOnInit() {
    }


    login(form) {
        this.css.ajaxErrorHidden = true;
        this.css.isSubmitted = true;
        this.user.login({
            email:this.currentUser.username,
            password:this.currentUser.password
        }).then((result)=>{
            if (result.success){
                alert('登录成功');
                window.location.href = '/finance/admin/home';
            }else{
                this.css.ajaxErrorHidden = false;
            }
            console.log(result)
        });
    }

    showInfoAfterLogin() {
        this.css.isSubmitted = false;
    }

    clearInput() {

        this.currentUser = new User();
        this.css.activeForRefresh = false;
        setTimeout(() => this.css.activeForRefresh = true, 0);
    }

    get diagnostic() { return JSON.stringify(this.currentUser); }
}
