/**
 * Created by JinWYP on 8/8/16.
 */
import { Component } from '@angular/core';

import { User, UserService } from '../../service/user';

declare var __moduleName: string;


@Component({
    selector: 'header',
    moduleId: module.id,
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

        this.user.getUserSessionObservable.subscribe(
            result => {
                if (result && result.success) {
                    this.currentUserSession = result.data;
                } else {

                }
            },
            error => console.error(error)
        )
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