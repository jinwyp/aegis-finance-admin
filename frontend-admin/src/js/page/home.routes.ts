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
import { SuperviseReportComponent } from '../components/task/supervise-report';
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
        data: { routetype : 'list', title: 'User Role List' }
    },
    {
        path: 'userroles/add',
        component: AddRoleComponent,
        data: { routetype : 'add', title: 'User Role Add' }
    },
    {
        path: 'userroles/:id',
        component: RoleInfoComponent,
        data: { routetype : 'info', title: 'User Role Info'}
    },
    {
        path: 'userroles/:id/edit',
        component: AddRoleComponent,
        data: { routetype : 'update',  title: 'User Role Edit'}
    },

    {
        path: 'users',
        component: UserListComponent,
        data: { routetype : 'list', title: 'User List' }
    },
    {
        path: 'users/add',
        component: AddUserComponent,
        data: { routetype : 'add', title: 'User Add' }
    },
    {
        path: 'users/:id',
        component: UserInfoComponent,
        data: { routetype : 'list', title: 'User Info'}
    },
    {
        path: 'users/:id/edit',
        component: AddUserComponent,
        data: { routetype : 'edit', title: 'User Edit' }
    },


    {
        path: 'tasks/pending',
        component: TaskListComponent,
        data: { routetype : 'pending', title: 'User Pending Tasks' }
    },

    {
        path: 'tasks/all',
        component: TaskListComponent,
        data: {routetype : 'all', title: 'User All Tasks' }
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
        component: SuperviseReportComponent
    },
    {
        path: 'tasks/:id/riskmanager',
        component: AuditRiskManagerComponent
    },

    {
        path: '**',
        redirectTo: '/tasks/pending'
    }
];


export const homePageRouterProviders : any[] = [

];

export const routing = RouterModule.forRoot(routes);