/* */ 
var reconcile = require('./reconcile');
var needRenderIds = [];
var renderingID = false;
avalon.suspendUpdate = 0;
function batchUpdate(id) {
  if (renderingID) {
    return avalon.Array.ensure(needRenderIds, id);
  } else {
    renderingID = id;
  }
  var scope = avalon.scopes[id];
  if (!scope || !document.nodeName || avalon.suspendUpdate) {
    return renderingID = null;
  }
  var vm = scope.vmodel;
  var dom = vm.$element;
  var source = dom.vtree || [];
  var renderFn = vm.$render;
  var copy = renderFn(scope.vmodel, scope.local);
  if (scope.isTemp) {
    reconcile([dom], source, dom.parentNode);
    delete avalon.scopes[id];
  }
  avalon.diff(copy, source);
  var index = needRenderIds.indexOf(renderingID);
  renderingID = 0;
  if (index > -1) {
    var removed = needRenderIds.splice(index, 1);
    return batchUpdate(removed[0]);
  }
  var more = needRenderIds.shift();
  if (more) {
    batchUpdate(more);
  }
}
module.exports = avalon.batch = batchUpdate;
