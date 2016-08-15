/**
 * Created by JinWYP on 8/8/16.
 */
System.register(['@angular/core', '@angular/router', '../../service/order'], function(exports_1, context_1) {
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
    var core_1, router_1, order_1;
    var HomeDashboardComponent;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (router_1_1) {
                router_1 = router_1_1;
            },
            function (order_1_1) {
                order_1 = order_1_1;
            }],
        execute: function() {
            HomeDashboardComponent = (function () {
                function HomeDashboardComponent(router, orderService) {
                    this.router = router;
                    this.orderService = orderService;
                    this.heroes = [];
                }
                HomeDashboardComponent.prototype.ngOnInit = function () {
                    this.getHeroes();
                };
                HomeDashboardComponent.prototype.getHeroes = function () {
                    var _this = this;
                    this.orderService.getOrderList().then(function (heroes) { return _this.heroes = heroes.slice(1, 5); });
                };
                HomeDashboardComponent.prototype.gotoDetail = function (hero) {
                    console.log(hero);
                    var link = ['/orders', hero.id];
                    this.router.navigate(link);
                };
                HomeDashboardComponent = __decorate([
                    core_1.Component({
                        selector: 'home-dashboard',
                        moduleId: __moduleName || module.id,
                        templateUrl: 'home-dashboard.html'
                    }), 
                    __metadata('design:paramtypes', [router_1.Router, order_1.OrderService])
                ], HomeDashboardComponent);
                return HomeDashboardComponent;
            }());
            exports_1("HomeDashboardComponent", HomeDashboardComponent);
        }
    }
});
//# sourceMappingURL=home-dashboard.js.map