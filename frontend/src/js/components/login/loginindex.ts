import { Component } from '@angular/core';
import { User } from '../../model/user';

declare var __moduleName: string;

@Component({
    selector: 'page-login',
    moduleId: __moduleName || module.id,
    templateUrl: 'loginindex.html'
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

