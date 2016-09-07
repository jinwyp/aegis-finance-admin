/**
 * Created by JinWYP on 8/11/16.
 */

import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule }   from '@angular/forms';
import { HttpModule }    from '@angular/http';


import { InMemoryBackendService, SEED_DATA } from 'angular2-in-memory-web-api';
import { InMemoryDataServiceOrder }          from '../mock/api/in-memory-data.service';

import { routing, homePageRouterProviders } from './home.routes';
import { UserService, UserGroupService } from '../service/user';
import { TaskService } from '../service/task';
import { FileUploadService } from '../service/file-upload';


import { FileUploadComponent } from '../components/aegis-ui/file-upload';
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


import { TaskListComponent } from '../components/task/task-list';
import { AssignPersonComponent } from '../components/task/assign-person';
import { ProcessTabComponent } from '../components/task/process-tab';
import { AuditTraderComponent } from '../components/task/audit-trader';
import { AuditSalesmanComponent } from '../components/task/audit-salesman';
import { AuditInvestigatorComponent } from '../components/task/audit-investigator';
import { AuditSupervisorComponent } from '../components/task/audit-supervisor';
import { AuditRiskManagerComponent } from '../components/task/audit-riskmanager';
import { CustomSelectComponent } from '../components/aegis-ui/custom-select';
import { CustomModalComponent } from '../components/aegis-ui/custom-modal';
import { CustomCheckboxComponent } from '../components/aegis-ui/custom-checkbox';



@NgModule({
    imports: [ BrowserModule, FormsModule, HttpModule, routing],
    declarations: [
        FileUploadComponent, headerComponent, LeftMenuComponent,
        HomeComponent, HomeDashboardComponent,
        UserDetailComponent, UserUpdatePasswordComponent,
        RoleListComponent, AddRoleComponent, RoleInfoComponent,
        UserListComponent, AddUserComponent, UserInfoComponent,
        TaskListComponent, AssignPersonComponent, ProcessTabComponent,
        AuditTraderComponent, AuditSalesmanComponent, AuditInvestigatorComponent, AuditSupervisorComponent, AuditRiskManagerComponent,
        CustomSelectComponent,CustomModalComponent, CustomCheckboxComponent
    ],
    providers: [ FileUploadService, homePageRouterProviders, TaskService, UserService, UserGroupService ],
    bootstrap: [ HomeComponent ]
})
export class HomeModule { }


