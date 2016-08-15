System.register(['@angular/platform-browser-dynamic', './login.module'], function(exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var platform_browser_dynamic_1, login_module_1;
    return {
        setters:[
            function (platform_browser_dynamic_1_1) {
                platform_browser_dynamic_1 = platform_browser_dynamic_1_1;
            },
            function (login_module_1_1) {
                login_module_1 = login_module_1_1;
            }],
        execute: function() {
            platform_browser_dynamic_1.platformBrowserDynamic().bootstrapModule(login_module_1.LoginModule);
        }
    }
});
//# sourceMappingURL=login.js.map