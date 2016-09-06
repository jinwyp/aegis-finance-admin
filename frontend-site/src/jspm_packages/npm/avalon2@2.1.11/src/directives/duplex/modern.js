/* */ 
var update = require('../_update');
var evaluatorPool = require('../../strategy/parser/evaluatorPool');
var stringify = require('../../strategy/parser/stringify');
var rchangeFilter = /\|\s*change\b/;
var rcheckedType = /^(?:checkbox|radio)$/;
var rdebounceFilter = /\|\s*debounce(?:\(([^)]+)\))?/;
var updateModelByEvent = require('./updateModelByEvent.modern');
var updateModelByValue = require('./updateModelByValue');
var updateModel = require('./updateModelHandle');
var updateView = require('./updateView.modern');
var addValidateField = require('./addValidateField');
avalon.directive('duplex', {
  priority: 2000,
  parse: function(copy, src, binding) {
    var expr = binding.expr;
    var etype = src.props.type;
    var parser = binding.param,
        dtype;
    var isChecked = false;
    parser = parser ? parser.split('-').map(function(a) {
      if (a === 'checked') {
        isChecked = true;
      }
      return a;
    }) : [];
    if (rcheckedType.test(etype) && isChecked) {
      parser = [];
      dtype = 'radio';
    }
    if (!/input|textarea|select/.test(src.type)) {
      if ('contenteditable' in src.props) {
        dtype = 'contenteditable';
      }
    } else if (!dtype) {
      dtype = src.type === 'select' ? 'select' : etype === 'checkbox' ? 'checkbox' : etype === 'radio' ? 'radio' : 'input';
    }
    var isChanged = false,
        debounceTime = 0;
    if (dtype === 'input' || dtype === 'contenteditable') {
      var isString = true;
      if (rchangeFilter.test(expr)) {
        isChanged = true;
      }
      if (!isChanged) {
        var match = expr.match(rdebounceFilter);
        if (match) {
          debounceTime = parseInt(match[1], 10) || 300;
        }
      }
    }
    var changed = copy.props['data-duplex-changed'];
    copy.parser = avalon.quote(parser + "");
    copy.modelValue = '(' + avalon.parseExpr(binding, 'duplex') + ')(__vmodel__)';
    var format = evaluatorPool.get('duplex:format:' + expr);
    copy.duplexData = stringify({
      type: dtype,
      vmodel: '__vmodel__',
      isChecked: isChecked,
      isString: !!isString,
      isChanged: isChanged,
      debounceTime: debounceTime,
      format: format || 'function(vm, a){return a}',
      set: evaluatorPool.get('duplex:set:' + expr),
      callback: changed ? avalon.parseExpr(changed, 'on') : 'avalon.noop'
    });
  },
  diff: function(copy, src) {
    if (copy === src || !src.duplexData) {
      var data = src.duplexData = copy.duplexData;
      data.parser = copy.parser ? copy.parser.split(',') : [];
      data.parse = parseValue;
      var curValue = copy.modelValue;
    } else {
      data = src.duplexData;
      var curValue = copy.modelValue;
      var preValue = data.modelValue;
      copy.duplexData = 0;
      if (!Array.isArray(curValue) && curValue === preValue) {
        return;
      }
    }
    if (data.isString) {
      var value = data.parse(curValue);
      if (value !== curValue) {
        data.set(data.vmodel, value);
        return;
      }
      curValue = value;
    }
    data.modelValue = curValue;
    if (data.isString) {
      value = data.format(data.vmodel, curValue + '');
      if (value !== curValue + '') {
        data.set(data.vmodel, value);
        return;
      }
      curValue = value;
    }
    data.viewValue = curValue;
    update(src, this.update, 'afterChange');
  },
  update: function(dom, vdom) {
    if (dom && dom.nodeType === 1) {
      if (!dom.__ms_duplex__) {
        dom.__ms_duplex__ = vdom.duplexData;
        updateModelByEvent(dom, vdom);
      }
      var data = dom.__ms_duplex__;
      data.dom = dom;
      addValidateField(dom, vdom);
      if (data.isString && !avalon.msie && updateModelByValue === false && !dom.valueHijack) {
        dom.valueHijack = updateModel;
        var intervalID = setInterval(function() {
          if (!avalon.contains(avalon.root, dom)) {
            clearInterval(intervalID);
          } else {
            dom.valueHijack();
          }
        }, 30);
      }
      updateView[data.type].call(data);
      if (dom.caret) {
        var pos = data.caretPos;
        pos && data.setCaret(dom, pos.start, pos.end);
        data.caretPos = null;
      }
    }
  }
});
function parseValue(val) {
  for (var i = 0,
      k; k = this.parser[i++]; ) {
    var fn = avalon.parsers[k];
    if (fn) {
      val = fn.call(this, val);
    }
  }
  return val;
}
