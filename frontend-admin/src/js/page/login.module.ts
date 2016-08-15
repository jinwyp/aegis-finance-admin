/**
 * Created by JinWYP on 8/11/16.
 */

import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule }   from '@angular/forms';
import { HttpModule }     from '@angular/http';

import { XHRBackend } from '@angular/http';
import { InMemoryBackendService, SEED_DATA } from 'angular2-in-memory-web-api';
import { InMemoryDataServiceUser }               from '../mock/api/in-memory-data.service';


import { LoginComponent } from '../components/login/login-index';
import { User, UserService } from '../service/user';



@NgModule({
    imports: [ BrowserModule, FormsModule, HttpModule],
    declarations: [ LoginComponent ],
    providers: [ UserService,
        { provide: XHRBackend, useClass: InMemoryBackendService }, // in-mem server
        { provide: SEED_DATA,  useClass: InMemoryDataServiceUser }     // in-mem server data
    ],
    bootstrap: [ LoginComponent ]
})
export class LoginModule { }
