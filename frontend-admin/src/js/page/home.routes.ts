/**
 * Created by JinWYP on 8/8/16.
 */

import { provideRouter, RouterConfig }  from '@angular/router';
import { OrderListComponent } from '../components/order/order-list';
import { HomeDashboardComponent } from '../components/home/home-dashboard';
import { OrderDetailComponent } from '../components/order/order-detail';
import { UserInfoComponent } from '../components/user/user-info';
import { UpdatePwdComponent } from '../components/user/update-pwd';
import { UserRoleComponent } from '../components/role/user-role';
import { AddRoleComponent } from '../components/role/add-role';
import { UserRoleListComponent } from '../components/role/user-role-list';

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
        path: 'userinfo',
        component: UserInfoComponent
    },
    {
        path: 'updatepwd',
        component: UpdatePwdComponent
    },
    {
        path: 'userrole',
        component: UserRoleComponent
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
        path: 'rolelist',
        component: UserRoleListComponent
    }
];


export const homePageRouterProviders = [
    provideRouter(routes)
];

