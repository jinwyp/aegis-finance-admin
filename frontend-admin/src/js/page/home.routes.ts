/**
 * Created by JinWYP on 8/8/16.
 */

import { provideRouter, RouterConfig }  from '@angular/router';
import { OrderListComponent } from '../components/order/order-list';
import { HomeDashboardComponent } from '../components/home/home-dashboard';
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
import { FinanceApplyComponent } from '../components/audit/finance-apply';
import { BusinessApprovalComponent } from '../components/audit/business-approval';
import { TuneReportComponent } from '../components/audit/tune-report';

const routes: RouterConfig = [
    {
        path: '',
        redirectTo: '/dashboard',
        pathMatch: 'full'
    },
    {
        path: 'dashboard',
        component: HomeDashboardComponent
    },
    {
        path: 'orders',
        component: OrderListComponent
    },
    {
        path: 'orders/:id',
        component: OrderDetailComponent
    },
    {
        path: 'userdetail',
        component: UserDetailComponent
    },
    {
        path: 'updatepwd',
        component: UpdatePwdComponent
    },
    {
        path: 'rolelist',
        component: RoleListComponent
    },
    {
        path: 'userrole',
        component: RoleInfoComponent
    },
    {
        path: 'addrole',
        component: AddRoleComponent
    },
    {
        path: 'editrole',
        component: AddRoleComponent
    },
    {
        path: 'userlist',
        component: UserListComponent
    },
    {
        path: 'adduser',
        component: AddUserComponent
    },
    {
        path: 'edituser',
        component: AddUserComponent
    },
    {
        path: 'userinfo',
        component: UserInfoComponent
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
    }
];


export const homePageRouterProviders = [
    provideRouter(routes)
];

