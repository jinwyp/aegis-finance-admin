import { Component } from '@angular/core';
import { User } from '../../model/user';

@Component({
    selector: 'page-login',
    template: '<h1>管理登录</h1> <h1>{{title}}</h1> <h2>My favorite hero is: {{myHero.name}}</h2>  <p *ngIf="heroes.length > 3">There are many heroes!</p>' +
                ' <ul> <li *ngFor="let hero of heroes">  {{ hero.username }} </li> </ul> ' +
        '<button (click)="onClickMe()">Click me!</button> {{clickMessage}}' +
        '<input #box (keyup)="onKey(box.value)"> <p>{{values}}</p>'
})



export class LoginComponent {

    title = 'Tour of Heroes';
    heroes = [
        new User(1, 'Windstorm'),
        new User(13, 'Bombasto'),
        new User(15, 'Magneta'),
        new User(20, 'Tornado')
    ];
    myHero = this.heroes[0];

    clickMessage = '';

    onClickMe() {
        this.clickMessage = 'You are my hero!';
    }

    values = '';
    onKey(value: string) {
        this.values += value + ' | ';
    }
}

