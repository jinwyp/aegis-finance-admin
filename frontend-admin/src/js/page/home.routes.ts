/**
 * Created by JinWYP on 8/8/16.
 */

import { provideRouter, RouterConfig }  from '@angular/router';
import { OrderListComponent } from '../components/order/order-list';
import { HomeDashboardComponent } from '../components/home/home-dashboard';
import { OrderDetailComponent } from '../components/order/order-detail';

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
    }
];


export const homePageRouterProviders = [
    provideRouter(routes)
];

