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
    var OrderListComponent;
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
            OrderListComponent = (function () {
                function OrderListComponent(router, orderService) {
                    this.router = router;
                    this.orderService = orderService;
                    this.title = 'Tour of Heroes';
                    this.addingHero = false;
                }
                OrderListComponent.prototype.ngOnInit = function () {
                    this.getHeroes();
                };
                OrderListComponent.prototype.onSelect = function (hero) {
                    this.selectedHero = hero;
                };
                OrderListComponent.prototype.getHeroes = function () {
                    var _this = this;
                    this.orderService.getOrderList().then(function (heroes) { return _this.heroes = heroes; });
                };
                OrderListComponent.prototype.gotoDetail = function () {
                    this.router.navigate(['/orders', this.selectedHero.id]);
                };
                OrderListComponent.prototype.addHero = function () {
                    this.addingHero = true;
                    this.selectedHero = null;
                };
                OrderListComponent.prototype.close = function (savedHero) {
                    this.addingHero = false;
                    if (savedHero) {
                        this.getHeroes();
                    }
                };
                OrderListComponent.prototype.deleteHero = function (hero, event) {
                    var _this = this;
                    event.stopPropagation();
                    this.orderService.delete(hero)
                        .then(function (res) {
                        _this.heroes = _this.heroes.filter(function (h) { return h !== hero; });
                        if (_this.selectedHero === hero) {
                            _this.selectedHero = null;
                        }
                    })
                        .catch(function (error) { return _this.error = error; });
                };
                OrderListComponent = __decorate([
                    core_1.Component({
                        selector: 'order-list',
                        moduleId: __moduleName || module.id,
                        templateUrl: 'order-list.html'
                    }), 
                    __metadata('design:paramtypes', [router_1.Router, order_1.OrderService])
                ], OrderListComponent);
                return OrderListComponent;
            }());
            exports_1("OrderListComponent", OrderListComponent);
        }
    }
});
//# sourceMappingURL=order-list.js.map