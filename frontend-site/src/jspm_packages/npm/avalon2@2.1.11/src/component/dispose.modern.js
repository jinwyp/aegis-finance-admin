/* */ 
var ret = require('./dispose.share');
var fireDisposeHook = ret.fireDisposeHook;
var fireDisposeHooks = ret.fireDisposeHooks;
var fireDisposeHookDelay = ret.fireDisposeHookDelay;
function byRewritePrototype() {
  if (byRewritePrototype.execute) {
    return;
  }
  byRewritePrototype.execute = true;
  var p = Node.prototype;
  function rewite(name, fn) {
    var cb = p[name];
    p[name] = function(a, b) {
      return fn.call(this, cb, a, b);
    };
  }
  rewite('removeChild', function(fn, a, b) {
    fn.call(this, a, b);
    if (a.nodeType === 1) {
      fireDisposeHookDelay(a);
    }
    return a;
  });
  rewite('replaceChild', function(fn, a, b) {
    fn.call(this, a, b);
    if (a.nodeType === 1) {
      fireDisposeHookDelay(a);
    }
    return a;
  });
  var ep = Element.prototype;
  function newSetter(html) {
    var all = avalon.slice(this.getElementsByTagName('*'));
    oldSetter.call(this, html);
    fireDisposeHooks(all);
  }
  var obj = Object.getOwnPropertyDescriptor(ep, 'innerHTML');
  var oldSetter = obj.set;
  obj.set = newSetter;
  Object.defineProperty(ep, 'innerHTML', obj);
  rewite('appendChild', function(fn, a) {
    fn.call(this, a);
    if (a.nodeType === 1 && this.nodeType === 11) {
      fireDisposeHookDelay(a);
    }
    return a;
  });
  rewite('insertBefore', function(fn, a, b) {
    fn.call(this, a, b);
    if (a.nodeType === 1 && this.nodeType === 11) {
      fireDisposeHookDelay(a);
    }
    return a;
  });
}
module.exports = function onComponentDispose(dom) {
  byRewritePrototype(dom);
};
