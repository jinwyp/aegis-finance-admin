/**
 * Created by liushengbin on 16/8/15.
 */


import { Component } from '@angular/core';


declare var __moduleName: string;

@Component({
    selector: 'user-info',
    moduleId: __moduleName || module.id,
    templateUrl: 'user-info.html'
})



export class UserInfoComponent {

    title = '查看用户';

}

