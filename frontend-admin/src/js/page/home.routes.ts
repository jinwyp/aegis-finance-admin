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
import { FinanceApplyComponent } from '../components/task/finance-apply';
import { BusinessApprovalComponent } from '../components/task/business-approval';
import { TuneReportComponent } from '../components/task/tune-report';
import { SuperviseReportComponent } from '../components/task/supervise-report';
import { RiskControlReportComponent } from '../components/task/risk-control-report';

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
        path: 'financeapply',
        component: FinanceApplyComponent
    },
    {
        path: 'businessapproval',
        component: BusinessApprovalComponent
    },
    {
        path: 'tunereport',
        component: TuneReportComponent
    },
    {
        path: 'supervisereport',
        component: SuperviseReportComponent
    },
    {
        path: 'riskcontrolreport',
        component: RiskControlReportComponent
    },

    {
        path: '**',
        redirectTo: '/tasks/pending'
    }
];


export const homePageRouterProviders : any[] = [

];

export const routing = RouterModule.forRoot(routes);