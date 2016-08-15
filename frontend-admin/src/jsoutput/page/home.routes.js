/**
 * Created by JinWYP on 8/8/16.
 */
System.register(['@angular/router', '../components/order/order-list', '../components/home/home-dashboard', '../components/order/order-detail'], function(exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var router_1, order_list_1, home_dashboard_1, order_detail_1;
    var routes, homePageRouterProviders;
    return {
        setters:[
            function (router_1_1) {
                router_1 = router_1_1;
            },
            function (order_list_1_1) {
                order_list_1 = order_list_1_1;
            },
            function (home_dashboard_1_1) {
                home_dashboard_1 = home_dashboard_1_1;
            },
            function (order_detail_1_1) {
                order_detail_1 = order_detail_1_1;
            }],
        execute: function() {
            routes = [
                {
                    path: '',
                    redirectTo: '/dashboard',
                    pathMatch: 'full'
                },
                {
                    path: 'dashboard',
                    component: home_dashboard_1.HomeDashboardComponent
                },
                {
                    path: 'orders',
                    component: order_list_1.OrderListComponent
                },
                {
                    path: 'orders/:id',
                    component: order_detail_1.OrderDetailComponent
                }
            ];
            exports_1("homePageRouterProviders", homePageRouterProviders = [
                router_1.provideRouter(routes)
            ]);
        }
    }
});
//# sourceMappingURL=home.routes.js.map