/**
 * Created by liushengbin on 16/8/15.
 */


import { Component, OnInit, OnDestroy} from '@angular/core';
import { Router, ActivatedRoute }      from '@angular/router';
import { Subscription } from 'rxjs/Subscription';

import { User, UserService, UserGroup, UserGroupService } from '../../service/user';


declare var __moduleName: string;

@Component({
    selector: 'role-info',
    moduleId: __moduleName || module.id,
    templateUrl: 'role-info.html'
})



export class RoleInfoComponent implements OnInit, OnDestroy{

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private group: UserGroupService
    ) {}

    private sub: Subscription;

    currentGroup : UserGroup = new UserGroup();


    ngOnInit() {
        this.sub = this.route.params.subscribe(params => {
            this.getGroupInfo(params['id']);
        });
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }

    getGroupInfo(id) {

        this.group.getGroupById(id).then((result)=>{
            if (result.success){
                this.currentGroup = result.data;
            }else{

            }
        });

        this.group.getUserListByGroupId(id).then((result)=>{
            if (result.success){
                this.currentGroup.userList = result.data;
            }else{

            }
        });
    }

    linkToEdit() {
        this.router.navigate(['/userroles', this.currentGroup.id, 'edit']);
    }

}

