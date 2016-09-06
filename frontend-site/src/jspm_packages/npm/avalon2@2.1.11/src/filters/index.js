/* */ 
var number = require('./number');
var sanitize = require('./sanitize');
var date = require('./date');
var arrayFilters = require('./array');
var eventFilters = require('./event');
var filters = avalon.filters;
function K(a) {
  return a;
}
avalon.__format__ = function(name) {
  var fn = filters[name];
  if (fn) {
    return fn.get ? fn.get : fn;
  }
  return K;
};
avalon.mix(filters, {
  uppercase: function(str) {
    return String(str).toUpperCase();
  },
  lowercase: function(str) {
    return String(str).toLowerCase();
  },
  truncate: function(str, length, truncation) {
    length = length || 30;
    truncation = typeof truncation === "string" ? truncation : "...";
    return str.length > length ? str.slice(0, length - truncation.length) + truncation : String(str);
  },
  camelize: avalon.camelize,
  date: date,
  escape: avalon.escapeHtml,
  sanitize: sanitize,
  number: number,
  currency: function(amount, symbol, fractionSize) {
    return (symbol || "\uFFE5") + number(amount, isFinite(fractionSize) ? fractionSize : 2);
  }
}, arrayFilters, eventFilters);
module.exports = avalon;
