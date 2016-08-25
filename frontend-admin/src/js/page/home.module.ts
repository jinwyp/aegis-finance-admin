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

import { routing, homePageRouterProviders } from './home.routes';
import { UserService, UserGroupService } from '../service/user';
import { TaskService } from '../service/task';


import { headerComponent } from '../components/header/header';
import { LeftMenuComponent } from '../components/left-menu/left-menu';

import { HomeComponent } from '../components/home/home-index';
import { HomeDashboardComponent } from '../components/home/home-dashboard';

import { OrderListComponent } from '../components/order/order-list';
import { OrderDetailComponent } from '../components/order/order-detail';

import { UserDetailComponent } from '../components/user/user-detail';
import { UserUpdatePasswordComponent } from '../components/user/user-update-password';

import { RoleListComponent } from '../components/role/role-list';
import { RoleInfoComponent } from '../components/role/role-info';
import { AddRoleComponent } from '../components/role/add-role';
import { UserListComponent } from '../components/role/user-list';
import { AddUserComponent } from '../components/role/add-user';
import { UserInfoComponent } from '../components/role/user-info';


import { PendingListComponent } from '../components/task/pending-list';
import { AssignPersonComponent } from '../components/task/assign-person';
import { ProcessTabComponent } from '../components/task/common/process-tab';
import { DistributionPersonComponent } from '../components/task/common/distribution-person';
import { FinanceApplyComponent } from '../components/task/finance-apply';
import { BusinessApprovalComponent } from '../components/task/business-approval';
import { TuneReportComponent } from '../components/task/tune-report';
import { SuperviseReportComponent } from '../components/task/supervise-report';
import { RiskControlReportComponent } from '../components/task/risk-control-report';
import { CustomSelectComponent } from '../components/task/common/custom-select';


import {AlertComponent,DROPDOWN_DIRECTIVES,TOOLTIP_DIRECTIVES} from 'ng2-bootstrap/ng2-bootstrap';

@NgModule({
    imports: [ BrowserModule, FormsModule, HttpModule, routing],
    declarations: [
        AlertComponent,DROPDOWN_DIRECTIVES,TOOLTIP_DIRECTIVES,
        headerComponent, LeftMenuComponent,
        HomeComponent, HomeDashboardComponent,
        UserDetailComponent, UserUpdatePasswordComponent,
        RoleListComponent, AddRoleComponent, RoleInfoComponent,
        UserListComponent, AddUserComponent, UserInfoComponent,
        ProcessTabComponent, DistributionPersonComponent,
        PendingListComponent, AssignPersonComponent, FinanceApplyComponent, BusinessApprovalComponent,
        TuneReportComponent, SuperviseReportComponent, RiskControlReportComponent, CustomSelectComponent
    ],
    providers: [ homePageRouterProviders, TaskService, UserService, UserGroupService ],
    bootstrap: [ HomeComponent ]
})
export class HomeModule { }


