/* */ 
var updateModel = require('./updateModelHandle');
var markID = require('../../seed/lang.share').getShortID;
var msie = avalon.msie;
var window = avalon.window;
var document = avalon.document;
function updateModelByEvent(node, vnode) {
  var events = {};
  var data = vnode.duplexData;
  data.update = updateModel;
  switch (data.type) {
    case 'radio':
    case 'checkbox':
      events.click = updateModel;
      break;
    case 'select':
      events.change = updateModel;
      break;
    case 'contenteditable':
      if (data.isChanged) {
        events.blur = updateModel;
      } else {
        if (avalon.modern) {
          if (window.webkitURL) {
            events.webkitEditableContentChanged = updateModel;
          } else if (window.MutationEvent) {
            events.DOMCharacterDataModified = updateModel;
          }
          events.input = updateModel;
        } else {
          events.keydown = updateModelKeyDown;
          events.paste = updateModelDelay;
          events.cut = updateModelDelay;
          events.focus = closeComposition;
          events.blur = openComposition;
        }
      }
      break;
    case 'input':
      if (data.isChanged) {
        events.change = updateModel;
      } else {
        if (msie) {
          events.keyup = updateModelKeyDown;
        }
        if (msie < 9) {
          events.propertychange = updateModelHack;
          events.paste = updateModelDelay;
          events.cut = updateModelDelay;
        } else {
          events.input = updateModel;
        }
        if (!msie || msie > 9) {
          events.compositionstart = openComposition;
          events.compositionend = closeComposition;
        }
        if (!msie) {
          if (!/\[native code\]/.test(window.Int8Array)) {
            events.keydown = updateModelKeyDown;
            events.paste = updateModelDelay;
            events.cut = updateModelDelay;
            if (window.netscape) {
              events.DOMAutoComplete = updateModel;
            }
          }
        }
      }
      break;
  }
  if (/password|text/.test(vnode.props.type)) {
    events.focus = openCaret;
    events.blur = closeCaret;
    data.getCaret = getCaret;
    data.setCaret = setCaret;
  }
  for (var name in events) {
    avalon.bind(node, name, events[name]);
  }
}
function updateModelHack(e) {
  if (e.propertyName === 'value') {
    updateModel.call(this, e);
  }
}
function updateModelDelay(e) {
  var elem = this;
  setTimeout(function() {
    updateModel.call(elem, e);
  }, 0);
}
function openCaret() {
  this.caret = true;
}
function closeCaret() {
  this.caret = false;
}
function openComposition() {
  this.composing = true;
}
function closeComposition(e) {
  this.composing = false;
  updateModelDelay.call(this, e);
}
function updateModelKeyDown(e) {
  var key = e.keyCode;
  if (key === 91 || (15 < key && key < 19) || (37 <= key && key <= 40))
    return;
  updateModel.call(this, e);
}
markID(openCaret);
markID(closeCaret);
markID(openComposition);
markID(closeComposition);
markID(updateModel);
markID(updateModelHack);
markID(updateModelDelay);
markID(updateModelKeyDown);
var mayBeAsync = function(fn) {
  setTimeout(fn, 0);
};
var setCaret = function(target, cursorPosition) {
  var range;
  if (target.createTextRange) {
    mayBeAsync(function() {
      target.focus();
      range = target.createTextRange();
      range.collapse(true);
      range.moveEnd('character', cursorPosition);
      range.moveStart('character', cursorPosition);
      range.select();
    });
  } else {
    target.focus();
    if (target.selectionStart !== undefined) {
      target.setSelectionRange(cursorPosition, cursorPosition);
    }
  }
};
var getCaret = function(target) {
  var start = 0;
  var normalizedValue;
  var range;
  var textInputRange;
  var len;
  var endRange;
  if (typeof target.selectionStart == "number" && typeof target.selectionEnd == "number") {
    start = target.selectionStart;
  } else {
    range = document.selection.createRange();
    if (range && range.parentElement() == target) {
      len = target.value.length;
      normalizedValue = target.value.replace(/\r\n/g, "\n");
      textInputRange = target.createTextRange();
      textInputRange.moveToBookmark(range.getBookmark());
      endRange = target.createTextRange();
      endRange.collapse(false);
      if (textInputRange.compareEndPoints("StartToEnd", endRange) > -1) {
        start = len;
      } else {
        start = -textInputRange.moveStart("character", -len);
        start += normalizedValue.slice(0, start).split("\n").length - 1;
      }
    }
  }
  return start;
};
module.exports = updateModelByEvent;
