/**
 * Created by JinWYP on 8/11/16.
 */
System.register(['@angular/core', '@angular/http', 'rxjs/add/operator/toPromise'], function(exports_1, context_1) {
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
    var core_1, http_1;
    var User, UserService;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (http_1_1) {
                http_1 = http_1_1;
            },
            function (_1) {}],
        execute: function() {
            User = (function () {
                function User() {
                    this.id = 0;
                    this.username = '';
                    this.password = '';
                }
                return User;
            }());
            UserService = (function () {
                function UserService(http) {
                    this.http = http;
                    this.ApiUrlOrder = 'app2/heroes'; // URL to web api
                }
                UserService.prototype.handleError = function (error) {
                    console.error('Http 请求发生错误!! ', error);
                    return Promise.reject(error.message || error);
                };
                UserService.prototype.getOrderList = function () {
                    return this.http.get(this.ApiUrlOrder).toPromise()
                        .then(function (response) { return response.json().data; })
                        .catch(this.handleError);
                };
                UserService.prototype.getUserById = function (id) {
                    return this.getOrderList().then(function (heroes) { return heroes.find(function (hero) { return hero.id === id; }); });
                };
                // Add new Hero
                UserService.prototype.post = function (hero) {
                    var headers = new http_1.Headers({ 'Content-Type': 'application/json' });
                    return this.http.post(this.ApiUrlOrder, JSON.stringify(hero), { headers: headers }).toPromise()
                        .then(function (res) { return res.json().data; })
                        .catch(this.handleError);
                };
                // Update existing Hero
                UserService.prototype.put = function (hero) {
                    var headers = new http_1.Headers();
                    headers.append('Content-Type', 'application/json');
                    var url = this.ApiUrlOrder + "/" + hero.id;
                    return this.http.put(url, JSON.stringify(hero), { headers: headers }).toPromise()
                        .then(function () { return hero; })
                        .catch(this.handleError);
                };
                UserService.prototype.delete = function (hero) {
                    var headers = new http_1.Headers();
                    headers.append('Content-Type', 'application/json');
                    var url = this.ApiUrlOrder + "/" + hero.id;
                    return this.http.delete(url, { headers: headers }).toPromise()
                        .catch(this.handleError);
                };
                UserService.prototype.save = function (hero) {
                    if (hero.id) {
                        return this.put(hero);
                    }
                    return this.post(hero);
                };
                UserService = __decorate([
                    core_1.Injectable(), 
                    __metadata('design:paramtypes', [http_1.Http])
                ], UserService);
                return UserService;
            }());
            exports_1("User", User);
            exports_1("UserService", UserService);
        }
    }
});
//# sourceMappingURL=user.js.map