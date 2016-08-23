/**
 * Created by liushengbin on 16/8/15.
 */


import { Component } from '@angular/core';
import { User, UserService } from '../../service/user';


declare var __moduleName: string;

@Component({
    selector: 'add-user',
    moduleId: __moduleName || module.id,
    templateUrl: 'add-user.html'
})



export class AddUserComponent {

    constructor(
        private userService: UserService
    ) {}

    currentUser = {
        "id": 1,
        "username": "liushengbin",
        "name": "liushengbin",
        "phone": "18621266707",
        "email": "liushengbin@yimei180.com",
        "department": "技术部",
        "password": "string",
        "groupIds":["10001","10005"]
    };

    groups=[
        { "id": "10001", "name": "系统管理员", "type": "null" },
        { "id": "10002", "name": "客服人员", "type": "null" },
        { "id": "10003", "name": "交易人员", "type": "null" },
        { "id": "10004", "name": "监管人员", "type": "null" },
        { "id": "10005", "name": "尽调人员", "type": "null" },
        { "id": "10006", "name": "风控人员", "type": "null" }
    ]

    css = {
        activeForRefresh : true,
        isSubmitted : false,
        ajaxErrorHidden : true
    };

    addUser(form) {
        this.css.ajaxErrorHidden = true;
        this.css.isSubmitted = true;
        // this.userService.login({
        //     username:this.currentUser.username,
        //     password:this.currentUser.password
        // }).then((result)=>{
        //     if (result.success){
        //         alert('登录成功');
        //         window.location.href = '/finance/admin/home';
        //     }else{
        //         this.css.ajaxErrorHidden = false;
        //     }
        //     console.log(result)
        // });
    }

}

