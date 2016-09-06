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
        if (window.webkitURL) {
          events.webkitEditableContentChanged = updateModel;
        } else if (window.MutationEvent) {
          events.DOMCharacterDataModified = updateModel;
        }
        events.input = updateModel;
      }
      break;
    case 'input':
      if (data.isChanged) {
        events.change = updateModel;
      } else {
        events.input = updateModel;
        events.compositionstart = openComposition;
        events.compositionend = closeComposition;
        if (avalon.msie) {
          events.keyup = updateModelKeyDown;
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
function updateModelKeyDown(e) {
  var key = e.keyCode;
  if (key === 91 || (15 < key && key < 19) || (37 <= key && key <= 40))
    return;
  updateModel.call(this, e);
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
  var elem = this;
  setTimeout(function() {
    updateModel.call(elem, e);
  }, 0);
}
markID(openCaret);
markID(closeCaret);
markID(openComposition);
markID(closeComposition);
markID(updateModelKeyDown);
markID(updateModel);
function getCaret(field) {
  var start = NaN;
  if (field.setSelectionRange) {
    start = field.selectionStart;
  }
  return start;
}
function setCaret(field, pos) {
  if (!field.value || field.readOnly)
    return;
  field.selectionStart = pos;
  field.selectionEnd = pos;
}
module.exports = updateModelByEvent;
