System.register(['@angular/core', '../../service/user'], function(exports_1, context_1) {
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
    var core_1, user_1;
    var LoginComponent;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (user_1_1) {
                user_1 = user_1_1;
            }],
        execute: function() {
            LoginComponent = (function () {
                function LoginComponent(userHttp) {
                    this.userHttp = userHttp;
                    this.active = true;
                    this.submitted = false;
                    this.currentUser = new user_1.User();
                    this.powers = ['Really Smart', 'Super Flexible', 'Super Hot', 'Weather Changer'];
                }
                LoginComponent.prototype.ngOnInit = function () {
                };
                LoginComponent.prototype.login = function () {
                    this.submitted = true;
                    // this.orderService.getOrderList().then(heroes => this.heroes = heroes);
                };
                LoginComponent.prototype.showInfoAfterlogin = function () {
                    this.submitted = false;
                };
                LoginComponent.prototype.clearInput = function () {
                    var _this = this;
                    this.currentUser = new user_1.User();
                    this.active = false;
                    setTimeout(function () { return _this.active = true; }, 0);
                };
                Object.defineProperty(LoginComponent.prototype, "diagnostic", {
                    get: function () { return JSON.stringify(this.currentUser); },
                    enumerable: true,
                    configurable: true
                });
                LoginComponent = __decorate([
                    core_1.Component({
                        selector: 'page-login',
                        moduleId: __moduleName || module.id,
                        templateUrl: 'login-index.html'
                    }), 
                    __metadata('design:paramtypes', [user_1.UserService])
                ], LoginComponent);
                return LoginComponent;
            }());
            exports_1("LoginComponent", LoginComponent);
        }
    }
});
//# sourceMappingURL=login-index.js.map