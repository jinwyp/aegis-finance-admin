import { Component } from '@angular/core';

import { User, UserService } from '../../service/user';

declare var __moduleName: string;


@Component({
    selector    : 'page-login',
    moduleId    : __moduleName || module.id,
    templateUrl : 'login-index.html',
    directives  : []
})



export class LoginComponent {

    constructor(
        private userHttp: UserService
    ) {}

    active = true;
    submitted = false;
    currentUser: User = new User();

    powers = ['Really Smart', 'Super Flexible', 'Super Hot', 'Weather Changer'];

    ngOnInit() {
    }


    login() {
        this.submitted = true;
        // this.orderService.getOrderList().then(heroes => this.heroes = heroes);
    }

    showInfoAfterlogin() {
        this.submitted = false;
    }

    clearInput() {

        this.currentUser = new User();
        this.active = false;
        setTimeout(() => this.active = true, 0);
    }

    get diagnostic() { return JSON.stringify(this.currentUser); }
}
