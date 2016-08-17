/**
 * Created by JinWYP on 8/8/16.
 */

import { provideRouter, RouterConfig }  from '@angular/router';
import { OrderListComponent } from '../components/order/order-list';
import { HomeDashboardComponent } from '../components/home/home-dashboard';
import { OrderDetailComponent } from '../components/order/order-detail';
import { UserInfoComponent } from '../components/user/user-info';
import { UpdatePwdComponent } from '../components/user/update-pwd';
import { RoleListComponent } from '../components/role/role-list';
import { RoleInfoComponent } from '../components/role/role-info';
import { AddRoleComponent } from '../components/role/add-role';
import { UserListComponent } from '../components/role/user-list';
import { AddUserComponent } from '../components/role/add-user';

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
    }
];


export const homePageRouterProviders = [
    provideRouter(routes)
];

