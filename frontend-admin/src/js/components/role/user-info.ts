/**
 * Created by liushengbin on 16/8/15.
 */

import { Component, OnInit, OnDestroy} from '@angular/core';
import { ActivatedRoute }      from '@angular/router';
import { Subscription } from 'rxjs/Subscription';

import { User, UserService, UserGroup, UserGroupService } from '../../service/user';


declare var __moduleName: string;

@Component({
    selector: 'user-info',
    moduleId: __moduleName || module.id,
    templateUrl: 'user-info.html'
})



export class UserInfoComponent implements OnInit, OnDestroy{

    constructor(
        private route: ActivatedRoute,
        private user: UserService,
        private group: UserGroupService
    ) {}

    private sub: Subscription;

    currentUser : User = new User();


    ngOnInit() {
        this.sub = this.route.params.subscribe(params => {
            this.getUserInfo(params['id']);
        });
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }

    getUserInfo(id) {

        this.user.getUserById(id).then((result)=>{
            if (result.success){
                this.currentUser = result.data;
            }else{

            }
        });
    }

}

