import { Component } from '@angular/core';

@Component({
    selector: 'page-login',
    template: '<h1>管理登录</h1> <h1>{{title}}</h1> <h2>{{hero}} details!</h2>'
})



export class LoginComponent {

    title = 'Tour of Heroes';
    hero = 'Windstorm';

}

