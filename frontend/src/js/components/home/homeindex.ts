import { Component } from '@angular/core';

@Component({
    selector: 'page-admin',
    template: '<h1>My First Angular 2 App 管理员首页</h1> <h1>{{title}}</h1> <h2>{{hero}} details!</h2>'
})



export class AppComponent {

    title = 'Tour of Heroes';
    hero = 'Windstorm';

}

