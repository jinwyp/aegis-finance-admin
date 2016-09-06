/* */ 
var avalon = require('./seed/compact');
require('./filters/index');
require('./vdom/compact');
require('./dom/compact');
require('./directives/compact');
require('./strategy/index');
avalon.onComponentDispose = require('./component/dispose.compact');
require('./vmodel/compact');
module.exports = avalon;
