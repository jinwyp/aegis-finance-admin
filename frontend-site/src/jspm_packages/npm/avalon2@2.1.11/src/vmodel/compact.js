/* */ 
var share = require('./parts/compact');
var createViewModel = require('./parts/createViewModel');
var isSkip = share.isSkip;
var toJson = share.toJson;
var $$midway = share.$$midway;
var $$skipArray = share.$$skipArray;
var makeAccessor = share.makeAccessor;
var initViewModel = share.initViewModel;
var modelAccessor = share.modelAccessor;
var modelAdaptor = share.modelAdaptor;
var makeHashCode = avalon.makeHashCode;
function Observer() {}
function masterFactory(definition, heirloom, options) {
  var $skipArray = {};
  if (definition.$skipArray) {
    $skipArray = avalon.oneObject(definition.$skipArray);
    delete definition.$skipArray;
  }
  var keys = {};
  options = options || {};
  heirloom = heirloom || {};
  var accessors = {};
  var hashcode = makeHashCode('$');
  var pathname = options.pathname || '';
  options.id = options.id || hashcode;
  options.hashcode = options.hashcode || hashcode;
  var key,
      sid,
      spath;
  for (key in definition) {
    if ($$skipArray[key])
      continue;
    var val = keys[key] = definition[key];
    if (!isSkip(key, val, $skipArray)) {
      sid = options.id + '.' + key;
      spath = pathname ? pathname + '.' + key : key;
      accessors[key] = makeAccessor(sid, spath, heirloom);
    }
  }
  accessors.$model = modelAccessor;
  var $vmodel = new Observer();
  $vmodel = createViewModel($vmodel, accessors, definition);
  for (key in keys) {
    $vmodel[key] = keys[key];
    if (key in $skipArray) {
      delete keys[key];
    } else {
      keys[key] = true;
    }
  }
  initViewModel($vmodel, heirloom, keys, accessors, options);
  return $vmodel;
}
$$midway.masterFactory = masterFactory;
var empty = {};
function slaveFactory(before, after, heirloom, options) {
  var keys = {};
  var skips = {};
  var accessors = {};
  heirloom = heirloom || {};
  var pathname = options.pathname;
  var resue = before.$accessors || {};
  var key,
      sid,
      spath;
  for (key in after) {
    if ($$skipArray[key])
      continue;
    keys[key] = true;
    if (!isSkip(key, after[key], empty)) {
      if (resue[key]) {
        accessors[key] = resue[key];
      } else {
        sid = options.id + '.' + key;
        spath = pathname ? pathname + '.' + key : key;
        accessors[key] = makeAccessor(sid, spath, heirloom);
      }
    } else {
      skips[key] = after[key];
      delete after[key];
    }
  }
  options.hashcode = before.$hashcode || makeHashCode('$');
  accessors.$model = modelAccessor;
  var $vmodel = new Observer();
  $vmodel = createViewModel($vmodel, accessors, skips);
  for (key in skips) {
    $vmodel[key] = skips[key];
  }
  initViewModel($vmodel, heirloom, keys, accessors, options);
  return $vmodel;
}
$$midway.slaveFactory = slaveFactory;
function mediatorFactory(before, after) {
  var keys = {},
      key;
  var accessors = {};
  var unresolve = {};
  var heirloom = {};
  var arr = avalon.slice(arguments);
  var $skipArray = {};
  var isWidget = typeof this === 'function' && this.isWidget;
  var config;
  var configName;
  for (var i = 0; i < arr.length; i++) {
    var obj = arr[i];
    var $accessors = obj.$accessors;
    for (var key in obj) {
      if (!obj.hasOwnProperty(key)) {
        continue;
      }
      var cur = obj[key];
      if (key === '$skipArray') {
        if (Array.isArray(cur)) {
          cur.forEach(function(el) {
            $skipArray[el] = 1;
          });
        }
        continue;
      }
      if (isWidget && arr.indexOf(cur) !== -1) {
        config = cur;
        configName = key;
        continue;
      }
      keys[key] = cur;
      if (accessors[key] && avalon.isObject(cur)) {
        delete accessors[key];
      }
      if ($accessors && $accessors[key]) {
        accessors[key] = $accessors[key];
      } else if (typeof keys[key] !== 'function') {
        unresolve[key] = 1;
      }
    }
  }
  if (typeof this === 'function') {
    this(keys, unresolve);
  }
  for (key in unresolve) {
    if ($$skipArray[key] || accessors[key])
      continue;
    if (!isSkip(key, keys[key], $skipArray)) {
      accessors[key] = makeAccessor(before.$id, key, heirloom);
      accessors[key].set(keys[key]);
    }
  }
  var $vmodel = new Observer();
  $vmodel = createViewModel($vmodel, accessors, keys);
  for (key in keys) {
    if (!accessors[key]) {
      $vmodel[key] = keys[key];
    }
    if (isWidget && config && accessors[key] && config.hasOwnProperty(key)) {
      var GET = accessors[key].get;
      if (!GET.$decompose) {
        GET.$decompose = {};
      }
      GET.$decompose[configName + '.' + key] = $vmodel;
    }
    if (key in $$skipArray) {
      delete keys[key];
    } else {
      keys[key] = true;
    }
  }
  initViewModel($vmodel, heirloom, keys, accessors, {
    id: before.$id,
    hashcode: makeHashCode('$'),
    master: true
  });
  return $vmodel;
}
$$midway.mediatorFactory = avalon.mediatorFactory = mediatorFactory;
var __array__ = share.__array__;
var ap = Array.prototype;
var _splice = ap.splice;
function notifySize(array, size) {
  if (array.length !== size) {
    array.notify('length', array.length, size, true);
  }
}
__array__.removeAll = function(all) {
  var size = this.length;
  if (Array.isArray(all)) {
    for (var i = this.length - 1; i >= 0; i--) {
      if (all.indexOf(this[i]) !== -1) {
        _splice.call(this, i, 1);
      }
    }
  } else if (typeof all === 'function') {
    for (i = this.length - 1; i >= 0; i--) {
      var el = this[i];
      if (all(el, i)) {
        _splice.call(this, i, 1);
      }
    }
  } else {
    _splice.call(this, 0, this.length);
  }
  if (!avalon.modern) {
    this.$model = toJson(this);
  }
  notifySize(this, size);
  this.notify();
};
var __method__ = ['push', 'pop', 'shift', 'unshift', 'splice'];
__method__.forEach(function(method) {
  var original = ap[method];
  __array__[method] = function(a, b) {
    var args = [],
        size = this.length;
    if (method === 'splice' && Object(this[0]) === this[0]) {
      var old = this.slice(a, b);
      var neo = ap.slice.call(arguments, 2);
      var args = [a, b];
      for (var j = 0,
          jn = neo.length; j < jn; j++) {
        var item = old[j];
        args[j + 2] = modelAdaptor(neo[j], item, (item && item.$events || {}), {
          id: this.$id + '.*',
          master: true
        });
      }
    } else {
      for (var i = 0,
          n = arguments.length; i < n; i++) {
        args[i] = modelAdaptor(arguments[i], 0, {}, {
          id: this.$id + '.*',
          master: true
        });
      }
    }
    var result = original.apply(this, args);
    if (!avalon.modern) {
      this.$model = toJson(this);
    }
    notifySize(this, size);
    this.notify();
    return result;
  };
});
'sort,reverse'.replace(avalon.rword, function(method) {
  __array__[method] = function() {
    ap[method].apply(this, arguments);
    if (!avalon.modern) {
      this.$model = toJson(this);
    }
    this.notify();
    return this;
  };
});
module.exports = avalon;
