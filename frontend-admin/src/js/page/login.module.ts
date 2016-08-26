/**
 * Created by JinWYP on 8/11/16.
 */

import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule }   from '@angular/forms';
import { HttpModule }     from '@angular/http';




import { LoginComponent } from '../components/login/login-index';
import { UserLoginService } from '../service/user';



@NgModule({
    imports: [ BrowserModule, FormsModule, HttpModule],
    declarations: [ LoginComponent ],
    providers: [ UserLoginService ],
    bootstrap: [ LoginComponent ]
})
export class LoginModule { }
