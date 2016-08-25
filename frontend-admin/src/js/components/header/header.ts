/**
 * Created by JinWYP on 8/8/16.
 */
import { Component } from '@angular/core';

import { User, UserService } from '../../service/user';

declare var __moduleName: string;


@Component({
    selector: 'header',
    moduleId: __moduleName || module.id,
    templateUrl: 'header.html'
})
export class headerComponent {

    constructor(
        private user: UserService
    ) {}

    currentUserSession : User = new User();

    ngOnInit() {
        this.getCurrentUser();
    }

    getCurrentUser() {

        this.user.getCurrentUser().then((result)=>{
            if (result.success){
                this.currentUserSession = result.data;
            }else{

            }
        });
    }

    logout(event) {
        event.preventDefault();
        event.stopPropagation();
        this.user.logout().then((result)=>{
            if (result.success){
                window.location.href = '/finance/admin/login';
            }else{

            }
        });
    }

}