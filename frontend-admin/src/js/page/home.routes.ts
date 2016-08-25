/**
 * Created by JinWYP on 8/8/16.
 */

import { Routes, RouterModule }   from '@angular/router';
import { OrderListComponent } from '../components/order/order-list';
import { HomeDashboardComponent } from '../components/home/home-dashboard';
import { OrderDetailComponent } from '../components/order/order-detail';
import { UserDetailComponent } from '../components/user/user-detail';
import { UserUpdatePasswordComponent } from '../components/user/user-update-password';
import { RoleListComponent } from '../components/role/role-list';
import { RoleInfoComponent } from '../components/role/role-info';
import { AddRoleComponent } from '../components/role/add-role';
import { UserListComponent } from '../components/role/user-list';
import { AddUserComponent } from '../components/role/add-user';
import { UserInfoComponent } from '../components/role/user-info';
import { WaitDealListComponent } from '../components/audit/wait-deal-list';
import { DistributionPageComponent } from '../components/audit/distribution-page';
import { FinanceApplyComponent } from '../components/audit/finance-apply';
import { BusinessApprovalComponent } from '../components/audit/business-approval';
import { TuneReportComponent } from '../components/audit/tune-report';
import { SuperviseReportComponent } from '../components/audit/supervise-report';
import { RiskControlReportComponent } from '../components/audit/risk-control-report';

const routes: Routes = [
    {
        path: '',
        redirectTo: '/userroles',
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
        data: {type : 'list', title: 'User Role List' }
    },
    {
        path: 'userroles/add',
        component: AddRoleComponent,
        data: {type : 'add', title: 'User Role Add' }
    },
    {
        path: 'userroles/:id',
        component: RoleInfoComponent,
        data: { type : 'info', title: 'User Role Info'}
    },
    {
        path: 'userroles/:id/edit',
        component: AddRoleComponent,
        data: {type : 'update',  title: 'User Role Edit'}
    },

    {
        path: 'users',
        component: UserListComponent
    },
    {
        path: 'users/add',
        component: AddUserComponent,
        data: {type : 'add', title: 'User Add' }
    },
    {
        path: 'users/:id',
        component: UserInfoComponent
    },
    {
        path: 'users/:id/edit',
        component: AddUserComponent,
        data: {type : 'edit', title: 'User Edit' }
    },

    {
        path: 'waitdeallist',
        component: WaitDealListComponent
    },
    {
        path: 'distributionpage',
        component: DistributionPageComponent
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
        redirectTo: '/userroles'
    }
];


export const homePageRouterProviders : any[] = [

];

export const routing = RouterModule.forRoot(routes);