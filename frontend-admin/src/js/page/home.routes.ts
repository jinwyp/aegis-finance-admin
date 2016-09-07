/**
 * Created by JinWYP on 8/8/16.
 */

import { Routes, RouterModule }   from '@angular/router';

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
import { AuditTraderComponent, } from '../components/task/audit-trader';
import { AuditSalesmanComponent } from '../components/task/audit-salesman';
import { AuditInvestigatorComponent } from '../components/task/audit-investigator';
import { AuditSupervisorComponent } from '../components/task/audit-supervisor';
import { AuditRiskManagerComponent } from '../components/task/audit-riskmanager';

const routes: Routes = [
    {
        path: '',
        redirectTo: '/tasks/pending',
        pathMatch: 'full'
    },

    {
        path: 'user/detail',
        component: UserDetailComponent
    },
    {
        path: 'user/password',
        component: UserUpdatePasswordComponent
    },

    {
        path: 'userroles',
        component: RoleListComponent,
        data: { routeType : 'list', title: 'User Role List' }
    },
    {
        path: 'userroles/add',
        component: AddRoleComponent,
        data: { routeType : 'add', title: 'User Role Add' }
    },
    {
        path: 'userroles/:id',
        component: RoleInfoComponent,
        data: { routeType : 'info', title: 'User Role Info'}
    },
    {
        path: 'userroles/:id/edit',
        component: AddRoleComponent,
        data: { routeType : 'update',  title: 'User Role Edit'}
    },

    {
        path: 'users',
        component: UserListComponent,
        data: { routeType : 'list', title: 'User List' }
    },
    {
        path: 'users/add',
        component: AddUserComponent,
        data: { routeType : 'add', title: 'User Add' }
    },
    {
        path: 'users/:id',
        component: UserInfoComponent,
        data: { routeType : 'list', title: 'User Info'}
    },
    {
        path: 'users/:id/edit',
        component: AddUserComponent,
        data: { routeType : 'edit', title: 'User Edit' }
    },


    {
        path: 'tasks/pending',
        component: TaskListComponent,
        data: { routeType : 'pending', title: 'User Pending Tasks' }
    },

    {
        path: 'tasks/all',
        component: TaskListComponent,
        data: {routeType : 'all', title: 'User All Tasks' }
    },

    {
        path: 'tasks/:id/assign',
        component: AssignPersonComponent
    },


    {
        path: 'tasks/:id/trader',
        component: AuditTraderComponent,
    },
    {
        path: 'tasks/:id/salesman',
        component: AuditSalesmanComponent
    },
    {
        path: 'tasks/:id/investigator',
        component: AuditInvestigatorComponent
    },
    {
        path: 'tasks/:id/supervisor',
        component: AuditSupervisorComponent
    },
    {
        path: 'tasks/:id/riskmanager',
        component: AuditRiskManagerComponent
    },


    {
        path: 'tasks/:id/investigator/material',
        component: AuditInvestigatorComponent
    },

    {
        path: '**',
        redirectTo: '/tasks/pending'
    }
];


export const homePageRouterProviders : any[] = [

];

export const routing = RouterModule.forRoot(routes);