/**
 * Created by JinWYP on 8/8/16.
 */
import { Component } from '@angular/core';

declare var __moduleName: string;


@Component({
    selector: 'left-menu',
    moduleId: __moduleName || module.id,
    templateUrl: 'left-menu.html'
})
export class LeftMenuComponent {

    css = {
        currentTab : 1,
        currentMenu : 1,
    };

    changeMenu = (menu)=>{
        this.css.currentMenu = menu;
        this.css.currentTab = Math.floor(menu/10);
    }
}