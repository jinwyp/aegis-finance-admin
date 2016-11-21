/**
 * Created by JinWYP on 8/8/16.
 */

import { Routes, RouterModule }   from '@angular/router';


const routes: Routes = [
    {
        path: '',
        redirectTo: '/tasks/pending',
        pathMatch: 'full'
    },
    {
        path: '**',
        redirectTo: '/tasks/pending'
    }
];


export const cangyaPageRouterProviders : any[] = [

];

export const routing = RouterModule.forRoot(routes);