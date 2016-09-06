/* */ 
var skipArray = require('../vmodel/parts/skipArray');
var legalTags = {
  wbr: 1,
  xmp: 1,
  template: 1
};
var events = 'onInit,onReady,onViewChange,onDispose';
var componentEvents = avalon.oneObject(events);
var immunity = events.split(',').concat('is', 'define');
var onceWarn = true;
function initComponent(src, rawOption, local, template) {
  var tag = src.type;
  var is = src.props.is;
  if (!legalTags[tag] && !isCustomTag(tag)) {
    avalon.warn(tag + '不合适做组件的标签');
    return;
  }
  var hooks = {};
  if (!rawOption) {
    options = [];
  } else {
    var options = [].concat(rawOption);
    options.forEach(function(a) {
      if (a && typeof a === 'object') {
        mixinHooks(hooks, (a.$model || a), true);
      }
    });
  }
  var definition = avalon.components[is];
  if (!definition) {
    return;
  }
  if (!hooks.$id && onceWarn) {
    avalon.warn('warning!', is, '组件最好在ms-widget配置对象中指定全局不重复的$id以提高性能!\n', '若在ms-for循环中可以利用 ($index,el) in @array 中的$index拼写你的$id\n', '如 ms-widget="{is:\'ms-button\',$id:\'btn\'+$index}"');
    onceWarn = false;
  }
  var define = hooks.define;
  define = define || avalon.directives.widget.define;
  var $id = hooks.$id || src.props.wid || 'w' + (new Date - 0);
  var defaults = avalon.mix(true, {}, definition.defaults);
  mixinHooks(hooks, defaults, false);
  var skipProps = immunity.concat();
  function sweeper(a, b) {
    skipProps.forEach(function(k) {
      delete a[k];
      delete b[k];
    });
  }
  sweeper.isWidget = true;
  var vmodel = define.apply(sweeper, [src.vmodel, defaults].concat(options));
  if (!avalon.modern) {
    for (var i in vmodel) {
      if (!skipArray[i] && typeof vmodel[i] === 'function') {
        vmodel[i] = vmodel[i].bind(vmodel);
      }
    }
  }
  vmodel.$id = $id;
  avalon.vmodels[$id] = vmodel;
  for (var e in componentEvents) {
    if (hooks[e]) {
      hooks[e].forEach(function(fn) {
        vmodel.$watch(e, fn);
      });
    }
  }
  var shell = avalon.lexer(template);
  var shellRoot = shell[0];
  var sc = shellRoot.children;
  if (sc && sc.length === 1 && sc[0].nodeValue) {
    shellRoot.children = avalon.lexer(sc[0].nodeValue);
  }
  delete shellRoot.isVoidTag;
  delete shellRoot.template;
  delete shellRoot.skipContent;
  delete shellRoot.props['ms-widget'];
  shellRoot.type = 'cheng7';
  shellRoot.children = shellRoot.children || [];
  shellRoot.props.is = is;
  shellRoot.props.wid = $id;
  avalon.speedUp(shell);
  var render = avalon.render(shell, local);
  var finalTemplate = definition.template.trim();
  if (typeof definition.getTemplate === 'function') {
    finalTemplate = definition.getTemplate(vmodel, finalTemplate);
  }
  var vtree = avalon.lexer(finalTemplate);
  if (vtree.length > 1) {
    avalon.error('组件必须用一个元素包起来');
  }
  var soleSlot = definition.soleSlot;
  replaceSlot(vtree, soleSlot);
  avalon.speedUp(vtree);
  var render2 = avalon.render(vtree);
  var str = fnTemplate + '';
  var zzzzz = soleSlot ? avalon.quote(soleSlot) : "null";
  str = str.replace('XXXXX', stringifyAnonymous(render)).replace('YYYYY', stringifyAnonymous(render2)).replace('ZZZZZ', zzzzz);
  var begin = str.indexOf('{') + 1;
  var end = str.lastIndexOf("}");
  var lastFn = Function('vm', 'local', str.slice(begin, end));
  vmodel.$render = lastFn;
  src['component-vm:' + is] = vmodel;
  return vmodel.$render = lastFn;
}
module.exports = initComponent;
function stringifyAnonymous(fn) {
  return fn.toString().replace('anonymous', '').replace(/\s*\/\*\*\//g, '');
}
function fnTemplate() {
  var shell = (XXXXX)(vm, local);
  var shellRoot = shell[0];
  var vtree = (YYYYY)(vm, local);
  var component = vtree[0];
  var orderUniq = {};
  String('ms-widget,' + shellRoot.order + ',' + component.order).replace(avalon.rword, function(a) {
    if (a !== 'undefined')
      orderUniq[a] = a;
  });
  shellRoot.order = Object.keys(orderUniq).join(',');
  for (var i in shellRoot) {
    if (i !== 'children' && i !== 'type') {
      if (i === 'props') {
        avalon.mix(component.props, shellRoot.props);
      } else {
        component[i] = shellRoot[i];
      }
    }
  }
  var soleSlot = ZZZZZ;
  var slots = avalon.collectSlots(shellRoot, soleSlot);
  if (soleSlot && (!slots[soleSlot] || !slots[soleSlot].length)) {
    slots[soleSlot] = [{
      nodeType: 3,
      type: '#text',
      nodeValue: vm[soleSlot],
      dynamic: true
    }];
  }
  avalon.insertSlots(vtree, slots);
  delete component.skipAttrs;
  delete component.skipContent;
  return vtree;
}
function replaceSlot(vtree, slotName) {
  for (var i = 0,
      el; el = vtree[i]; i++) {
    if (el.type === 'slot') {
      vtree.splice(i, 1, {
        type: '#comment',
        nodeValue: 'slot:' + (el.props.name || slotName),
        nodeType: 8,
        dynamic: (el.props.name || slotName)
      }, {
        type: '#comment',
        nodeValue: 'slot-end:',
        nodeType: 8
      });
      i++;
    } else if (el.nodeType === 1 && el.children) {
      replaceSlot(el.children, slotName);
    }
  }
}
avalon.insertSlots = function(vtree, slots) {
  for (var i = 0,
      el; el = vtree[i]; i++) {
    if (el.nodeType === 8 && slots[el.dynamic]) {
      var args = [i + 1, 0].concat(slots[el.dynamic]);
      vtree.splice.apply(vtree, args);
      i += slots[el.dynamic].length;
    } else if (el.nodeType === 1 && el.children) {
      avalon.insertSlots(el.children, slots);
    }
  }
};
avalon.collectSlots = function(node, soleSlot) {
  var slots = {};
  if (soleSlot) {
    slots[soleSlot] = node.children;
    slots.__sole__ = soleSlot;
  } else {
    node.children.forEach(function(el, i) {
      if (el.nodeType === 1) {
        var name = el.props.slot;
        if (name) {
          if (Array.isArray(slots[name])) {
            slots[name].push(el);
          } else {
            slots[name] = [el];
          }
        }
      } else if (el.dynamic === 'for' && /slot=['"](\w+)/.test(el.template)) {
        var a = RegExp.$1;
        slots[a] = node.children.slice(i, i + 2);
      }
    });
  }
  return slots;
};
var rcustomTag = /^[a-z]([a-z\d]+\-)+[a-z\d]+$/;
function isCustomTag(type) {
  return rcustomTag.test(type) || avalon.components[type];
}
function mixinHooks(target, option, overwrite) {
  for (var k in option) {
    var v = option[k];
    if (componentEvents[k]) {
      if (k in target) {
        target[k].push(v);
      } else {
        target[k] = [option[k]];
      }
    } else {
      if (overwrite) {
        target[k] = v;
      }
    }
  }
}
