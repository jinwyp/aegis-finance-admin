/**
 * Created by JinWYP on 8/11/16.
 */

import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule }   from '@angular/forms';
import { HttpModule }     from '@angular/http';

import { XHRBackend } from '@angular/http';
import { InMemoryBackendService, SEED_DATA } from 'angular2-in-memory-web-api';
import { InMemoryDataServiceOrder }          from '../mock/api/in-memory-data.service';


import { HomeComponent } from '../components/home/home-index';
import { Order, OrderService } from '../service/order';
import { homePageRouterProviders } from './home.routes';


@NgModule({
    imports: [ BrowserModule, FormsModule, HttpModule],
    declarations: [ HomeComponent ],
    providers: [ OrderService,
        homePageRouterProviders,
        { provide: XHRBackend, useClass: InMemoryBackendService }, // in-mem server
        { provide: SEED_DATA,  useClass: InMemoryDataServiceOrder }     // in-mem server data
    ],
    bootstrap: [ HomeComponent ]
})
export class HomeModule { }


