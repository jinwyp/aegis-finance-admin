/* */ 
var attrUpdate = require('../dom/attr/modern');
var update = require('./_update');
avalon.directive('attr', {
  diff: function(copy, src, name) {
    var a = copy[name];
    var p = src[name];
    if (a && typeof a === 'object') {
      if (Array.isArray(a)) {
        a = avalon.mix.apply({}, a);
      }
      if (copy === src || typeof p !== 'object') {
        src.changeAttr = src[name] = a;
      } else {
        var patch = {};
        var hasChange = false;
        for (var i in a) {
          if (a[i] !== p[i]) {
            hasChange = true;
            patch[i] = a[i];
          }
        }
        if (hasChange) {
          src[name] = a;
          src.changeAttr = patch;
        }
      }
      if (src.changeAttr) {
        update(src, this.update);
      }
    }
    if (copy !== src) {
      delete copy[name];
    }
  },
  update: attrUpdate
});
