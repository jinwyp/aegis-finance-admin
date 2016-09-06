/* */ 
var update = require('./_update');
var reconcile = require('../strategy/reconcile');
var tryInitComponent = require('../component/init');
avalon.component = function(name, definition) {
  if (!avalon.components[name]) {
    avalon.components[name] = definition;
  }
};
avalon.directive('widget', {
  priority: 4,
  parse: function(copy, src, binding) {
    src.props.wid = src.props.wid || avalon.makeHashCode('w');
    copy[binding.name] = avalon.parseExpr(binding);
    copy.template = src.template;
    copy.vmodel = '__vmodel__';
    copy.local = '__local__';
  },
  define: function() {
    return avalon.mediatorFactory.apply(this, arguments);
  },
  diff: function(copy, src, name, copyList, srcList, index) {
    var a = copy[name];
    if (Object(a) === a) {
      var is = src.props.is || (/^ms\-/.test(src.type) ? src.type : 0);
      if (!is) {
        a = a.$model || a;
        if (Array.isArray(a)) {
          a.unshift({});
          avalon.mix.apply(0, a);
          a = a.shift();
        }
        is = a.is;
      }
      var vmName = 'component-vm:' + is;
      src.props.is = is;
      src.vmodel = copy.vmodel;
      if (!src[vmName]) {
        if (!tryInitComponent(src, copy[name], copy.local, copy.template)) {
          src.nodeType = 8;
          src.nodeValue = 'unresolved component placeholder';
          copyList[index] = src;
          update(src, this.mountComment);
          return;
        }
      }
      var comVm = src[vmName];
      var scope = avalon.scopes[comVm.$id];
      if (scope && scope.vmodel) {
        var com = scope.vmodel.$element;
        if (src.dom !== com) {
          var component = com.vtree[0];
          srcList[index] = copyList[index] = component;
          src.com = com;
          if (!component.skipContent) {
            component.skipContent = 'optimize';
          }
          update(src, this.replaceCachedComponent);
          update(component, function() {
            if (component.skipContent === 'optimize') {
              component.skipContent = true;
            }
          }, 'afterChange');
          return;
        }
      }
      var render = comVm.$render;
      var tree = render(comVm, copy.local);
      var component = tree[0];
      if (component && isComponentReady(component)) {
        component.local = copy.local;
        src.dynamic = true;
        Array(vmName, 'component-html:' + is, 'component-ready:' + is, 'dom', 'dynamic').forEach(function(name) {
          component[name] = src[name];
        });
        component.vmodel = comVm;
        copyList[index] = component;
        if (src.nodeType === 8 && src.comment) {
          component.dom = src.comment;
          src.type = '#comment';
        }
        if (src.type !== component.type) {
          srcList[index] = component;
          update(component, this.mountComponent);
        } else {
          update(src, this.updateComponent);
        }
      } else {
        src.nodeType = 8;
        src.nodeValue = 'unresolved component placeholder';
        copyList[index] = src;
        update(src, this.mountComment);
      }
    } else {
      if (src.props.is === copy.props.is) {
        update(src, this.updateComponent);
      }
    }
  },
  replaceCachedComponent: function(dom, vdom, parent) {
    var com = vdom.com;
    parent.replaceChild(com, dom);
    delete vdom.com;
  },
  mountComment: function(dom, vdom, parent) {
    var comment = document.createComment(vdom.nodeValue);
    vdom.dom = comment;
    parent.replaceChild(comment, dom);
  },
  updateComponent: function(dom, vdom) {
    var vm = vdom["component-vm:" + vdom.props.is];
    var viewChangeObservers = vm.$events.onViewChange;
    if (viewChangeObservers && viewChangeObservers.length) {
      update(vdom, viewChangeHandle, 'afterChange');
    }
  },
  mountComponent: function(dom, vdom, parent) {
    var com = avalon.vdomAdaptor(vdom, 'toDOM');
    var is = vdom.props.is;
    var vm = vdom['component-vm:' + is];
    vm.$fire('onInit', {
      type: 'init',
      vmodel: vm,
      is: is
    });
    reconcile([com], [vdom]);
    parent.replaceChild(com, dom);
    vdom.dom = vm.$element = com;
    com.vtree = [vdom];
    avalon.onComponentDispose(com);
    vdom['component-ready:' + is] = true;
    avalon.scopes[vm.$id] = {
      vmodel: vm,
      top: vdom.vmodel,
      local: vdom.local
    };
    update(vdom, function() {
      vm.$fire('onReady', {
        type: 'ready',
        target: com,
        vmodel: vm,
        is: is
      });
    }, 'afterChange');
    update(vdom, function() {
      vdom['component-html:' + is] = avalon.vdomAdaptor(vdom, 'toHTML');
    }, 'afterChange');
  }
});
function viewChangeHandle(dom, vdom) {
  var is = vdom.props.is;
  var vm = vdom['component-vm:' + is];
  var html = 'component-html:' + is;
  var preHTML = vdom[html];
  var curHTML = avalon.vdomAdaptor(vdom, 'toHTML');
  if (preHTML !== curHTML) {
    vdom[html] = curHTML;
    vm.$fire('onViewChange', {
      type: 'viewchange',
      target: dom,
      vmodel: vm,
      is: is
    });
  }
}
function isComponentReady(vnode) {
  var isReady = true;
  try {
    hasUnresolvedComponent(vnode);
  } catch (e) {
    isReady = false;
  }
  return isReady;
}
function hasUnresolvedComponent(vnode) {
  vnode.children.forEach(function(el) {
    if (el.nodeType === 8) {
      if (el.nodeValue === 'unresolved component placeholder') {
        throw 'unresolved';
      }
    } else if (el.children) {
      hasUnresolvedComponent(el);
    }
  });
}
