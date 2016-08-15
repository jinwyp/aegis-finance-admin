/**
 * Created by JinWYP on 8/11/16.
 */
System.register(['@angular/core', '@angular/platform-browser', '@angular/forms', '@angular/http', '@angular/router', 'angular2-in-memory-web-api', '../mock/api/in-memory-data.service', './home.routes', '../service/order', '../components/header/header', '../components/left-menu/left-menu', '../components/home/home-index', '../components/home/home-dashboard', '../components/order/order-list', '../components/order/order-detail'], function(exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
        var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
        if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
        else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
        return c > 3 && r && Object.defineProperty(target, key, r), r;
    };
    var __metadata = (this && this.__metadata) || function (k, v) {
        if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
    };
    var core_1, platform_browser_1, forms_1, http_1, router_1, http_2, angular2_in_memory_web_api_1, in_memory_data_service_1, home_routes_1, order_1, header_1, left_menu_1, home_index_1, home_dashboard_1, order_list_1, order_detail_1;
    var HomeModule;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (platform_browser_1_1) {
                platform_browser_1 = platform_browser_1_1;
            },
            function (forms_1_1) {
                forms_1 = forms_1_1;
            },
            function (http_1_1) {
                http_1 = http_1_1;
                http_2 = http_1_1;
            },
            function (router_1_1) {
                router_1 = router_1_1;
            },
            function (angular2_in_memory_web_api_1_1) {
                angular2_in_memory_web_api_1 = angular2_in_memory_web_api_1_1;
            },
            function (in_memory_data_service_1_1) {
                in_memory_data_service_1 = in_memory_data_service_1_1;
            },
            function (home_routes_1_1) {
                home_routes_1 = home_routes_1_1;
            },
            function (order_1_1) {
                order_1 = order_1_1;
            },
            function (header_1_1) {
                header_1 = header_1_1;
            },
            function (left_menu_1_1) {
                left_menu_1 = left_menu_1_1;
            },
            function (home_index_1_1) {
                home_index_1 = home_index_1_1;
            },
            function (home_dashboard_1_1) {
                home_dashboard_1 = home_dashboard_1_1;
            },
            function (order_list_1_1) {
                order_list_1 = order_list_1_1;
            },
            function (order_detail_1_1) {
                order_detail_1 = order_detail_1_1;
            }],
        execute: function() {
            HomeModule = (function () {
                function HomeModule() {
                }
                HomeModule = __decorate([
                    core_1.NgModule({
                        imports: [platform_browser_1.BrowserModule, forms_1.FormsModule, http_1.HttpModule],
                        declarations: [
                            router_1.ROUTER_DIRECTIVES,
                            header_1.headerComponent, left_menu_1.LeftMenuComponent,
                            home_index_1.HomeComponent, home_dashboard_1.HomeDashboardComponent, order_list_1.OrderListComponent, order_detail_1.OrderDetailComponent
                        ],
                        providers: [order_1.OrderService,
                            home_routes_1.homePageRouterProviders,
                            { provide: http_2.XHRBackend, useClass: angular2_in_memory_web_api_1.InMemoryBackendService },
                            { provide: angular2_in_memory_web_api_1.SEED_DATA, useClass: in_memory_data_service_1.InMemoryDataServiceOrder } // in-mem server data
                        ],
                        bootstrap: [home_index_1.HomeComponent]
                    }), 
                    __metadata('design:paramtypes', [])
                ], HomeModule);
                return HomeModule;
            }());
            exports_1("HomeModule", HomeModule);
        }
    }
});
//# sourceMappingURL=home.module.js.map