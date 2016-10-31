/* */ 
"format cjs";
(function(root, factory) {
  var modules = {},
      _require = function(deps, callback) {
        var args,
            len,
            i;
        if (typeof deps === 'string') {
          return getModule(deps);
        } else {
          args = [];
          for (len = deps.length, i = 0; i < len; i++) {
            args.push(getModule(deps[i]));
          }
          return callback.apply(null, args);
        }
      },
      _define = function(id, deps, factory) {
        if (arguments.length === 2) {
          factory = deps;
          deps = null;
        }
        _require(deps || [], function() {
          setModule(id, factory, arguments);
        });
      },
      setModule = function(id, factory, args) {
        var module = {exports: factory},
            returned;
        if (typeof factory === 'function') {
          args.length || (args = [_require, module.exports, module]);
          returned = factory.apply(null, args);
          returned !== undefined && (module.exports = returned);
        }
        modules[id] = module.exports;
      },
      getModule = function(id) {
        var module = modules[id] || root[id];
        if (!module) {
          throw new Error('`' + id + '` is undefined');
        }
        return module;
      },
      exportsTo = function(obj) {
        var key,
            host,
            parts,
            part,
            last,
            ucFirst;
        ucFirst = function(str) {
          return str && (str.charAt(0).toUpperCase() + str.substr(1));
        };
        for (key in modules) {
          host = obj;
          if (!modules.hasOwnProperty(key)) {
            continue;
          }
          parts = key.split('/');
          last = ucFirst(parts.pop());
          while ((part = ucFirst(parts.shift()))) {
            host[part] = host[part] || {};
            host = host[part];
          }
          host[last] = modules[key];
        }
        return obj;
      },
      makeExport = function(dollar) {
        root.__dollar = dollar;
        return exportsTo(factory(root, _define, _require));
      },
      origin;
  if (typeof module === 'object' && typeof module.exports === 'object') {
    module.exports = makeExport();
  } else if (typeof define === 'function' && define.amd) {
    define(['jquery'], makeExport);
  } else {
    origin = root.WebUploader;
    root.WebUploader = makeExport();
    root.WebUploader.noConflict = function() {
      root.WebUploader = origin;
    };
  }
})(window, function(window, define, require) {
  define('dollar-third', [], function() {
    var $ = window.__dollar || window.jQuery || window.Zepto;
    if (!$) {
      throw new Error('jQuery or Zepto not found!');
    }
    return $;
  });
  define('dollar', ['dollar-third'], function(_) {
    return _;
  });
  define('promise-third', ['dollar'], function($) {
    return {
      Deferred: $.Deferred,
      when: $.when,
      isPromise: function(anything) {
        return anything && typeof anything.then === 'function';
      }
    };
  });
  define('promise', ['promise-third'], function(_) {
    return _;
  });
  define('base', ['dollar', 'promise'], function($, promise) {
    var noop = function() {},
        call = Function.call;
    function uncurryThis(fn) {
      return function() {
        return call.apply(fn, arguments);
      };
    }
    function bindFn(fn, context) {
      return function() {
        return fn.apply(context, arguments);
      };
    }
    function createObject(proto) {
      var f;
      if (Object.create) {
        return Object.create(proto);
      } else {
        f = function() {};
        f.prototype = proto;
        return new f();
      }
    }
    return {
      version: '0.1.5',
      $: $,
      Deferred: promise.Deferred,
      isPromise: promise.isPromise,
      when: promise.when,
      browser: (function(ua) {
        var ret = {},
            webkit = ua.match(/WebKit\/([\d.]+)/),
            chrome = ua.match(/Chrome\/([\d.]+)/) || ua.match(/CriOS\/([\d.]+)/),
            ie = ua.match(/MSIE\s([\d\.]+)/) || ua.match(/(?:trident)(?:.*rv:([\w.]+))?/i),
            firefox = ua.match(/Firefox\/([\d.]+)/),
            safari = ua.match(/Safari\/([\d.]+)/),
            opera = ua.match(/OPR\/([\d.]+)/);
        webkit && (ret.webkit = parseFloat(webkit[1]));
        chrome && (ret.chrome = parseFloat(chrome[1]));
        ie && (ret.ie = parseFloat(ie[1]));
        firefox && (ret.firefox = parseFloat(firefox[1]));
        safari && (ret.safari = parseFloat(safari[1]));
        opera && (ret.opera = parseFloat(opera[1]));
        return ret;
      })(navigator.userAgent),
      os: (function(ua) {
        var ret = {},
            android = ua.match(/(?:Android);?[\s\/]+([\d.]+)?/),
            ios = ua.match(/(?:iPad|iPod|iPhone).*OS\s([\d_]+)/);
        android && (ret.android = parseFloat(android[1]));
        ios && (ret.ios = parseFloat(ios[1].replace(/_/g, '.')));
        return ret;
      })(navigator.userAgent),
      inherits: function(Super, protos, staticProtos) {
        var child;
        if (typeof protos === 'function') {
          child = protos;
          protos = null;
        } else if (protos && protos.hasOwnProperty('constructor')) {
          child = protos.constructor;
        } else {
          child = function() {
            return Super.apply(this, arguments);
          };
        }
        $.extend(true, child, Super, staticProtos || {});
        child.__super__ = Super.prototype;
        child.prototype = createObject(Super.prototype);
        protos && $.extend(true, child.prototype, protos);
        return child;
      },
      noop: noop,
      bindFn: bindFn,
      log: (function() {
        if (window.console) {
          return bindFn(console.log, console);
        }
        return noop;
      })(),
      nextTick: (function() {
        return function(cb) {
          setTimeout(cb, 1);
        };
      })(),
      slice: uncurryThis([].slice),
      guid: (function() {
        var counter = 0;
        return function(prefix) {
          var guid = (+new Date()).toString(32),
              i = 0;
          for (; i < 5; i++) {
            guid += Math.floor(Math.random() * 65535).toString(32);
          }
          return (prefix || 'wu_') + guid + (counter++).toString(32);
        };
      })(),
      formatSize: function(size, pointLength, units) {
        var unit;
        units = units || ['B', 'K', 'M', 'G', 'TB'];
        while ((unit = units.shift()) && size > 1024) {
          size = size / 1024;
        }
        return (unit === 'B' ? size : size.toFixed(pointLength || 2)) + unit;
      }
    };
  });
  define('mediator', ['base'], function(Base) {
    var $ = Base.$,
        slice = [].slice,
        separator = /\s+/,
        protos;
    function findHandlers(arr, name, callback, context) {
      return $.grep(arr, function(handler) {
        return handler && (!name || handler.e === name) && (!callback || handler.cb === callback || handler.cb._cb === callback) && (!context || handler.ctx === context);
      });
    }
    function eachEvent(events, callback, iterator) {
      $.each((events || '').split(separator), function(_, key) {
        iterator(key, callback);
      });
    }
    function triggerHanders(events, args) {
      var stoped = false,
          i = -1,
          len = events.length,
          handler;
      while (++i < len) {
        handler = events[i];
        if (handler.cb.apply(handler.ctx2, args) === false) {
          stoped = true;
          break;
        }
      }
      return !stoped;
    }
    protos = {
      on: function(name, callback, context) {
        var me = this,
            set;
        if (!callback) {
          return this;
        }
        set = this._events || (this._events = []);
        eachEvent(name, callback, function(name, callback) {
          var handler = {e: name};
          handler.cb = callback;
          handler.ctx = context;
          handler.ctx2 = context || me;
          handler.id = set.length;
          set.push(handler);
        });
        return this;
      },
      once: function(name, callback, context) {
        var me = this;
        if (!callback) {
          return me;
        }
        eachEvent(name, callback, function(name, callback) {
          var once = function() {
            me.off(name, once);
            return callback.apply(context || me, arguments);
          };
          once._cb = callback;
          me.on(name, once, context);
        });
        return me;
      },
      off: function(name, cb, ctx) {
        var events = this._events;
        if (!events) {
          return this;
        }
        if (!name && !cb && !ctx) {
          this._events = [];
          return this;
        }
        eachEvent(name, cb, function(name, cb) {
          $.each(findHandlers(events, name, cb, ctx), function() {
            delete events[this.id];
          });
        });
        return this;
      },
      trigger: function(type) {
        var args,
            events,
            allEvents;
        if (!this._events || !type) {
          return this;
        }
        args = slice.call(arguments, 1);
        events = findHandlers(this._events, type);
        allEvents = findHandlers(this._events, 'all');
        return triggerHanders(events, args) && triggerHanders(allEvents, arguments);
      }
    };
    return $.extend({installTo: function(obj) {
        return $.extend(obj, protos);
      }}, protos);
  });
  define('uploader', ['base', 'mediator'], function(Base, Mediator) {
    var $ = Base.$;
    function Uploader(opts) {
      this.options = $.extend(true, {}, Uploader.options, opts);
      this._init(this.options);
    }
    Uploader.options = {};
    Mediator.installTo(Uploader.prototype);
    $.each({
      upload: 'start-upload',
      stop: 'stop-upload',
      getFile: 'get-file',
      getFiles: 'get-files',
      addFile: 'add-file',
      addFiles: 'add-file',
      sort: 'sort-files',
      removeFile: 'remove-file',
      cancelFile: 'cancel-file',
      skipFile: 'skip-file',
      retry: 'retry',
      isInProgress: 'is-in-progress',
      makeThumb: 'make-thumb',
      md5File: 'md5-file',
      getDimension: 'get-dimension',
      addButton: 'add-btn',
      predictRuntimeType: 'predict-runtime-type',
      refresh: 'refresh',
      disable: 'disable',
      enable: 'enable',
      reset: 'reset'
    }, function(fn, command) {
      Uploader.prototype[fn] = function() {
        return this.request(command, arguments);
      };
    });
    $.extend(Uploader.prototype, {
      state: 'pending',
      _init: function(opts) {
        var me = this;
        me.request('init', opts, function() {
          me.state = 'ready';
          me.trigger('ready');
        });
      },
      option: function(key, val) {
        var opts = this.options;
        if (arguments.length > 1) {
          if ($.isPlainObject(val) && $.isPlainObject(opts[key])) {
            $.extend(opts[key], val);
          } else {
            opts[key] = val;
          }
        } else {
          return key ? opts[key] : opts;
        }
      },
      getStats: function() {
        var stats = this.request('get-stats');
        return stats ? {
          successNum: stats.numOfSuccess,
          progressNum: stats.numOfProgress,
          cancelNum: stats.numOfCancel,
          invalidNum: stats.numOfInvalid,
          uploadFailNum: stats.numOfUploadFailed,
          queueNum: stats.numOfQueue,
          interruptNum: stats.numofInterrupt
        } : {};
      },
      trigger: function(type) {
        var args = [].slice.call(arguments, 1),
            opts = this.options,
            name = 'on' + type.substring(0, 1).toUpperCase() + type.substring(1);
        if (Mediator.trigger.apply(this, arguments) === false || $.isFunction(opts[name]) && opts[name].apply(this, args) === false || $.isFunction(this[name]) && this[name].apply(this, args) === false || Mediator.trigger.apply(Mediator, [this, type].concat(args)) === false) {
          return false;
        }
        return true;
      },
      destroy: function() {
        this.request('destroy', arguments);
        this.off();
      },
      request: Base.noop
    });
    Base.create = Uploader.create = function(opts) {
      return new Uploader(opts);
    };
    Base.Uploader = Uploader;
    return Uploader;
  });
  define('runtime/runtime', ['base', 'mediator'], function(Base, Mediator) {
    var $ = Base.$,
        factories = {},
        getFirstKey = function(obj) {
          for (var key in obj) {
            if (obj.hasOwnProperty(key)) {
              return key;
            }
          }
          return null;
        };
    function Runtime(options) {
      this.options = $.extend({container: document.body}, options);
      this.uid = Base.guid('rt_');
    }
    $.extend(Runtime.prototype, {
      getContainer: function() {
        var opts = this.options,
            parent,
            container;
        if (this._container) {
          return this._container;
        }
        parent = $(opts.container || document.body);
        container = $(document.createElement('div'));
        container.attr('id', 'rt_' + this.uid);
        container.css({
          position: 'absolute',
          top: '0px',
          left: '0px',
          width: '1px',
          height: '1px',
          overflow: 'hidden'
        });
        parent.append(container);
        parent.addClass('webuploader-container');
        this._container = container;
        this._parent = parent;
        return container;
      },
      init: Base.noop,
      exec: Base.noop,
      destroy: function() {
        this._container && this._container.remove();
        this._parent && this._parent.removeClass('webuploader-container');
        this.off();
      }
    });
    Runtime.orders = 'html5,flash';
    Runtime.addRuntime = function(type, factory) {
      factories[type] = factory;
    };
    Runtime.hasRuntime = function(type) {
      return !!(type ? factories[type] : getFirstKey(factories));
    };
    Runtime.create = function(opts, orders) {
      var type,
          runtime;
      orders = orders || Runtime.orders;
      $.each(orders.split(/\s*,\s*/g), function() {
        if (factories[this]) {
          type = this;
          return false;
        }
      });
      type = type || getFirstKey(factories);
      if (!type) {
        throw new Error('Runtime Error');
      }
      runtime = new factories[type](opts);
      return runtime;
    };
    Mediator.installTo(Runtime.prototype);
    return Runtime;
  });
  define('runtime/client', ['base', 'mediator', 'runtime/runtime'], function(Base, Mediator, Runtime) {
    var cache;
    cache = (function() {
      var obj = {};
      return {
        add: function(runtime) {
          obj[runtime.uid] = runtime;
        },
        get: function(ruid, standalone) {
          var i;
          if (ruid) {
            return obj[ruid];
          }
          for (i in obj) {
            if (standalone && obj[i].__standalone) {
              continue;
            }
            return obj[i];
          }
          return null;
        },
        remove: function(runtime) {
          delete obj[runtime.uid];
        }
      };
    })();
    function RuntimeClient(component, standalone) {
      var deferred = Base.Deferred(),
          runtime;
      this.uid = Base.guid('client_');
      this.runtimeReady = function(cb) {
        return deferred.done(cb);
      };
      this.connectRuntime = function(opts, cb) {
        if (runtime) {
          throw new Error('already connected!');
        }
        deferred.done(cb);
        if (typeof opts === 'string' && cache.get(opts)) {
          runtime = cache.get(opts);
        }
        runtime = runtime || cache.get(null, standalone);
        if (!runtime) {
          runtime = Runtime.create(opts, opts.runtimeOrder);
          runtime.__promise = deferred.promise();
          runtime.once('ready', deferred.resolve);
          runtime.init();
          cache.add(runtime);
          runtime.__client = 1;
        } else {
          Base.$.extend(runtime.options, opts);
          runtime.__promise.then(deferred.resolve);
          runtime.__client++;
        }
        standalone && (runtime.__standalone = standalone);
        return runtime;
      };
      this.getRuntime = function() {
        return runtime;
      };
      this.disconnectRuntime = function() {
        if (!runtime) {
          return;
        }
        runtime.__client--;
        if (runtime.__client <= 0) {
          cache.remove(runtime);
          delete runtime.__promise;
          runtime.destroy();
        }
        runtime = null;
      };
      this.exec = function() {
        if (!runtime) {
          return;
        }
        var args = Base.slice(arguments);
        component && args.unshift(component);
        return runtime.exec.apply(this, args);
      };
      this.getRuid = function() {
        return runtime && runtime.uid;
      };
      this.destroy = (function(destroy) {
        return function() {
          destroy && destroy.apply(this, arguments);
          this.trigger('destroy');
          this.off();
          this.exec('destroy');
          this.disconnectRuntime();
        };
      })(this.destroy);
    }
    Mediator.installTo(RuntimeClient.prototype);
    return RuntimeClient;
  });
  define('lib/dnd', ['base', 'mediator', 'runtime/client'], function(Base, Mediator, RuntimeClent) {
    var $ = Base.$;
    function DragAndDrop(opts) {
      opts = this.options = $.extend({}, DragAndDrop.options, opts);
      opts.container = $(opts.container);
      if (!opts.container.length) {
        return;
      }
      RuntimeClent.call(this, 'DragAndDrop');
    }
    DragAndDrop.options = {
      accept: null,
      disableGlobalDnd: false
    };
    Base.inherits(RuntimeClent, {
      constructor: DragAndDrop,
      init: function() {
        var me = this;
        me.connectRuntime(me.options, function() {
          me.exec('init');
          me.trigger('ready');
        });
      }
    });
    Mediator.installTo(DragAndDrop.prototype);
    return DragAndDrop;
  });
  define('widgets/widget', ['base', 'uploader'], function(Base, Uploader) {
    var $ = Base.$,
        _init = Uploader.prototype._init,
        _destroy = Uploader.prototype.destroy,
        IGNORE = {},
        widgetClass = [];
    function isArrayLike(obj) {
      if (!obj) {
        return false;
      }
      var length = obj.length,
          type = $.type(obj);
      if (obj.nodeType === 1 && length) {
        return true;
      }
      return type === 'array' || type !== 'function' && type !== 'string' && (length === 0 || typeof length === 'number' && length > 0 && (length - 1) in obj);
    }
    function Widget(uploader) {
      this.owner = uploader;
      this.options = uploader.options;
    }
    $.extend(Widget.prototype, {
      init: Base.noop,
      invoke: function(apiName, args) {
        var map = this.responseMap;
        if (!map || !(apiName in map) || !(map[apiName] in this) || !$.isFunction(this[map[apiName]])) {
          return IGNORE;
        }
        return this[map[apiName]].apply(this, args);
      },
      request: function() {
        return this.owner.request.apply(this.owner, arguments);
      }
    });
    $.extend(Uploader.prototype, {
      _init: function() {
        var me = this,
            widgets = me._widgets = [],
            deactives = me.options.disableWidgets || '';
        $.each(widgetClass, function(_, klass) {
          (!deactives || !~deactives.indexOf(klass._name)) && widgets.push(new klass(me));
        });
        return _init.apply(me, arguments);
      },
      request: function(apiName, args, callback) {
        var i = 0,
            widgets = this._widgets,
            len = widgets && widgets.length,
            rlts = [],
            dfds = [],
            widget,
            rlt,
            promise,
            key;
        args = isArrayLike(args) ? args : [args];
        for (; i < len; i++) {
          widget = widgets[i];
          rlt = widget.invoke(apiName, args);
          if (rlt !== IGNORE) {
            if (Base.isPromise(rlt)) {
              dfds.push(rlt);
            } else {
              rlts.push(rlt);
            }
          }
        }
        if (callback || dfds.length) {
          promise = Base.when.apply(Base, dfds);
          key = promise.pipe ? 'pipe' : 'then';
          return promise[key](function() {
            var deferred = Base.Deferred(),
                args = arguments;
            if (args.length === 1) {
              args = args[0];
            }
            setTimeout(function() {
              deferred.resolve(args);
            }, 1);
            return deferred.promise();
          })[callback ? key : 'done'](callback || Base.noop);
        } else {
          return rlts[0];
        }
      },
      destroy: function() {
        _destroy.apply(this, arguments);
        this._widgets = null;
      }
    });
    Uploader.register = Widget.register = function(responseMap, widgetProto) {
      var map = {
        init: 'init',
        destroy: 'destroy',
        name: 'anonymous'
      },
          klass;
      if (arguments.length === 1) {
        widgetProto = responseMap;
        $.each(widgetProto, function(key) {
          if (key[0] === '_' || key === 'name') {
            key === 'name' && (map.name = widgetProto.name);
            return;
          }
          map[key.replace(/[A-Z]/g, '-$&').toLowerCase()] = key;
        });
      } else {
        map = $.extend(map, responseMap);
      }
      widgetProto.responseMap = map;
      klass = Base.inherits(Widget, widgetProto);
      klass._name = map.name;
      widgetClass.push(klass);
      return klass;
    };
    Uploader.unRegister = Widget.unRegister = function(name) {
      if (!name || name === 'anonymous') {
        return;
      }
      for (var i = widgetClass.length; i--; ) {
        if (widgetClass[i]._name === name) {
          widgetClass.splice(i, 1);
        }
      }
    };
    return Widget;
  });
  define('widgets/filednd', ['base', 'uploader', 'lib/dnd', 'widgets/widget'], function(Base, Uploader, Dnd) {
    var $ = Base.$;
    Uploader.options.dnd = '';
    return Uploader.register({
      name: 'dnd',
      init: function(opts) {
        if (!opts.dnd || this.request('predict-runtime-type') !== 'html5') {
          return;
        }
        var me = this,
            deferred = Base.Deferred(),
            options = $.extend({}, {
              disableGlobalDnd: opts.disableGlobalDnd,
              container: opts.dnd,
              accept: opts.accept
            }),
            dnd;
        this.dnd = dnd = new Dnd(options);
        dnd.once('ready', deferred.resolve);
        dnd.on('drop', function(files) {
          me.request('add-file', [files]);
        });
        dnd.on('accept', function(items) {
          return me.owner.trigger('dndAccept', items);
        });
        dnd.init();
        return deferred.promise();
      },
      destroy: function() {
        this.dnd && this.dnd.destroy();
      }
    });
  });
  define('lib/filepaste', ['base', 'mediator', 'runtime/client'], function(Base, Mediator, RuntimeClent) {
    var $ = Base.$;
    function FilePaste(opts) {
      opts = this.options = $.extend({}, opts);
      opts.container = $(opts.container || document.body);
      RuntimeClent.call(this, 'FilePaste');
    }
    Base.inherits(RuntimeClent, {
      constructor: FilePaste,
      init: function() {
        var me = this;
        me.connectRuntime(me.options, function() {
          me.exec('init');
          me.trigger('ready');
        });
      }
    });
    Mediator.installTo(FilePaste.prototype);
    return FilePaste;
  });
  define('widgets/filepaste', ['base', 'uploader', 'lib/filepaste', 'widgets/widget'], function(Base, Uploader, FilePaste) {
    var $ = Base.$;
    return Uploader.register({
      name: 'paste',
      init: function(opts) {
        if (!opts.paste || this.request('predict-runtime-type') !== 'html5') {
          return;
        }
        var me = this,
            deferred = Base.Deferred(),
            options = $.extend({}, {
              container: opts.paste,
              accept: opts.accept
            }),
            paste;
        this.paste = paste = new FilePaste(options);
        paste.once('ready', deferred.resolve);
        paste.on('paste', function(files) {
          me.owner.request('add-file', [files]);
        });
        paste.init();
        return deferred.promise();
      },
      destroy: function() {
        this.paste && this.paste.destroy();
      }
    });
  });
  define('lib/blob', ['base', 'runtime/client'], function(Base, RuntimeClient) {
    function Blob(ruid, source) {
      var me = this;
      me.source = source;
      me.ruid = ruid;
      this.size = source.size || 0;
      if (!source.type && this.ext && ~'jpg,jpeg,png,gif,bmp'.indexOf(this.ext)) {
        this.type = 'image/' + (this.ext === 'jpg' ? 'jpeg' : this.ext);
      } else {
        this.type = source.type || 'application/octet-stream';
      }
      RuntimeClient.call(me, 'Blob');
      this.uid = source.uid || this.uid;
      if (ruid) {
        me.connectRuntime(ruid);
      }
    }
    Base.inherits(RuntimeClient, {
      constructor: Blob,
      slice: function(start, end) {
        return this.exec('slice', start, end);
      },
      getSource: function() {
        return this.source;
      }
    });
    return Blob;
  });
  define('lib/file', ['base', 'lib/blob'], function(Base, Blob) {
    var uid = 1,
        rExt = /\.([^.]+)$/;
    function File(ruid, file) {
      var ext;
      this.name = file.name || ('untitled' + uid++);
      ext = rExt.exec(file.name) ? RegExp.$1.toLowerCase() : '';
      if (!ext && file.type) {
        ext = /\/(jpg|jpeg|png|gif|bmp)$/i.exec(file.type) ? RegExp.$1.toLowerCase() : '';
        this.name += '.' + ext;
      }
      this.ext = ext;
      this.lastModifiedDate = file.lastModifiedDate || (new Date()).toLocaleString();
      Blob.apply(this, arguments);
    }
    return Base.inherits(Blob, File);
  });
  define('lib/filepicker', ['base', 'runtime/client', 'lib/file'], function(Base, RuntimeClent, File) {
    var $ = Base.$;
    function FilePicker(opts) {
      opts = this.options = $.extend({}, FilePicker.options, opts);
      opts.container = $(opts.id);
      if (!opts.container.length) {
        throw new Error('按钮指定错误');
      }
      opts.innerHTML = opts.innerHTML || opts.label || opts.container.html() || '';
      opts.button = $(opts.button || document.createElement('div'));
      opts.button.html(opts.innerHTML);
      opts.container.html(opts.button);
      RuntimeClent.call(this, 'FilePicker', true);
    }
    FilePicker.options = {
      button: null,
      container: null,
      label: null,
      innerHTML: null,
      multiple: true,
      accept: null,
      name: 'file'
    };
    Base.inherits(RuntimeClent, {
      constructor: FilePicker,
      init: function() {
        var me = this,
            opts = me.options,
            button = opts.button;
        button.addClass('webuploader-pick');
        me.on('all', function(type) {
          var files;
          switch (type) {
            case 'mouseenter':
              button.addClass('webuploader-pick-hover');
              break;
            case 'mouseleave':
              button.removeClass('webuploader-pick-hover');
              break;
            case 'change':
              files = me.exec('getFiles');
              me.trigger('select', $.map(files, function(file) {
                file = new File(me.getRuid(), file);
                file._refer = opts.container;
                return file;
              }), opts.container);
              break;
          }
        });
        me.connectRuntime(opts, function() {
          me.refresh();
          me.exec('init', opts);
          me.trigger('ready');
        });
        this._resizeHandler = Base.bindFn(this.refresh, this);
        $(window).on('resize', this._resizeHandler);
      },
      refresh: function() {
        var shimContainer = this.getRuntime().getContainer(),
            button = this.options.button,
            width = button.outerWidth ? button.outerWidth() : button.width(),
            height = button.outerHeight ? button.outerHeight() : button.height(),
            pos = button.offset();
        width && height && shimContainer.css({
          bottom: 'auto',
          right: 'auto',
          width: width + 'px',
          height: height + 'px'
        }).offset(pos);
      },
      enable: function() {
        var btn = this.options.button;
        btn.removeClass('webuploader-pick-disable');
        this.refresh();
      },
      disable: function() {
        var btn = this.options.button;
        this.getRuntime().getContainer().css({top: '-99999px'});
        btn.addClass('webuploader-pick-disable');
      },
      destroy: function() {
        var btn = this.options.button;
        $(window).off('resize', this._resizeHandler);
        btn.removeClass('webuploader-pick-disable webuploader-pick-hover ' + 'webuploader-pick');
      }
    });
    return FilePicker;
  });
  define('widgets/filepicker', ['base', 'uploader', 'lib/filepicker', 'widgets/widget'], function(Base, Uploader, FilePicker) {
    var $ = Base.$;
    $.extend(Uploader.options, {
      pick: null,
      accept: null
    });
    return Uploader.register({
      name: 'picker',
      init: function(opts) {
        this.pickers = [];
        return opts.pick && this.addBtn(opts.pick);
      },
      refresh: function() {
        $.each(this.pickers, function() {
          this.refresh();
        });
      },
      addBtn: function(pick) {
        var me = this,
            opts = me.options,
            accept = opts.accept,
            promises = [];
        if (!pick) {
          return;
        }
        $.isPlainObject(pick) || (pick = {id: pick});
        $(pick.id).each(function() {
          var options,
              picker,
              deferred;
          deferred = Base.Deferred();
          options = $.extend({}, pick, {
            accept: $.isPlainObject(accept) ? [accept] : accept,
            swf: opts.swf,
            runtimeOrder: opts.runtimeOrder,
            id: this
          });
          picker = new FilePicker(options);
          picker.once('ready', deferred.resolve);
          picker.on('select', function(files) {
            me.owner.request('add-file', [files]);
          });
          picker.init();
          me.pickers.push(picker);
          promises.push(deferred.promise());
        });
        return Base.when.apply(Base, promises);
      },
      disable: function() {
        $.each(this.pickers, function() {
          this.disable();
        });
      },
      enable: function() {
        $.each(this.pickers, function() {
          this.enable();
        });
      },
      destroy: function() {
        $.each(this.pickers, function() {
          this.destroy();
        });
        this.pickers = null;
      }
    });
  });
  define('file', ['base', 'mediator'], function(Base, Mediator) {
    var $ = Base.$,
        idPrefix = 'WU_FILE_',
        idSuffix = 0,
        rExt = /\.([^.]+)$/,
        statusMap = {};
    function gid() {
      return idPrefix + idSuffix++;
    }
    function WUFile(source) {
      this.name = source.name || 'Untitled';
      this.size = source.size || 0;
      this.type = source.type || 'application/octet-stream';
      this.lastModifiedDate = source.lastModifiedDate || (new Date() * 1);
      this.id = gid();
      this.ext = rExt.exec(this.name) ? RegExp.$1 : '';
      this.statusText = '';
      statusMap[this.id] = WUFile.Status.INITED;
      this.source = source;
      this.loaded = 0;
      this.on('error', function(msg) {
        this.setStatus(WUFile.Status.ERROR, msg);
      });
    }
    $.extend(WUFile.prototype, {
      setStatus: function(status, text) {
        var prevStatus = statusMap[this.id];
        typeof text !== 'undefined' && (this.statusText = text);
        if (status !== prevStatus) {
          statusMap[this.id] = status;
          this.trigger('statuschange', status, prevStatus);
        }
      },
      getStatus: function() {
        return statusMap[this.id];
      },
      getSource: function() {
        return this.source;
      },
      destroy: function() {
        this.off();
        delete statusMap[this.id];
      }
    });
    Mediator.installTo(WUFile.prototype);
    WUFile.Status = {
      INITED: 'inited',
      QUEUED: 'queued',
      PROGRESS: 'progress',
      ERROR: 'error',
      COMPLETE: 'complete',
      CANCELLED: 'cancelled',
      INTERRUPT: 'interrupt',
      INVALID: 'invalid'
    };
    return WUFile;
  });
  define('queue', ['base', 'mediator', 'file'], function(Base, Mediator, WUFile) {
    var $ = Base.$,
        STATUS = WUFile.Status;
    function Queue() {
      this.stats = {
        numOfQueue: 0,
        numOfSuccess: 0,
        numOfCancel: 0,
        numOfProgress: 0,
        numOfUploadFailed: 0,
        numOfInvalid: 0,
        numofDeleted: 0,
        numofInterrupt: 0
      };
      this._queue = [];
      this._map = {};
    }
    $.extend(Queue.prototype, {
      append: function(file) {
        this._queue.push(file);
        this._fileAdded(file);
        return this;
      },
      prepend: function(file) {
        this._queue.unshift(file);
        this._fileAdded(file);
        return this;
      },
      getFile: function(fileId) {
        if (typeof fileId !== 'string') {
          return fileId;
        }
        return this._map[fileId];
      },
      fetch: function(status) {
        var len = this._queue.length,
            i,
            file;
        status = status || STATUS.QUEUED;
        for (i = 0; i < len; i++) {
          file = this._queue[i];
          if (status === file.getStatus()) {
            return file;
          }
        }
        return null;
      },
      sort: function(fn) {
        if (typeof fn === 'function') {
          this._queue.sort(fn);
        }
      },
      getFiles: function() {
        var sts = [].slice.call(arguments, 0),
            ret = [],
            i = 0,
            len = this._queue.length,
            file;
        for (; i < len; i++) {
          file = this._queue[i];
          if (sts.length && !~$.inArray(file.getStatus(), sts)) {
            continue;
          }
          ret.push(file);
        }
        return ret;
      },
      removeFile: function(file) {
        var me = this,
            existing = this._map[file.id];
        if (existing) {
          delete this._map[file.id];
          file.destroy();
          this.stats.numofDeleted++;
        }
      },
      _fileAdded: function(file) {
        var me = this,
            existing = this._map[file.id];
        if (!existing) {
          this._map[file.id] = file;
          file.on('statuschange', function(cur, pre) {
            me._onFileStatusChange(cur, pre);
          });
        }
      },
      _onFileStatusChange: function(curStatus, preStatus) {
        var stats = this.stats;
        switch (preStatus) {
          case STATUS.PROGRESS:
            stats.numOfProgress--;
            break;
          case STATUS.QUEUED:
            stats.numOfQueue--;
            break;
          case STATUS.ERROR:
            stats.numOfUploadFailed--;
            break;
          case STATUS.INVALID:
            stats.numOfInvalid--;
            break;
          case STATUS.INTERRUPT:
            stats.numofInterrupt--;
            break;
        }
        switch (curStatus) {
          case STATUS.QUEUED:
            stats.numOfQueue++;
            break;
          case STATUS.PROGRESS:
            stats.numOfProgress++;
            break;
          case STATUS.ERROR:
            stats.numOfUploadFailed++;
            break;
          case STATUS.COMPLETE:
            stats.numOfSuccess++;
            break;
          case STATUS.CANCELLED:
            stats.numOfCancel++;
            break;
          case STATUS.INVALID:
            stats.numOfInvalid++;
            break;
          case STATUS.INTERRUPT:
            stats.numofInterrupt++;
            break;
        }
      }
    });
    Mediator.installTo(Queue.prototype);
    return Queue;
  });
  define('widgets/queue', ['base', 'uploader', 'queue', 'file', 'lib/file', 'runtime/client', 'widgets/widget'], function(Base, Uploader, Queue, WUFile, File, RuntimeClient) {
    var $ = Base.$,
        rExt = /\.\w+$/,
        Status = WUFile.Status;
    return Uploader.register({
      name: 'queue',
      init: function(opts) {
        var me = this,
            deferred,
            len,
            i,
            item,
            arr,
            accept,
            runtime;
        if ($.isPlainObject(opts.accept)) {
          opts.accept = [opts.accept];
        }
        if (opts.accept) {
          arr = [];
          for (i = 0, len = opts.accept.length; i < len; i++) {
            item = opts.accept[i].extensions;
            item && arr.push(item);
          }
          if (arr.length) {
            accept = '\\.' + arr.join(',').replace(/,/g, '$|\\.').replace(/\*/g, '.*') + '$';
          }
          me.accept = new RegExp(accept, 'i');
        }
        me.queue = new Queue();
        me.stats = me.queue.stats;
        if (this.request('predict-runtime-type') !== 'html5') {
          return;
        }
        deferred = Base.Deferred();
        this.placeholder = runtime = new RuntimeClient('Placeholder');
        runtime.connectRuntime({runtimeOrder: 'html5'}, function() {
          me._ruid = runtime.getRuid();
          deferred.resolve();
        });
        return deferred.promise();
      },
      _wrapFile: function(file) {
        if (!(file instanceof WUFile)) {
          if (!(file instanceof File)) {
            if (!this._ruid) {
              throw new Error('Can\'t add external files.');
            }
            file = new File(this._ruid, file);
          }
          file = new WUFile(file);
        }
        return file;
      },
      acceptFile: function(file) {
        var invalid = !file || !file.size || this.accept && rExt.exec(file.name) && !this.accept.test(file.name);
        return !invalid;
      },
      _addFile: function(file) {
        var me = this;
        file = me._wrapFile(file);
        if (!me.owner.trigger('beforeFileQueued', file)) {
          return;
        }
        if (!me.acceptFile(file)) {
          me.owner.trigger('error', 'Q_TYPE_DENIED', file);
          return;
        }
        me.queue.append(file);
        me.owner.trigger('fileQueued', file);
        return file;
      },
      getFile: function(fileId) {
        return this.queue.getFile(fileId);
      },
      addFile: function(files) {
        var me = this;
        if (!files.length) {
          files = [files];
        }
        files = $.map(files, function(file) {
          return me._addFile(file);
        });
        me.owner.trigger('filesQueued', files);
        if (me.options.auto) {
          setTimeout(function() {
            me.request('start-upload');
          }, 20);
        }
      },
      getStats: function() {
        return this.stats;
      },
      removeFile: function(file, remove) {
        var me = this;
        file = file.id ? file : me.queue.getFile(file);
        this.request('cancel-file', file);
        if (remove) {
          this.queue.removeFile(file);
        }
      },
      getFiles: function() {
        return this.queue.getFiles.apply(this.queue, arguments);
      },
      fetchFile: function() {
        return this.queue.fetch.apply(this.queue, arguments);
      },
      retry: function(file, noForceStart) {
        var me = this,
            files,
            i,
            len;
        if (file) {
          file = file.id ? file : me.queue.getFile(file);
          file.setStatus(Status.QUEUED);
          noForceStart || me.request('start-upload');
          return;
        }
        files = me.queue.getFiles(Status.ERROR);
        i = 0;
        len = files.length;
        for (; i < len; i++) {
          file = files[i];
          file.setStatus(Status.QUEUED);
        }
        me.request('start-upload');
      },
      sortFiles: function() {
        return this.queue.sort.apply(this.queue, arguments);
      },
      reset: function() {
        this.owner.trigger('reset');
        this.queue = new Queue();
        this.stats = this.queue.stats;
      },
      destroy: function() {
        this.reset();
        this.placeholder && this.placeholder.destroy();
      }
    });
  });
  define('widgets/runtime', ['uploader', 'runtime/runtime', 'widgets/widget'], function(Uploader, Runtime) {
    Uploader.support = function() {
      return Runtime.hasRuntime.apply(Runtime, arguments);
    };
    return Uploader.register({
      name: 'runtime',
      init: function() {
        if (!this.predictRuntimeType()) {
          throw Error('Runtime Error');
        }
      },
      predictRuntimeType: function() {
        var orders = this.options.runtimeOrder || Runtime.orders,
            type = this.type,
            i,
            len;
        if (!type) {
          orders = orders.split(/\s*,\s*/g);
          for (i = 0, len = orders.length; i < len; i++) {
            if (Runtime.hasRuntime(orders[i])) {
              this.type = type = orders[i];
              break;
            }
          }
        }
        return type;
      }
    });
  });
  define('lib/transport', ['base', 'runtime/client', 'mediator'], function(Base, RuntimeClient, Mediator) {
    var $ = Base.$;
    function Transport(opts) {
      var me = this;
      opts = me.options = $.extend(true, {}, Transport.options, opts || {});
      RuntimeClient.call(this, 'Transport');
      this._blob = null;
      this._formData = opts.formData || {};
      this._headers = opts.headers || {};
      this.on('progress', this._timeout);
      this.on('load error', function() {
        me.trigger('progress', 1);
        clearTimeout(me._timer);
      });
    }
    Transport.options = {
      server: '',
      method: 'POST',
      withCredentials: false,
      fileVal: 'file',
      timeout: 2 * 60 * 1000,
      formData: {},
      headers: {},
      sendAsBinary: false
    };
    $.extend(Transport.prototype, {
      appendBlob: function(key, blob, filename) {
        var me = this,
            opts = me.options;
        if (me.getRuid()) {
          me.disconnectRuntime();
        }
        me.connectRuntime(blob.ruid, function() {
          me.exec('init');
        });
        me._blob = blob;
        opts.fileVal = key || opts.fileVal;
        opts.filename = filename || opts.filename;
      },
      append: function(key, value) {
        if (typeof key === 'object') {
          $.extend(this._formData, key);
        } else {
          this._formData[key] = value;
        }
      },
      setRequestHeader: function(key, value) {
        if (typeof key === 'object') {
          $.extend(this._headers, key);
        } else {
          this._headers[key] = value;
        }
      },
      send: function(method) {
        this.exec('send', method);
        this._timeout();
      },
      abort: function() {
        clearTimeout(this._timer);
        return this.exec('abort');
      },
      destroy: function() {
        this.trigger('destroy');
        this.off();
        this.exec('destroy');
        this.disconnectRuntime();
      },
      getResponse: function() {
        return this.exec('getResponse');
      },
      getResponseAsJson: function() {
        return this.exec('getResponseAsJson');
      },
      getStatus: function() {
        return this.exec('getStatus');
      },
      _timeout: function() {
        var me = this,
            duration = me.options.timeout;
        if (!duration) {
          return;
        }
        clearTimeout(me._timer);
        me._timer = setTimeout(function() {
          me.abort();
          me.trigger('error', 'timeout');
        }, duration);
      }
    });
    Mediator.installTo(Transport.prototype);
    return Transport;
  });
  define('widgets/upload', ['base', 'uploader', 'file', 'lib/transport', 'widgets/widget'], function(Base, Uploader, WUFile, Transport) {
    var $ = Base.$,
        isPromise = Base.isPromise,
        Status = WUFile.Status;
    $.extend(Uploader.options, {
      prepareNextFile: false,
      chunked: false,
      chunkSize: 5 * 1024 * 1024,
      chunkRetry: 2,
      threads: 3,
      formData: {}
    });
    function CuteFile(file, chunkSize) {
      var pending = [],
          blob = file.source,
          total = blob.size,
          chunks = chunkSize ? Math.ceil(total / chunkSize) : 1,
          start = 0,
          index = 0,
          len,
          api;
      api = {
        file: file,
        has: function() {
          return !!pending.length;
        },
        shift: function() {
          return pending.shift();
        },
        unshift: function(block) {
          pending.unshift(block);
        }
      };
      while (index < chunks) {
        len = Math.min(chunkSize, total - start);
        pending.push({
          file: file,
          start: start,
          end: chunkSize ? (start + len) : total,
          total: total,
          chunks: chunks,
          chunk: index++,
          cuted: api
        });
        start += len;
      }
      file.blocks = pending.concat();
      file.remaning = pending.length;
      return api;
    }
    Uploader.register({
      name: 'upload',
      init: function() {
        var owner = this.owner,
            me = this;
        this.runing = false;
        this.progress = false;
        owner.on('startUpload', function() {
          me.progress = true;
        }).on('uploadFinished', function() {
          me.progress = false;
        });
        this.pool = [];
        this.stack = [];
        this.pending = [];
        this.remaning = 0;
        this.__tick = Base.bindFn(this._tick, this);
        owner.on('uploadComplete', function(file) {
          file.blocks && $.each(file.blocks, function(_, v) {
            v.transport && (v.transport.abort(), v.transport.destroy());
            delete v.transport;
          });
          delete file.blocks;
          delete file.remaning;
        });
      },
      reset: function() {
        this.request('stop-upload', true);
        this.runing = false;
        this.pool = [];
        this.stack = [];
        this.pending = [];
        this.remaning = 0;
        this._trigged = false;
        this._promise = null;
      },
      startUpload: function(file) {
        var me = this;
        $.each(me.request('get-files', Status.INVALID), function() {
          me.request('remove-file', this);
        });
        if (file) {
          file = file.id ? file : me.request('get-file', file);
          if (file.getStatus() === Status.INTERRUPT) {
            $.each(me.pool, function(_, v) {
              if (v.file !== file) {
                return;
              }
              v.transport && v.transport.send();
            });
            file.setStatus(Status.QUEUED);
          } else if (file.getStatus() === Status.PROGRESS) {
            return;
          } else {
            file.setStatus(Status.QUEUED);
          }
        } else {
          $.each(me.request('get-files', [Status.INITED]), function() {
            this.setStatus(Status.QUEUED);
          });
        }
        if (me.runing) {
          return;
        }
        me.runing = true;
        $.each(me.pool, function(_, v) {
          var file = v.file;
          if (file.getStatus() === Status.INTERRUPT) {
            file.setStatus(Status.PROGRESS);
            me._trigged = false;
            v.transport && v.transport.send();
          }
        });
        file || $.each(me.request('get-files', Status.INTERRUPT), function() {
          this.setStatus(Status.PROGRESS);
        });
        me._trigged = false;
        Base.nextTick(me.__tick);
        me.owner.trigger('startUpload');
      },
      stopUpload: function(file, interrupt) {
        var me = this;
        if (file === true) {
          interrupt = file;
          file = null;
        }
        if (me.runing === false) {
          return;
        }
        if (file) {
          file = file.id ? file : me.request('get-file', file);
          if (file.getStatus() !== Status.PROGRESS && file.getStatus() !== Status.QUEUED) {
            return;
          }
          file.setStatus(Status.INTERRUPT);
          $.each(me.pool, function(_, v) {
            if (v.file !== file) {
              return;
            }
            v.transport && v.transport.abort();
            me._putback(v);
            me._popBlock(v);
          });
          return Base.nextTick(me.__tick);
        }
        me.runing = false;
        if (this._promise && this._promise.file) {
          this._promise.file.setStatus(Status.INTERRUPT);
        }
        interrupt && $.each(me.pool, function(_, v) {
          v.transport && v.transport.abort();
          v.file.setStatus(Status.INTERRUPT);
        });
        me.owner.trigger('stopUpload');
      },
      cancelFile: function(file) {
        file = file.id ? file : this.request('get-file', file);
        file.blocks && $.each(file.blocks, function(_, v) {
          var _tr = v.transport;
          if (_tr) {
            _tr.abort();
            _tr.destroy();
            delete v.transport;
          }
        });
        file.setStatus(Status.CANCELLED);
        this.owner.trigger('fileDequeued', file);
      },
      isInProgress: function() {
        return !!this.progress;
      },
      _getStats: function() {
        return this.request('get-stats');
      },
      skipFile: function(file, status) {
        file = file.id ? file : this.request('get-file', file);
        file.setStatus(status || Status.COMPLETE);
        file.skipped = true;
        file.blocks && $.each(file.blocks, function(_, v) {
          var _tr = v.transport;
          if (_tr) {
            _tr.abort();
            _tr.destroy();
            delete v.transport;
          }
        });
        this.owner.trigger('uploadSkip', file);
      },
      _tick: function() {
        var me = this,
            opts = me.options,
            fn,
            val;
        if (me._promise) {
          return me._promise.always(me.__tick);
        }
        if (me.pool.length < opts.threads && (val = me._nextBlock())) {
          me._trigged = false;
          fn = function(val) {
            me._promise = null;
            val && val.file && me._startSend(val);
            Base.nextTick(me.__tick);
          };
          me._promise = isPromise(val) ? val.always(fn) : fn(val);
        } else if (!me.remaning && !me._getStats().numOfQueue && !me._getStats().numofInterrupt) {
          me.runing = false;
          me._trigged || Base.nextTick(function() {
            me.owner.trigger('uploadFinished');
          });
          me._trigged = true;
        }
      },
      _putback: function(block) {
        var idx;
        block.cuted.unshift(block);
        idx = this.stack.indexOf(block.cuted);
        if (!~idx) {
          this.stack.unshift(block.cuted);
        }
      },
      _getStack: function() {
        var i = 0,
            act;
        while ((act = this.stack[i++])) {
          if (act.has() && act.file.getStatus() === Status.PROGRESS) {
            return act;
          } else if (!act.has() || act.file.getStatus() !== Status.PROGRESS && act.file.getStatus() !== Status.INTERRUPT) {
            this.stack.splice(--i, 1);
          }
        }
        return null;
      },
      _nextBlock: function() {
        var me = this,
            opts = me.options,
            act,
            next,
            done,
            preparing;
        if ((act = this._getStack())) {
          if (opts.prepareNextFile && !me.pending.length) {
            me._prepareNextFile();
          }
          return act.shift();
        } else if (me.runing) {
          if (!me.pending.length && me._getStats().numOfQueue) {
            me._prepareNextFile();
          }
          next = me.pending.shift();
          done = function(file) {
            if (!file) {
              return null;
            }
            act = CuteFile(file, opts.chunked ? opts.chunkSize : 0);
            me.stack.push(act);
            return act.shift();
          };
          if (isPromise(next)) {
            preparing = next.file;
            next = next[next.pipe ? 'pipe' : 'then'](done);
            next.file = preparing;
            return next;
          }
          return done(next);
        }
      },
      _prepareNextFile: function() {
        var me = this,
            file = me.request('fetch-file'),
            pending = me.pending,
            promise;
        if (file) {
          promise = me.request('before-send-file', file, function() {
            if (file.getStatus() === Status.PROGRESS || file.getStatus() === Status.INTERRUPT) {
              return file;
            }
            return me._finishFile(file);
          });
          me.owner.trigger('uploadStart', file);
          file.setStatus(Status.PROGRESS);
          promise.file = file;
          promise.done(function() {
            var idx = $.inArray(promise, pending);
            ~idx && pending.splice(idx, 1, file);
          });
          promise.fail(function(reason) {
            file.setStatus(Status.ERROR, reason);
            me.owner.trigger('uploadError', file, reason);
            me.owner.trigger('uploadComplete', file);
          });
          pending.push(promise);
        }
      },
      _popBlock: function(block) {
        var idx = $.inArray(block, this.pool);
        this.pool.splice(idx, 1);
        block.file.remaning--;
        this.remaning--;
      },
      _startSend: function(block) {
        var me = this,
            file = block.file,
            promise;
        if (file.getStatus() !== Status.PROGRESS) {
          if (file.getStatus() === Status.INTERRUPT) {
            me._putback(block);
          }
          return;
        }
        me.pool.push(block);
        me.remaning++;
        block.blob = block.chunks === 1 ? file.source : file.source.slice(block.start, block.end);
        promise = me.request('before-send', block, function() {
          if (file.getStatus() === Status.PROGRESS) {
            me._doSend(block);
          } else {
            me._popBlock(block);
            Base.nextTick(me.__tick);
          }
        });
        promise.fail(function() {
          if (file.remaning === 1) {
            me._finishFile(file).always(function() {
              block.percentage = 1;
              me._popBlock(block);
              me.owner.trigger('uploadComplete', file);
              Base.nextTick(me.__tick);
            });
          } else {
            block.percentage = 1;
            me._popBlock(block);
            Base.nextTick(me.__tick);
          }
        });
      },
      _doSend: function(block) {
        var me = this,
            owner = me.owner,
            opts = me.options,
            file = block.file,
            tr = new Transport(opts),
            data = $.extend({}, opts.formData),
            headers = $.extend({}, opts.headers),
            requestAccept,
            ret;
        block.transport = tr;
        tr.on('destroy', function() {
          delete block.transport;
          me._popBlock(block);
          Base.nextTick(me.__tick);
        });
        tr.on('progress', function(percentage) {
          var totalPercent = 0,
              uploaded = 0;
          totalPercent = block.percentage = percentage;
          if (block.chunks > 1) {
            $.each(file.blocks, function(_, v) {
              uploaded += (v.percentage || 0) * (v.end - v.start);
            });
            totalPercent = uploaded / file.size;
          }
          owner.trigger('uploadProgress', file, totalPercent || 0);
        });
        requestAccept = function(reject) {
          var fn;
          ret = tr.getResponseAsJson() || {};
          ret._raw = tr.getResponse();
          fn = function(value) {
            reject = value;
          };
          if (!owner.trigger('uploadAccept', block, ret, fn)) {
            reject = reject || 'server';
          }
          return reject;
        };
        tr.on('error', function(type, flag) {
          block.retried = block.retried || 0;
          if (block.chunks > 1 && ~'http,abort'.indexOf(type) && block.retried < opts.chunkRetry) {
            block.retried++;
            tr.send();
          } else {
            if (!flag && type === 'server') {
              type = requestAccept(type);
            }
            file.setStatus(Status.ERROR, type);
            owner.trigger('uploadError', file, type);
            owner.trigger('uploadComplete', file);
          }
        });
        tr.on('load', function() {
          var reason;
          if ((reason = requestAccept())) {
            tr.trigger('error', reason, true);
            return;
          }
          if (file.remaning === 1) {
            me._finishFile(file, ret);
          } else {
            tr.destroy();
          }
        });
        data = $.extend(data, {
          id: file.id,
          name: file.name,
          type: file.type,
          lastModifiedDate: file.lastModifiedDate,
          size: file.size
        });
        block.chunks > 1 && $.extend(data, {
          chunks: block.chunks,
          chunk: block.chunk
        });
        owner.trigger('uploadBeforeSend', block, data, headers);
        tr.appendBlob(opts.fileVal, block.blob, file.name);
        tr.append(data);
        tr.setRequestHeader(headers);
        tr.send();
      },
      _finishFile: function(file, ret, hds) {
        var owner = this.owner;
        return owner.request('after-send-file', arguments, function() {
          file.setStatus(Status.COMPLETE);
          owner.trigger('uploadSuccess', file, ret, hds);
        }).fail(function(reason) {
          if (file.getStatus() === Status.PROGRESS) {
            file.setStatus(Status.ERROR, reason);
          }
          owner.trigger('uploadError', file, reason);
        }).always(function() {
          owner.trigger('uploadComplete', file);
        });
      }
    });
  });
  define('widgets/validator', ['base', 'uploader', 'file', 'widgets/widget'], function(Base, Uploader, WUFile) {
    var $ = Base.$,
        validators = {},
        api;
    api = {
      addValidator: function(type, cb) {
        validators[type] = cb;
      },
      removeValidator: function(type) {
        delete validators[type];
      }
    };
    Uploader.register({
      name: 'validator',
      init: function() {
        var me = this;
        Base.nextTick(function() {
          $.each(validators, function() {
            this.call(me.owner);
          });
        });
      }
    });
    api.addValidator('fileNumLimit', function() {
      var uploader = this,
          opts = uploader.options,
          count = 0,
          max = parseInt(opts.fileNumLimit, 10),
          flag = true;
      if (!max) {
        return;
      }
      uploader.on('beforeFileQueued', function(file) {
        if (count >= max && flag) {
          flag = false;
          this.trigger('error', 'Q_EXCEED_NUM_LIMIT', max, file);
          setTimeout(function() {
            flag = true;
          }, 1);
        }
        return count >= max ? false : true;
      });
      uploader.on('fileQueued', function() {
        count++;
      });
      uploader.on('fileDequeued', function() {
        count--;
      });
      uploader.on('reset', function() {
        count = 0;
      });
    });
    api.addValidator('fileSizeLimit', function() {
      var uploader = this,
          opts = uploader.options,
          count = 0,
          max = parseInt(opts.fileSizeLimit, 10),
          flag = true;
      if (!max) {
        return;
      }
      uploader.on('beforeFileQueued', function(file) {
        var invalid = count + file.size > max;
        if (invalid && flag) {
          flag = false;
          this.trigger('error', 'Q_EXCEED_SIZE_LIMIT', max, file);
          setTimeout(function() {
            flag = true;
          }, 1);
        }
        return invalid ? false : true;
      });
      uploader.on('fileQueued', function(file) {
        count += file.size;
      });
      uploader.on('fileDequeued', function(file) {
        count -= file.size;
      });
      uploader.on('reset', function() {
        count = 0;
      });
    });
    api.addValidator('fileSingleSizeLimit', function() {
      var uploader = this,
          opts = uploader.options,
          max = opts.fileSingleSizeLimit;
      if (!max) {
        return;
      }
      uploader.on('beforeFileQueued', function(file) {
        if (file.size > max) {
          file.setStatus(WUFile.Status.INVALID, 'exceed_size');
          this.trigger('error', 'F_EXCEED_SIZE', max, file);
          return false;
        }
      });
    });
    api.addValidator('duplicate', function() {
      var uploader = this,
          opts = uploader.options,
          mapping = {};
      if (opts.duplicate) {
        return;
      }
      function hashString(str) {
        var hash = 0,
            i = 0,
            len = str.length,
            _char;
        for (; i < len; i++) {
          _char = str.charCodeAt(i);
          hash = _char + (hash << 6) + (hash << 16) - hash;
        }
        return hash;
      }
      uploader.on('beforeFileQueued', function(file) {
        var hash = file.__hash || (file.__hash = hashString(file.name + file.size + file.lastModifiedDate));
        if (mapping[hash]) {
          this.trigger('error', 'F_DUPLICATE', file);
          return false;
        }
      });
      uploader.on('fileQueued', function(file) {
        var hash = file.__hash;
        hash && (mapping[hash] = true);
      });
      uploader.on('fileDequeued', function(file) {
        var hash = file.__hash;
        hash && (delete mapping[hash]);
      });
      uploader.on('reset', function() {
        mapping = {};
      });
    });
    return api;
  });
  define('runtime/compbase', [], function() {
    function CompBase(owner, runtime) {
      this.owner = owner;
      this.options = owner.options;
      this.getRuntime = function() {
        return runtime;
      };
      this.getRuid = function() {
        return runtime.uid;
      };
      this.trigger = function() {
        return owner.trigger.apply(owner, arguments);
      };
    }
    return CompBase;
  });
  define('runtime/html5/runtime', ['base', 'runtime/runtime', 'runtime/compbase'], function(Base, Runtime, CompBase) {
    var type = 'html5',
        components = {};
    function Html5Runtime() {
      var pool = {},
          me = this,
          destroy = this.destroy;
      Runtime.apply(me, arguments);
      me.type = type;
      me.exec = function(comp, fn) {
        var client = this,
            uid = client.uid,
            args = Base.slice(arguments, 2),
            instance;
        if (components[comp]) {
          instance = pool[uid] = pool[uid] || new components[comp](client, me);
          if (instance[fn]) {
            return instance[fn].apply(instance, args);
          }
        }
      };
      me.destroy = function() {
        return destroy && destroy.apply(this, arguments);
      };
    }
    Base.inherits(Runtime, {
      constructor: Html5Runtime,
      init: function() {
        var me = this;
        setTimeout(function() {
          me.trigger('ready');
        }, 1);
      }
    });
    Html5Runtime.register = function(name, component) {
      var klass = components[name] = Base.inherits(CompBase, component);
      return klass;
    };
    if (window.Blob && window.FileReader && window.DataView) {
      Runtime.addRuntime(type, Html5Runtime);
    }
    return Html5Runtime;
  });
  define('runtime/html5/blob', ['runtime/html5/runtime', 'lib/blob'], function(Html5Runtime, Blob) {
    return Html5Runtime.register('Blob', {slice: function(start, end) {
        var blob = this.owner.source,
            slice = blob.slice || blob.webkitSlice || blob.mozSlice;
        blob = slice.call(blob, start, end);
        return new Blob(this.getRuid(), blob);
      }});
  });
  define('runtime/html5/dnd', ['base', 'runtime/html5/runtime', 'lib/file'], function(Base, Html5Runtime, File) {
    var $ = Base.$,
        prefix = 'webuploader-dnd-';
    return Html5Runtime.register('DragAndDrop', {
      init: function() {
        var elem = this.elem = this.options.container;
        this.dragEnterHandler = Base.bindFn(this._dragEnterHandler, this);
        this.dragOverHandler = Base.bindFn(this._dragOverHandler, this);
        this.dragLeaveHandler = Base.bindFn(this._dragLeaveHandler, this);
        this.dropHandler = Base.bindFn(this._dropHandler, this);
        this.dndOver = false;
        elem.on('dragenter', this.dragEnterHandler);
        elem.on('dragover', this.dragOverHandler);
        elem.on('dragleave', this.dragLeaveHandler);
        elem.on('drop', this.dropHandler);
        if (this.options.disableGlobalDnd) {
          $(document).on('dragover', this.dragOverHandler);
          $(document).on('drop', this.dropHandler);
        }
      },
      _dragEnterHandler: function(e) {
        var me = this,
            denied = me._denied || false,
            items;
        e = e.originalEvent || e;
        if (!me.dndOver) {
          me.dndOver = true;
          items = e.dataTransfer.items;
          if (items && items.length) {
            me._denied = denied = !me.trigger('accept', items);
          }
          me.elem.addClass(prefix + 'over');
          me.elem[denied ? 'addClass' : 'removeClass'](prefix + 'denied');
        }
        e.dataTransfer.dropEffect = denied ? 'none' : 'copy';
        return false;
      },
      _dragOverHandler: function(e) {
        var parentElem = this.elem.parent().get(0);
        if (parentElem && !$.contains(parentElem, e.currentTarget)) {
          return false;
        }
        clearTimeout(this._leaveTimer);
        this._dragEnterHandler.call(this, e);
        return false;
      },
      _dragLeaveHandler: function() {
        var me = this,
            handler;
        handler = function() {
          me.dndOver = false;
          me.elem.removeClass(prefix + 'over ' + prefix + 'denied');
        };
        clearTimeout(me._leaveTimer);
        me._leaveTimer = setTimeout(handler, 100);
        return false;
      },
      _dropHandler: function(e) {
        var me = this,
            ruid = me.getRuid(),
            parentElem = me.elem.parent().get(0),
            dataTransfer,
            data;
        if (parentElem && !$.contains(parentElem, e.currentTarget)) {
          return false;
        }
        e = e.originalEvent || e;
        dataTransfer = e.dataTransfer;
        try {
          data = dataTransfer.getData('text/html');
        } catch (err) {}
        if (data) {
          return;
        }
        me._getTansferFiles(dataTransfer, function(results) {
          me.trigger('drop', $.map(results, function(file) {
            return new File(ruid, file);
          }));
        });
        me.dndOver = false;
        me.elem.removeClass(prefix + 'over');
        return false;
      },
      _getTansferFiles: function(dataTransfer, callback) {
        var results = [],
            promises = [],
            items,
            files,
            file,
            item,
            i,
            len,
            canAccessFolder;
        items = dataTransfer.items;
        files = dataTransfer.files;
        canAccessFolder = !!(items && items[0].webkitGetAsEntry);
        for (i = 0, len = files.length; i < len; i++) {
          file = files[i];
          item = items && items[i];
          if (canAccessFolder && item.webkitGetAsEntry().isDirectory) {
            promises.push(this._traverseDirectoryTree(item.webkitGetAsEntry(), results));
          } else {
            results.push(file);
          }
        }
        Base.when.apply(Base, promises).done(function() {
          if (!results.length) {
            return;
          }
          callback(results);
        });
      },
      _traverseDirectoryTree: function(entry, results) {
        var deferred = Base.Deferred(),
            me = this;
        if (entry.isFile) {
          entry.file(function(file) {
            results.push(file);
            deferred.resolve();
          });
        } else if (entry.isDirectory) {
          entry.createReader().readEntries(function(entries) {
            var len = entries.length,
                promises = [],
                arr = [],
                i;
            for (i = 0; i < len; i++) {
              promises.push(me._traverseDirectoryTree(entries[i], arr));
            }
            Base.when.apply(Base, promises).then(function() {
              results.push.apply(results, arr);
              deferred.resolve();
            }, deferred.reject);
          });
        }
        return deferred.promise();
      },
      destroy: function() {
        var elem = this.elem;
        if (!elem) {
          return;
        }
        elem.off('dragenter', this.dragEnterHandler);
        elem.off('dragover', this.dragOverHandler);
        elem.off('dragleave', this.dragLeaveHandler);
        elem.off('drop', this.dropHandler);
        if (this.options.disableGlobalDnd) {
          $(document).off('dragover', this.dragOverHandler);
          $(document).off('drop', this.dropHandler);
        }
      }
    });
  });
  define('runtime/html5/filepaste', ['base', 'runtime/html5/runtime', 'lib/file'], function(Base, Html5Runtime, File) {
    return Html5Runtime.register('FilePaste', {
      init: function() {
        var opts = this.options,
            elem = this.elem = opts.container,
            accept = '.*',
            arr,
            i,
            len,
            item;
        if (opts.accept) {
          arr = [];
          for (i = 0, len = opts.accept.length; i < len; i++) {
            item = opts.accept[i].mimeTypes;
            item && arr.push(item);
          }
          if (arr.length) {
            accept = arr.join(',');
            accept = accept.replace(/,/g, '|').replace(/\*/g, '.*');
          }
        }
        this.accept = accept = new RegExp(accept, 'i');
        this.hander = Base.bindFn(this._pasteHander, this);
        elem.on('paste', this.hander);
      },
      _pasteHander: function(e) {
        var allowed = [],
            ruid = this.getRuid(),
            items,
            item,
            blob,
            i,
            len;
        e = e.originalEvent || e;
        items = e.clipboardData.items;
        for (i = 0, len = items.length; i < len; i++) {
          item = items[i];
          if (item.kind !== 'file' || !(blob = item.getAsFile())) {
            continue;
          }
          allowed.push(new File(ruid, blob));
        }
        if (allowed.length) {
          e.preventDefault();
          e.stopPropagation();
          this.trigger('paste', allowed);
        }
      },
      destroy: function() {
        this.elem.off('paste', this.hander);
      }
    });
  });
  define('runtime/html5/filepicker', ['base', 'runtime/html5/runtime'], function(Base, Html5Runtime) {
    var $ = Base.$;
    return Html5Runtime.register('FilePicker', {
      init: function() {
        var container = this.getRuntime().getContainer(),
            me = this,
            owner = me.owner,
            opts = me.options,
            label = this.label = $(document.createElement('label')),
            input = this.input = $(document.createElement('input')),
            arr,
            i,
            len,
            mouseHandler;
        input.attr('type', 'file');
        input.attr('name', opts.name);
        input.addClass('webuploader-element-invisible');
        label.on('click', function() {
          input.trigger('click');
        });
        label.css({
          opacity: 0,
          width: '100%',
          height: '100%',
          display: 'block',
          cursor: 'pointer',
          background: '#ffffff'
        });
        if (opts.multiple) {
          input.attr('multiple', 'multiple');
        }
        if (opts.accept && opts.accept.length > 0) {
          arr = [];
          for (i = 0, len = opts.accept.length; i < len; i++) {
            arr.push(opts.accept[i].mimeTypes);
          }
          input.attr('accept', arr.join(','));
        }
        container.append(input);
        container.append(label);
        mouseHandler = function(e) {
          owner.trigger(e.type);
        };
        input.on('change', function(e) {
          var fn = arguments.callee,
              clone;
          me.files = e.target.files;
          clone = this.cloneNode(true);
          clone.value = null;
          this.parentNode.replaceChild(clone, this);
          input.off();
          input = $(clone).on('change', fn).on('mouseenter mouseleave', mouseHandler);
          owner.trigger('change');
        });
        label.on('mouseenter mouseleave', mouseHandler);
      },
      getFiles: function() {
        return this.files;
      },
      destroy: function() {
        this.input.off();
        this.label.off();
      }
    });
  });
  define('runtime/html5/transport', ['base', 'runtime/html5/runtime'], function(Base, Html5Runtime) {
    var noop = Base.noop,
        $ = Base.$;
    return Html5Runtime.register('Transport', {
      init: function() {
        this._status = 0;
        this._response = null;
      },
      send: function() {
        var owner = this.owner,
            opts = this.options,
            xhr = this._initAjax(),
            blob = owner._blob,
            server = opts.server,
            formData,
            binary,
            fr;
        if (opts.sendAsBinary) {
          server += (/\?/.test(server) ? '&' : '?') + $.param(owner._formData);
          binary = blob.getSource();
        } else {
          formData = new FormData();
          $.each(owner._formData, function(k, v) {
            formData.append(k, v);
          });
          formData.append(opts.fileVal, blob.getSource(), opts.filename || owner._formData.name || '');
        }
        if (opts.withCredentials && 'withCredentials' in xhr) {
          xhr.open(opts.method, server, true);
          xhr.withCredentials = true;
        } else {
          xhr.open(opts.method, server);
        }
        this._setRequestHeader(xhr, opts.headers);
        if (binary) {
          xhr.overrideMimeType && xhr.overrideMimeType('application/octet-stream');
          if (Base.os.android) {
            fr = new FileReader();
            fr.onload = function() {
              xhr.send(this.result);
              fr = fr.onload = null;
            };
            fr.readAsArrayBuffer(binary);
          } else {
            xhr.send(binary);
          }
        } else {
          xhr.send(formData);
        }
      },
      getResponse: function() {
        return this._response;
      },
      getResponseAsJson: function() {
        return this._parseJson(this._response);
      },
      getStatus: function() {
        return this._status;
      },
      abort: function() {
        var xhr = this._xhr;
        if (xhr) {
          xhr.upload.onprogress = noop;
          xhr.onreadystatechange = noop;
          xhr.abort();
          this._xhr = xhr = null;
        }
      },
      destroy: function() {
        this.abort();
      },
      _initAjax: function() {
        var me = this,
            xhr = new XMLHttpRequest(),
            opts = this.options;
        if (opts.withCredentials && !('withCredentials' in xhr) && typeof XDomainRequest !== 'undefined') {
          xhr = new XDomainRequest();
        }
        xhr.upload.onprogress = function(e) {
          var percentage = 0;
          if (e.lengthComputable) {
            percentage = e.loaded / e.total;
          }
          return me.trigger('progress', percentage);
        };
        xhr.onreadystatechange = function() {
          if (xhr.readyState !== 4) {
            return;
          }
          xhr.upload.onprogress = noop;
          xhr.onreadystatechange = noop;
          me._xhr = null;
          me._status = xhr.status;
          if (xhr.status >= 200 && xhr.status < 300) {
            me._response = xhr.responseText;
            return me.trigger('load');
          } else if (xhr.status >= 500 && xhr.status < 600) {
            me._response = xhr.responseText;
            return me.trigger('error', 'server');
          }
          return me.trigger('error', me._status ? 'http' : 'abort');
        };
        me._xhr = xhr;
        return xhr;
      },
      _setRequestHeader: function(xhr, headers) {
        $.each(headers, function(key, val) {
          xhr.setRequestHeader(key, val);
        });
      },
      _parseJson: function(str) {
        var json;
        try {
          json = JSON.parse(str);
        } catch (ex) {
          json = {};
        }
        return json;
      }
    });
  });
  define('runtime/flash/runtime', ['base', 'runtime/runtime', 'runtime/compbase'], function(Base, Runtime, CompBase) {
    var $ = Base.$,
        type = 'flash',
        components = {};
    function getFlashVersion() {
      var version;
      try {
        version = navigator.plugins['Shockwave Flash'];
        version = version.description;
      } catch (ex) {
        try {
          version = new ActiveXObject('ShockwaveFlash.ShockwaveFlash').GetVariable('$version');
        } catch (ex2) {
          version = '0.0';
        }
      }
      version = version.match(/\d+/g);
      return parseFloat(version[0] + '.' + version[1], 10);
    }
    function FlashRuntime() {
      var pool = {},
          clients = {},
          destroy = this.destroy,
          me = this,
          jsreciver = Base.guid('webuploader_');
      Runtime.apply(me, arguments);
      me.type = type;
      me.exec = function(comp, fn) {
        var client = this,
            uid = client.uid,
            args = Base.slice(arguments, 2),
            instance;
        clients[uid] = client;
        if (components[comp]) {
          if (!pool[uid]) {
            pool[uid] = new components[comp](client, me);
          }
          instance = pool[uid];
          if (instance[fn]) {
            return instance[fn].apply(instance, args);
          }
        }
        return me.flashExec.apply(client, arguments);
      };
      function handler(evt, obj) {
        var type = evt.type || evt,
            parts,
            uid;
        parts = type.split('::');
        uid = parts[0];
        type = parts[1];
        if (type === 'Ready' && uid === me.uid) {
          me.trigger('ready');
        } else if (clients[uid]) {
          clients[uid].trigger(type.toLowerCase(), evt, obj);
        }
      }
      window[jsreciver] = function() {
        var args = arguments;
        setTimeout(function() {
          handler.apply(null, args);
        }, 1);
      };
      this.jsreciver = jsreciver;
      this.destroy = function() {
        return destroy && destroy.apply(this, arguments);
      };
      this.flashExec = function(comp, fn) {
        var flash = me.getFlash(),
            args = Base.slice(arguments, 2);
        return flash.exec(this.uid, comp, fn, args);
      };
    }
    Base.inherits(Runtime, {
      constructor: FlashRuntime,
      init: function() {
        var container = this.getContainer(),
            opts = this.options,
            html;
        container.css({
          position: 'absolute',
          top: '-8px',
          left: '-8px',
          width: '9px',
          height: '9px',
          overflow: 'hidden'
        });
        html = '<object id="' + this.uid + '" type="application/' + 'x-shockwave-flash" data="' + opts.swf + '" ';
        if (Base.browser.ie) {
          html += 'classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" ';
        }
        html += 'width="100%" height="100%" style="outline:0">' + '<param name="movie" value="' + opts.swf + '" />' + '<param name="flashvars" value="uid=' + this.uid + '&jsreciver=' + this.jsreciver + '" />' + '<param name="wmode" value="transparent" />' + '<param name="allowscriptaccess" value="always" />' + '</object>';
        container.html(html);
      },
      getFlash: function() {
        if (this._flash) {
          return this._flash;
        }
        this._flash = $('#' + this.uid).get(0);
        return this._flash;
      }
    });
    FlashRuntime.register = function(name, component) {
      component = components[name] = Base.inherits(CompBase, $.extend({flashExec: function() {
          var owner = this.owner,
              runtime = this.getRuntime();
          return runtime.flashExec.apply(owner, arguments);
        }}, component));
      return component;
    };
    if (getFlashVersion() >= 11.4) {
      Runtime.addRuntime(type, FlashRuntime);
    }
    return FlashRuntime;
  });
  define('runtime/flash/filepicker', ['base', 'runtime/flash/runtime'], function(Base, FlashRuntime) {
    var $ = Base.$;
    return FlashRuntime.register('FilePicker', {
      init: function(opts) {
        var copy = $.extend({}, opts),
            len,
            i;
        len = copy.accept && copy.accept.length;
        for (i = 0; i < len; i++) {
          if (!copy.accept[i].title) {
            copy.accept[i].title = 'Files';
          }
        }
        delete copy.button;
        delete copy.id;
        delete copy.container;
        this.flashExec('FilePicker', 'init', copy);
      },
      destroy: function() {
        this.flashExec('FilePicker', 'destroy');
      }
    });
  });
  define('runtime/flash/transport', ['base', 'runtime/flash/runtime', 'runtime/client'], function(Base, FlashRuntime, RuntimeClient) {
    var $ = Base.$;
    return FlashRuntime.register('Transport', {
      init: function() {
        this._status = 0;
        this._response = null;
        this._responseJson = null;
      },
      send: function() {
        var owner = this.owner,
            opts = this.options,
            xhr = this._initAjax(),
            blob = owner._blob,
            server = opts.server,
            binary;
        xhr.connectRuntime(blob.ruid);
        if (opts.sendAsBinary) {
          server += (/\?/.test(server) ? '&' : '?') + $.param(owner._formData);
          binary = blob.uid;
        } else {
          $.each(owner._formData, function(k, v) {
            xhr.exec('append', k, v);
          });
          xhr.exec('appendBlob', opts.fileVal, blob.uid, opts.filename || owner._formData.name || '');
        }
        this._setRequestHeader(xhr, opts.headers);
        xhr.exec('send', {
          method: opts.method,
          url: server,
          forceURLStream: opts.forceURLStream,
          mimeType: 'application/octet-stream'
        }, binary);
      },
      getStatus: function() {
        return this._status;
      },
      getResponse: function() {
        return this._response || '';
      },
      getResponseAsJson: function() {
        return this._responseJson;
      },
      abort: function() {
        var xhr = this._xhr;
        if (xhr) {
          xhr.exec('abort');
          xhr.destroy();
          this._xhr = xhr = null;
        }
      },
      destroy: function() {
        this.abort();
      },
      _initAjax: function() {
        var me = this,
            xhr = new RuntimeClient('XMLHttpRequest');
        xhr.on('uploadprogress progress', function(e) {
          var percent = e.loaded / e.total;
          percent = Math.min(1, Math.max(0, percent));
          return me.trigger('progress', percent);
        });
        xhr.on('load', function() {
          var status = xhr.exec('getStatus'),
              readBody = false,
              err = '',
              p;
          xhr.off();
          me._xhr = null;
          if (status >= 200 && status < 300) {
            readBody = true;
          } else if (status >= 500 && status < 600) {
            readBody = true;
            err = 'server';
          } else {
            err = 'http';
          }
          if (readBody) {
            me._response = xhr.exec('getResponse');
            me._response = decodeURIComponent(me._response);
            p = window.JSON && window.JSON.parse || function(s) {
              try {
                return new Function('return ' + s).call();
              } catch (err) {
                return {};
              }
            };
            me._responseJson = me._response ? p(me._response) : {};
          }
          xhr.destroy();
          xhr = null;
          return err ? me.trigger('error', err) : me.trigger('load');
        });
        xhr.on('error', function() {
          xhr.off();
          me._xhr = null;
          me.trigger('error', 'http');
        });
        me._xhr = xhr;
        return xhr;
      },
      _setRequestHeader: function(xhr, headers) {
        $.each(headers, function(key, val) {
          xhr.exec('setRequestHeader', key, val);
        });
      }
    });
  });
  define('preset/withoutimage', ['base', 'widgets/filednd', 'widgets/filepaste', 'widgets/filepicker', 'widgets/queue', 'widgets/runtime', 'widgets/upload', 'widgets/validator', 'runtime/html5/blob', 'runtime/html5/dnd', 'runtime/html5/filepaste', 'runtime/html5/filepicker', 'runtime/html5/transport', 'runtime/flash/filepicker', 'runtime/flash/transport'], function(Base) {
    return Base;
  });
  define('webuploader', ['preset/withoutimage'], function(preset) {
    return preset;
  });
  return require('./webuploader.fis');
});
