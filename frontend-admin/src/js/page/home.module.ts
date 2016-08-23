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

import { UserDetailComponent } from '../components/user/user-detail';
import { UpdatePwdComponent } from '../components/user/update-pwd';

import { RoleListComponent } from '../components/role/role-list';
import { RoleInfoComponent } from '../components/role/role-info';
import { AddRoleComponent } from '../components/role/add-role';
import { UserListComponent } from '../components/role/user-list';
import { AddUserComponent } from '../components/role/add-user';
import { UserInfoComponent } from '../components/role/user-info';
import { WaitDealListComponent } from '../components/audit/wait-deal-list';
import { DistributionPageComponent } from '../components/audit/distribution-page';
import { ProcessTabComponent } from '../components/audit/common/process-tab';
import { DistributionPersonComponent } from '../components/audit/common/distribution-person';
import { FinanceApplyComponent } from '../components/audit/finance-apply';
import { BusinessApprovalComponent } from '../components/audit/business-approval';
import { TuneReportComponent } from '../components/audit/tune-report';
import { SuperviseReportComponent } from '../components/audit/supervise-report';
import { RiskControlReportComponent } from '../components/audit/risk-control-report';


import {AlertComponent,DROPDOWN_DIRECTIVES,TOOLTIP_DIRECTIVES} from 'ng2-bootstrap/ng2-bootstrap';

@NgModule({
    imports: [ BrowserModule, FormsModule, HttpModule],
    declarations: [
        ROUTER_DIRECTIVES,
        AlertComponent,DROPDOWN_DIRECTIVES,TOOLTIP_DIRECTIVES,
        headerComponent, LeftMenuComponent,
        HomeComponent, HomeDashboardComponent, OrderListComponent, OrderDetailComponent,
        UserDetailComponent, UpdatePwdComponent,
        RoleListComponent, AddRoleComponent, RoleInfoComponent,
        UserListComponent, AddUserComponent, UserInfoComponent,
        ProcessTabComponent, DistributionPersonComponent,
        WaitDealListComponent, DistributionPageComponent, FinanceApplyComponent, BusinessApprovalComponent,
        TuneReportComponent,SuperviseReportComponent,RiskControlReportComponent
    ],
    providers: [ OrderService,
        homePageRouterProviders,
        { provide: XHRBackend, useClass: InMemoryBackendService }, // in-mem server
        { provide: SEED_DATA,  useClass: InMemoryDataServiceOrder }     // in-mem server data
    ],
    bootstrap: [ HomeComponent ]
})
export class HomeModule { }


