System.register([], function(exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var InMemoryDataServiceOrder, InMemoryDataServiceUser;
    return {
        setters:[],
        execute: function() {
            /**
             * Created by JinWYP on 8/8/16.
             */
            InMemoryDataServiceOrder = (function () {
                function InMemoryDataServiceOrder() {
                }
                InMemoryDataServiceOrder.prototype.createDb = function () {
                    var heroes = [
                        { id: 11, name: 'Mr. Nice' },
                        { id: 12, name: 'Narco' },
                        { id: 13, name: 'Bombasto' },
                        { id: 14, name: 'Celeritas' },
                        { id: 15, name: 'Magneta' },
                        { id: 16, name: 'RubberMan' },
                        { id: 17, name: 'Dynama' },
                        { id: 18, name: 'Dr IQ' },
                        { id: 19, name: 'Magma' },
                        { id: 20, name: 'Tornado' },
                        { id: 21, name: 'Tornado22' },
                        { id: 22, name: 'Tornado33' }
                    ];
                    return { heroes: heroes };
                };
                return InMemoryDataServiceOrder;
            }());
            exports_1("InMemoryDataServiceOrder", InMemoryDataServiceOrder);
            InMemoryDataServiceUser = (function () {
                function InMemoryDataServiceUser() {
                }
                InMemoryDataServiceUser.prototype.createDb = function () {
                    var users = [
                        { id: 11, name: 'Mr. Nice' },
                        { id: 12, name: 'Narco' },
                        { id: 13, name: 'Bombasto' },
                        { id: 14, name: 'Celeritas' },
                        { id: 15, name: 'Magneta' },
                        { id: 16, name: 'RubberMan' },
                        { id: 17, name: 'Dynama' },
                        { id: 18, name: 'Dr IQ' },
                        { id: 19, name: 'Magma' },
                        { id: 20, name: 'Tornado' },
                        { id: 21, name: 'Tornado22' },
                        { id: 22, name: 'Tornado33' }
                    ];
                    return { users: users };
                };
                return InMemoryDataServiceUser;
            }());
            exports_1("InMemoryDataServiceUser", InMemoryDataServiceUser);
        }
    }
});
//# sourceMappingURL=in-memory-data.service.js.map