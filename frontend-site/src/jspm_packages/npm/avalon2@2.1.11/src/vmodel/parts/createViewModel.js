/* */ 
var canHideProperty = require('./canHideProperty');
var $$skipArray = require('./skipArray');
var defineProperties = Object.defineProperties;
var defineProperty;
var expose = new Date() - 0;
if (!canHideProperty) {
  if ('__defineGetter__' in avalon) {
    defineProperty = function(obj, prop, desc) {
      if ('value' in desc) {
        obj[prop] = desc.value;
      }
      if ('get' in desc) {
        obj.__defineGetter__(prop, desc.get);
      }
      if ('set' in desc) {
        obj.__defineSetter__(prop, desc.set);
      }
      return obj;
    };
    defineProperties = function(obj, descs) {
      for (var prop in descs) {
        if (descs.hasOwnProperty(prop)) {
          defineProperty(obj, prop, descs[prop]);
        }
      }
      return obj;
    };
  }
  if (avalon.msie) {
    var VBClassPool = {};
    window.execScript(['Function parseVB(code)', '\tExecuteGlobal(code)', 'End Function'].join('\n'), 'VBScript');
    function VBMediator(instance, accessors, name, value) {
      var accessor = accessors[name];
      if (arguments.length === 4) {
        accessor.set.call(instance, value);
      } else {
        return accessor.get.call(instance);
      }
    }
    defineProperties = function(name, accessors, properties) {
      var buffer = [];
      buffer.push('\r\n\tPrivate [__data__], [__proxy__]', '\tPublic Default Function [__const__](d' + expose + ', p' + expose + ')', '\t\tSet [__data__] = d' + expose + ': set [__proxy__] = p' + expose, '\t\tSet [__const__] = Me', '\tEnd Function');
      var uniq = {
        __proxy__: true,
        __data__: true,
        __const__: true
      };
      for (name in accessors) {
        uniq[name] = true;
        buffer.push('\tPublic Property Let [' + name + '](val' + expose + ')', '\t\tCall [__proxy__](Me,[__data__], "' + name + '", val' + expose + ')', '\tEnd Property', '\tPublic Property Set [' + name + '](val' + expose + ')', '\t\tCall [__proxy__](Me,[__data__], "' + name + '", val' + expose + ')', '\tEnd Property', '\tPublic Property Get [' + name + ']', '\tOn Error Resume Next', '\t\tSet[' + name + '] = [__proxy__](Me,[__data__],"' + name + '")', '\tIf Err.Number <> 0 Then', '\t\t[' + name + '] = [__proxy__](Me,[__data__],"' + name + '")', '\tEnd If', '\tOn Error Goto 0', '\tEnd Property');
      }
      for (name in properties) {
        if (uniq[name] !== true) {
          uniq[name] = true;
          buffer.push('\tPublic [' + name + ']');
        }
      }
      for (name in $$skipArray) {
        if (uniq[name] !== true) {
          uniq[name] = true;
          buffer.push('\tPublic [' + name + ']');
        }
      }
      buffer.push('\tPublic [' + 'hasOwnProperty' + ']');
      buffer.push('End Class');
      var body = buffer.join('\r\n');
      var className = VBClassPool[body];
      if (!className) {
        className = avalon.makeHashCode('VBClass');
        window.parseVB('Class ' + className + body);
        window.parseVB(['Function ' + className + 'Factory(a, b)', '\tDim o', '\tSet o = (New ' + className + ')(a, b)', '\tSet ' + className + 'Factory = o', 'End Function'].join('\r\n'));
        VBClassPool[body] = className;
      }
      var ret = window[className + 'Factory'](accessors, VBMediator);
      return ret;
    };
  }
}
module.exports = defineProperties;
