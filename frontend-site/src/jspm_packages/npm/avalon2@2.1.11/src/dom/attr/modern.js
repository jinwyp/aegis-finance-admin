/* */ 
var propMap = require('./propMap');
var rsvg = /^\[object SVG\w*Element\]$/;
function attrUpdate(node, vnode) {
  var attrs = vnode.changeAttr;
  if (attrs) {
    for (var attrName in attrs) {
      var val = attrs[attrName];
      if (attrName === 'src' && window.chrome && node.tagName === 'EMBED') {
        node[attrName] = val;
        var parent = node.parentNode;
        var comment = document.createComment('ms-src');
        parent.replaceChild(comment, node);
        parent.replaceChild(node, comment);
      } else if (attrName.indexOf('data-') == 0) {
        node.setAttribute(attrName, val);
      } else {
        var propName = propMap[attrName] || attrName;
        if (typeof node[propName] === 'boolean') {
          node[propName] = !!val;
        }
        if (val === false) {
          node.removeAttribute(attrName);
          continue;
        }
        var isInnate = rsvg.test(node) ? false : attrName in node.cloneNode(false);
        if (isInnate) {
          node[propName] = val + '';
        } else {
          node.setAttribute(attrName, val);
        }
      }
    }
  }
  vnode.changeAttr = null;
}
avalon.parseJSON = JSON.parse;
avalon.fn.attr = function(name, value) {
  if (arguments.length === 2) {
    this[0].setAttribute(name, value);
    return this;
  } else {
    return this[0].getAttribute(name);
  }
};
module.exports = attrUpdate;
