/* */ 
var document = avalon.document;
var window = avalon.window;
var root = avalon.root;
var W3C = avalon.modern;
var getShortID = require('../../seed/lang.share').getShortID;
var canBubbleUp = require('./canBubbleUp');
if (!W3C) {
  delete canBubbleUp.change;
  delete canBubbleUp.select;
}
var eventHooks = avalon.eventHooks;
avalon.bind = function(elem, type, fn) {
  if (elem.nodeType === 1) {
    var value = elem.getAttribute('avalon-events') || '';
    var uuid = getShortID(fn);
    var hook = eventHooks[type];
    if (hook) {
      type = hook.type || type;
      if (hook.fix) {
        fn = hook.fix(elem, fn);
        fn.uuid = uuid;
      }
    }
    var key = type + ':' + uuid;
    avalon.eventListeners[fn.uuid] = fn;
    if (value.indexOf(type + ':') === -1) {
      if (canBubbleUp[type] || (avalon.modern && focusBlur[type])) {
        delegateEvent(type);
      } else {
        nativeBind(elem, type, dispatch);
      }
    }
    var keys = value.split(',');
    if (keys[0] === '') {
      keys.shift();
    }
    if (keys.indexOf(key) === -1) {
      keys.push(key);
      elem.setAttribute('avalon-events', keys.join(','));
    }
  } else {
    nativeBind(elem, type, fn);
  }
  return fn;
};
avalon.unbind = function(elem, type, fn) {
  if (elem.nodeType === 1) {
    var value = elem.getAttribute('avalon-events') || '';
    switch (arguments.length) {
      case 1:
        nativeUnBind(elem, type, dispatch);
        elem.removeAttribute('avalon-events');
        break;
      case 2:
        value = value.split(',').filter(function(str) {
          return str.indexOf(type + ':') === -1;
        }).join(',');
        elem.setAttribute('avalon-events', value);
        break;
      default:
        var search = type + ':' + fn.uuid;
        value = value.split(',').filter(function(str) {
          return str !== search;
        }).join(',');
        elem.setAttribute('avalon-events', value);
        delete avalon.eventListeners[fn.uuid];
        break;
    }
  } else {
    nativeUnBind(elem, type, fn);
  }
};
var typeRegExp = {};
function collectHandlers(elem, type, handlers) {
  var value = elem.getAttribute('avalon-events');
  if (value && (elem.disabled !== true || type !== 'click')) {
    var uuids = [];
    var reg = typeRegExp[type] || (typeRegExp[type] = new RegExp("\\b" + type + '\\:([^,\\s]+)', 'g'));
    value.replace(reg, function(a, b) {
      uuids.push(b);
      return a;
    });
    if (uuids.length) {
      handlers.push({
        elem: elem,
        uuids: uuids
      });
    }
  }
  elem = elem.parentNode;
  var g = avalon.gestureEvents || {};
  if (elem && elem.getAttribute && (canBubbleUp[type] || g[type])) {
    collectHandlers(elem, type, handlers);
  }
}
var rhandleHasVm = /^e/;
var stopImmediate = false;
function dispatch(event) {
  event = new avEvent(event);
  var type = event.type;
  var elem = event.target;
  var handlers = [];
  collectHandlers(elem, type, handlers);
  var i = 0,
      j,
      uuid,
      handler;
  while ((handler = handlers[i++]) && !event.cancelBubble) {
    var host = event.currentTarget = handler.elem;
    j = 0;
    while ((uuid = handler.uuids[j++])) {
      if (stopImmediate) {
        stopImmediate = false;
        break;
      }
      var fn = avalon.eventListeners[uuid];
      if (fn) {
        var vm = rhandleHasVm.test(uuid) ? handler.elem._ms_context_ : 0;
        if (vm && vm.$hashcode === false) {
          return avalon.unbind(elem, type, fn);
        }
        var ret = fn.call(vm || elem, event, host._ms_local);
        if (ret === false) {
          event.preventDefault();
          event.stopPropagation();
        }
      }
    }
  }
}
var focusBlur = {
  focus: true,
  blur: true
};
var nativeBind = W3C ? function(el, type, fn, capture) {
  el.addEventListener(type, fn, capture);
} : function(el, type, fn) {
  el.attachEvent('on' + type, fn);
};
var nativeUnBind = W3C ? function(el, type, fn) {
  el.removeEventListener(type, fn);
} : function(el, type, fn) {
  el.detachEvent('on' + type, fn);
};
function delegateEvent(type) {
  var value = root.getAttribute('delegate-events') || '';
  if (value.indexOf(type) === -1) {
    var arr = value.match(avalon.rword) || [];
    arr.push(type);
    root.setAttribute('delegate-events', arr.join(','));
    nativeBind(root, type, dispatch, !!focusBlur[type]);
  }
}
avalon.fireDom = function(elem, type, opts) {
  if (document.createEvent) {
    var hackEvent = document.createEvent('Events');
    hackEvent.initEvent(type, true, true, opts);
    avalon.shadowCopy(hackEvent, opts);
    elem.dispatchEvent(hackEvent);
  } else if (root.contains(elem)) {
    hackEvent = document.createEventObject();
    avalon.shadowCopy(hackEvent, opts);
    elem.fireEvent('on' + type, hackEvent);
  }
};
var rmouseEvent = /^(?:mouse|contextmenu|drag)|click/;
var rconstant = /^[A-Z_]+$/;
function avEvent(event) {
  if (event.originalEvent) {
    return this;
  }
  for (var i in event) {
    if (!rconstant.test(i) && typeof event[i] !== 'function') {
      this[i] = event[i];
    }
  }
  if (!this.target) {
    this.target = event.srcElement;
  }
  var target = this.target;
  if (this.which == null && event.type.indexOf('key') === 0) {
    this.which = event.charCode != null ? event.charCode : event.keyCode;
  } else if (rmouseEvent.test(event.type) && !('pageX' in this)) {
    var doc = target.ownerDocument || document;
    var box = doc.compatMode === 'BackCompat' ? doc.body : doc.documentElement;
    this.pageX = event.clientX + (box.scrollLeft >> 0) - (box.clientLeft >> 0);
    this.pageY = event.clientY + (box.scrollTop >> 0) - (box.clientTop >> 0);
    this.wheelDeltaY = this.wheelDelta;
    this.wheelDeltaX = 0;
  }
  this.timeStamp = new Date() - 0;
  this.originalEvent = event;
}
avEvent.prototype = {
  preventDefault: function() {
    var e = this.originalEvent || {};
    e.returnValue = this.returnValue = false;
    if (e.preventDefault) {
      e.preventDefault();
    }
  },
  stopPropagation: function() {
    var e = this.originalEvent || {};
    e.cancelBubble = this.cancelBubble = true;
    if (e.stopPropagation) {
      e.stopPropagation();
    }
  },
  stopImmediatePropagation: function() {
    stopImmediate = true;
    this.stopPropagation();
  },
  toString: function() {
    return '[object Event]';
  }
};
if (!('onmouseenter' in root)) {
  avalon.each({
    mouseenter: 'mouseover',
    mouseleave: 'mouseout'
  }, function(origType, fixType) {
    eventHooks[origType] = {
      type: fixType,
      fix: function(elem, fn) {
        return function(e) {
          var t = e.relatedTarget;
          if (!t || (t !== elem && !(elem.compareDocumentPosition(t) & 16))) {
            delete e.type;
            e.type = origType;
            return fn.apply(this, arguments);
          }
        };
      }
    };
  });
}
avalon.each({
  AnimationEvent: 'animationend',
  WebKitAnimationEvent: 'webkitAnimationEnd'
}, function(construct, fixType) {
  if (window[construct] && !eventHooks.animationend) {
    eventHooks.animationend = {type: fixType};
  }
});
if (!('oninput' in document.createElement('input'))) {
  eventHooks.input = {
    type: 'propertychange',
    fix: function(elem, fn) {
      return function(e) {
        if (e.propertyName === 'value') {
          e.type = 'input';
          return fn.apply(this, arguments);
        }
      };
    }
  };
}
if (document.onmousewheel === void 0) {
  var fixWheelType = document.onwheel !== void 0 ? 'wheel' : 'DOMMouseScroll';
  var fixWheelDelta = fixWheelType === 'wheel' ? 'deltaY' : 'detail';
  eventHooks.mousewheel = {
    type: fixWheelType,
    fix: function(elem, fn) {
      return function(e) {
        var delta = e[fixWheelDelta] > 0 ? -120 : 120;
        e.wheelDelta = ~~elem._ms_wheel_ + delta;
        elem._ms_wheel_ = e.wheelDeltaY = e.wheelDelta;
        e.wheelDeltaX = 0;
        if (Object.defineProperty) {
          Object.defineProperty(e, 'type', {value: 'mousewheel'});
        }
        return fn.apply(this, arguments);
      };
    }
  };
}
avalon.fn.bind = function(type, fn, phase) {
  if (this[0]) {
    return avalon.bind(this[0], type, fn, phase);
  }
};
avalon.fn.unbind = function(type, fn, phase) {
  if (this[0]) {
    avalon.unbind(this[0], type, fn, phase);
  }
  return this;
};
