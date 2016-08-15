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
    var OrderDetailComponent;
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
            OrderDetailComponent = (function () {
                function OrderDetailComponent(orderService, route) {
                    this.orderService = orderService;
                    this.route = route;
                    this.close = new core_1.EventEmitter();
                    this.navigated = false; // true if navigated here
                }
                OrderDetailComponent.prototype.ngOnInit = function () {
                    var _this = this;
                    this.sub = this.route.params.subscribe(function (params) {
                        if (params['id'] !== undefined) {
                            var id = +params['id'];
                            _this.navigated = true;
                            _this.orderService.getOrderById(id).then(function (hero) { return _this.hero = hero; });
                        }
                        else {
                            _this.navigated = false;
                            _this.hero = new order_1.Order();
                        }
                    });
                };
                OrderDetailComponent.prototype.ngOnDestroy = function () {
                    this.sub.unsubscribe();
                };
                OrderDetailComponent.prototype.goBack = function (savedHero) {
                    if (savedHero === void 0) { savedHero = null; }
                    this.close.emit(savedHero);
                    if (this.navigated) {
                        window.history.back();
                    }
                };
                OrderDetailComponent.prototype.save = function () {
                    var _this = this;
                    this.orderService.save(this.hero)
                        .then(function (hero) {
                        _this.hero = hero; // saved hero, w/ id if new
                        _this.goBack(hero);
                    })
                        .catch(function (error) { return _this.error = error; }); // TODO: Display error message
                };
                __decorate([
                    core_1.Input(), 
                    __metadata('design:type', order_1.Order)
                ], OrderDetailComponent.prototype, "hero", void 0);
                __decorate([
                    core_1.Output(), 
                    __metadata('design:type', Object)
                ], OrderDetailComponent.prototype, "close", void 0);
                OrderDetailComponent = __decorate([
                    core_1.Component({
                        selector: 'order-detail',
                        moduleId: __moduleName || module.id,
                        templateUrl: 'order-detail.html'
                    }), 
                    __metadata('design:paramtypes', [order_1.OrderService, router_1.ActivatedRoute])
                ], OrderDetailComponent);
                return OrderDetailComponent;
            }());
            exports_1("OrderDetailComponent", OrderDetailComponent);
        }
    }
});
//# sourceMappingURL=order-detail.js.map