/* */ 
var propMap = require('./propMap');
var isVML = require('./isVML');
var rsvg = /^\[object SVG\w*Element\]$/;
var ramp = /&amp;/g;
function attrUpdate(node, vnode) {
  var attrs = vnode.changeAttr;
  if (!node || node.nodeType !== 1) {
    return;
  }
  if (attrs) {
    for (var attrName in attrs) {
      var val = attrs[attrName];
      if (attrName === 'href' || attrName === 'src') {
        if (!node.hasAttribute) {
          val = String(val).replace(ramp, '&');
        }
        node[attrName] = val;
        if (window.chrome && node.tagName === 'EMBED') {
          var parent = node.parentNode;
          var comment = document.createComment('ms-src');
          parent.replaceChild(comment, node);
          parent.replaceChild(node, comment);
        }
      } else if (attrName.indexOf('data-') === 0) {
        node.setAttribute(attrName, val);
      } else {
        var propName = propMap[attrName] || attrName;
        if (typeof node[propName] === 'boolean') {
          node[propName] = !!val;
        }
        if (val === false) {
          node.removeAttribute(propName);
          continue;
        }
        var isInnate = rsvg.test(node) ? false : (!avalon.modern && isVML(node)) ? true : attrName in node.cloneNode(false);
        if (isInnate) {
          node[propName] = val + '';
        } else {
          node.setAttribute(attrName, val);
        }
      }
    }
    vnode.changeAttr = null;
  }
}
var rvalidchars = /^[\],:{}\s]*$/,
    rvalidbraces = /(?:^|:|,)(?:\s*\[)+/g,
    rvalidescape = /\\(?:["\\\/bfnrt]|u[\da-fA-F]{4})/g,
    rvalidtokens = /"[^"\\\r\n]*"|true|false|null|-?(?:\d+\.|)\d+(?:[eE][+-]?\d+|)/g;
avalon.parseJSON = avalon.window.JSON ? JSON.parse : function(data) {
  if (typeof data === 'string') {
    data = data.trim();
    if (data) {
      if (rvalidchars.test(data.replace(rvalidescape, '@').replace(rvalidtokens, ']').replace(rvalidbraces, ''))) {
        return (new Function('return ' + data))();
      }
    }
    avalon.error('Invalid JSON: ' + data);
  }
  return data;
};
avalon.fn.attr = function(name, value) {
  if (arguments.length === 2) {
    this[0].setAttribute(name, value);
    return this;
  } else {
    return this[0].getAttribute(name);
  }
};
module.exports = attrUpdate;
