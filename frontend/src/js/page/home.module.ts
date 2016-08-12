/**
 * Created by JinWYP on 8/11/16.
 */

import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule }   from '@angular/forms';
import { HttpModule }     from '@angular/http';
import { ROUTER_DIRECTIVES } from '@angular/router';

import { XHRBackend } from '@angular/http';
import { InMemoryBackendService, SEED_DATA } from 'angular2-in-memory-web-api';
import { InMemoryDataServiceOrder }          from '../mock/api/in-memory-data.service';

import { homePageRouterProviders } from './home.routes';
import { Order, OrderService } from '../service/order';


import { headerComponent } from '../components/header/header';
import { LeftMenuComponent } from '../components/left-menu/left-menu';

import { HomeComponent } from '../components/home/home-index';
import { HomeDashboardComponent } from '../components/home/home-dashboard';
import { OrderListComponent } from '../components/order/order-list';
import { OrderDetailComponent } from '../components/order/order-detail';



@NgModule({
    imports: [ BrowserModule, FormsModule, HttpModule],
    declarations: [
        ROUTER_DIRECTIVES,
        headerComponent, LeftMenuComponent,
        HomeComponent, HomeDashboardComponent, OrderListComponent, OrderDetailComponent
    ],
    providers: [ OrderService,
        homePageRouterProviders,
        { provide: XHRBackend, useClass: InMemoryBackendService }, // in-mem server
        { provide: SEED_DATA,  useClass: InMemoryDataServiceOrder }     // in-mem server data
    ],
    bootstrap: [ HomeComponent ]
})
export class HomeModule { }


