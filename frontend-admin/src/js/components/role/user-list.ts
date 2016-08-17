/**
 * Created by liushengbin on 16/8/15.
 */


import { Component } from '@angular/core';


declare var __moduleName: string;

@Component({
    selector: 'user-list',
    moduleId: __moduleName || module.id,
    templateUrl: 'user-list.html'
})



export class UserListComponent {

    title = '用户管理';

}

