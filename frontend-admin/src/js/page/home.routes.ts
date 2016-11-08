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
import { MaterialDetailComponent } from "../components/task/material-detail";
import { AddRiskLineComponent } from "../components/risk-line/add-risk-line";
import { FundCompanyListComponent } from "../components/role/fund-company-list";
import { RiskLineListComponent } from "../components/risk-line/risk-line-list";

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
        data: { routeType : 'list', title: '用户角色列表' }
    },
    {
        path: 'userroles/add',
        component: AddRoleComponent,
        data: { routeType : 'add', title: '用户角色 - 添加' }
    },
    {
        path: 'userroles/:id',
        component: RoleInfoComponent,
        data: { routeType : 'info', title: '用户角色 - 查看角色信息'}
    },
    {
        path: 'userroles/:id/edit',
        component: AddRoleComponent,
        data: { routeType : 'update',  title: '用户角色 - 编辑'}
    },

    {
        path: 'users',
        component: UserListComponent,
        data: { routeType : 'list', title: '用户列表' }
    },
    {
        path: 'fundcompanys',
        component: FundCompanyListComponent,
        data: { routeType : 'list', title: '资金方公司列表' }
    },
    {
        path: 'risklines',
        component: RiskLineListComponent,
        data: { routeType : 'list', title: '风控线列表' }
    },
    {
        path: 'users/add',
        component: AddUserComponent,
        data: { routeType : 'add', title: '用户 - 添加' }
    },
    {
        path: 'fundcompany/add',
        component: AddRiskLineComponent,
        data: { routeType : 2, title: '资金方公司 - 添加' }
    },
    {
        path: 'riskline/add',
        component: AddRiskLineComponent,
        data: { routeType : 1, title: '业务线 - 添加' }
    },
    {
        path: 'users/:id',
        component: UserInfoComponent,
        data: { routeType : 'list', title: '用户 - 查看信息'}
    },
    {
        path: 'users/:id/edit',
        component: AddUserComponent,
        data: { routeType : 'edit', title: '用户 - 编辑' }
    },
    {
        path: 'fundcompany/:id/edit',
        component: AddRiskLineComponent,
        data: { routeType : 2, title: '资金方公司 - 编辑' }
    },
    {
        path: 'riskline/:id/edit',
        component: AddRiskLineComponent,
        data: { routeType : 1, title: '业务闲 - 编辑' }
    },
    {
        path: 'tasks/pending',
        component: TaskListComponent,
        data: { routeType : 'pending', title: '待办任务' }
    },

    {
        path: 'tasks/all',
        component: TaskListComponent,
        data: {routeType : 'all', title: '全部任务' }
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
        component: MaterialDetailComponent
    },
    {
        path: 'tasks/:id/supervisor/material',
        component: MaterialDetailComponent
    },
    {
        path: 'tasks/:id/riskmanager/material/investigator',
        component: MaterialDetailComponent
    },

    {
        path: 'tasks/:id/trader/info',
        component: AuditTraderComponent,
        data: { routeType : 'info', title: '任务 - 查看详情' }
    },
    {
        path: 'tasks/:id/salesman/info',
        component: AuditSalesmanComponent,
        data: { routeType : 'info', title: '任务 - 查看详情' }
    },
    {
        path: 'tasks/:id/investigator/info',
        component: AuditInvestigatorComponent,
        data: { routeType : 'info', title: '任务 - 查看详情' }
    },
    {
        path: 'tasks/:id/supervisor/info',
        component: AuditSupervisorComponent,
        data: { routeType : 'info', title: '任务 - 查看详情' }
    },
    {
        path: 'tasks/:id/riskmanager/info',
        component: AuditRiskManagerComponent,
        data: { routeType : 'info', title: '任务 - 查看详情' }
    },
    {
        path: '**',
        redirectTo: '/tasks/pending'
    }
];


export const homePageRouterProviders : any[] = [

];

export const routing = RouterModule.forRoot(routes);