/* */ 
var update = require('./_update');
avalon.directive('if', {
  priority: 6,
  diff: function(copy, src, name) {
    var cur = !!copy[name];
    var old = src[name];
    src[name] = cur;
    if (src.execIf) {
      if (!cur) {
        copy.nodeType = 8;
        copy.order = '';
      }
      if (copy === src || cur !== old) {
        update(src, this.update);
      }
    } else {
      update(src, this.update, 'afterChange');
    }
  },
  update: function(dom, vdom, parent) {
    var show = vdom['ms-if'];
    vdom.execIf = true;
    if (show) {
      vdom.nodeType = 1;
      vdom.nodeValue = null;
      var comment = vdom.comment;
      if (!comment) {
        return;
      }
      parent = comment.parentNode;
      parent.replaceChild(dom, comment);
      avalon.applyEffect(dom, vdom, {hook: 'onEnterDone'});
    } else {
      avalon.applyEffect(dom, vdom, {
        hook: 'onLeaveDone',
        cb: function() {
          var comment = document.createComment('ms-if');
          parent = parent || dom.parentNode;
          vdom.nodeValue = 'ms-if';
          parent.replaceChild(comment, dom);
          vdom.nodeType = 8;
          vdom.comment = comment;
        }
      });
    }
  }
});
