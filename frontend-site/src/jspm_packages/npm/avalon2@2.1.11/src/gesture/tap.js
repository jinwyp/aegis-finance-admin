/* */ 
var Recognizer = require('./recognizer');
var root = avalon.root;
var supportPointer = !!navigator.pointerEnabled || !!navigator.msPointerEnabled;
if (supportPointer) {
  root.style.msTouchAction = root.style.touchAction = 'none';
}
var tapRecognizer = {
  events: ['tap'],
  touchBoundary: 10,
  tapDelay: 90,
  needClick: function(target) {
    switch (target.nodeName.toLowerCase()) {
      case 'button':
      case 'select':
      case 'textarea':
        if (target.disabled) {
          return true;
        }
        break;
      case 'input':
        if ((Recognizer.isIOS && target.type === 'file') || target.disabled) {
          return true;
        }
        break;
      case 'label':
      case 'iframe':
      case 'video':
        return true;
    }
    return false;
  },
  needFocus: function(target) {
    switch (target.nodeName.toLowerCase()) {
      case 'textarea':
      case 'select':
        return true;
      case 'input':
        switch (target.type) {
          case 'button':
          case 'checkbox':
          case 'file':
          case 'image':
          case 'radio':
          case 'submit':
            return false;
        }
        return !target.disabled && !target.readOnly;
      default:
        return false;
    }
  },
  focus: function(targetElement) {
    var length;
    var type = targetElement.type;
    if (Recognizer.isIOS && targetElement.setSelectionRange && type.indexOf('date') !== 0 && type !== 'time' && type !== 'month') {
      length = targetElement.value.length;
      targetElement.setSelectionRange(length, length);
    } else {
      targetElement.focus();
    }
  },
  findControl: function(labelElement) {
    if (labelElement.control !== undefined) {
      return labelElement.control;
    }
    if (labelElement.htmlFor) {
      return document.getElementById(labelElement.htmlFor);
    }
    return labelElement.querySelector('button, input:not([type=hidden]), keygen, meter, output, progress, select, textarea');
  },
  fixTarget: function(target) {
    if (target.nodeType === 3) {
      return target.parentNode;
    }
    if (window.SVGElementInstance && (target instanceof SVGElementInstance)) {
      return target.correspondingUseElement;
    }
    return target;
  },
  updateScrollParent: function(targetElement) {
    var scrollParent = targetElement.tapScrollParent;
    if (!scrollParent || !scrollParent.contains(targetElement)) {
      var parentElement = targetElement;
      do {
        if (parentElement.scrollHeight > parentElement.offsetHeight) {
          scrollParent = parentElement;
          targetElement.tapScrollParent = parentElement;
          break;
        }
        parentElement = parentElement.parentElement;
      } while (parentElement);
    }
    if (scrollParent) {
      scrollParent.lastScrollTop = scrollParent.scrollTop;
    }
  },
  touchHasMoved: function(event) {
    var touch = event.changedTouches[0],
        boundary = tapRecognizer.touchBoundary;
    return Math.abs(touch.pageX - tapRecognizer.pageX) > boundary || Math.abs(touch.pageY - tapRecognizer.pageY) > boundary;
  },
  findType: function(targetElement) {
    return Recognizer.isAndroid && targetElement.tagName.toLowerCase() === 'select' ? 'mousedown' : 'click';
  },
  sendClick: function(targetElement, event) {
    Recognizer.fire(targetElement, 'tap', {touchEvent: event});
    var clickEvent,
        touch;
    if (document.activeElement && document.activeElement !== targetElement) {
      document.activeElement.blur();
    }
    touch = event.changedTouches[0];
    clickEvent = document.createEvent('MouseEvents');
    clickEvent.initMouseEvent(tapRecognizer.findType(targetElement), true, true, window, 1, touch.screenX, touch.screenY, touch.clientX, touch.clientY, false, false, false, false, 0, null);
    clickEvent.touchEvent = event;
    targetElement.dispatchEvent(clickEvent);
  },
  touchstart: function(event) {
    if (event.targetTouches.length !== 1) {
      return true;
    }
    var targetElement = tapRecognizer.fixTarget(event.target);
    var touch = event.targetTouches[0];
    if (Recognizer.isIOS) {
      var selection = window.getSelection();
      if (selection.rangeCount && !selection.isCollapsed) {
        return true;
      }
      var id = touch.identifier;
      if (id && isFinite(tapRecognizer.lastTouchIdentifier) && tapRecognizer.lastTouchIdentifier === id) {
        event.preventDefault();
        return false;
      }
      tapRecognizer.lastTouchIdentifier = id;
      tapRecognizer.updateScrollParent(targetElement);
    }
    tapRecognizer.status = "tapping";
    tapRecognizer.startTime = Date.now();
    tapRecognizer.element = targetElement;
    tapRecognizer.pageX = touch.pageX;
    tapRecognizer.pageY = touch.pageY;
    if ((tapRecognizer.startTime - tapRecognizer.lastTime) < tapRecognizer.tapDelay) {
      event.preventDefault();
    }
  },
  touchmove: function(event) {
    if (tapRecognizer.status !== "tapping") {
      return true;
    }
    if (tapRecognizer.element !== tapRecognizer.fixTarget(event.target) || tapRecognizer.touchHasMoved(event)) {
      tapRecognizer.status = tapRecognizer.element = 0;
    }
  },
  touchend: function(event) {
    var targetElement = tapRecognizer.element;
    var now = Date.now();
    if (!targetElement || now - tapRecognizer.startTime > tapRecognizer.tapDelay) {
      return true;
    }
    tapRecognizer.lastTime = now;
    var startTime = tapRecognizer.startTime;
    tapRecognizer.status = tapRecognizer.startTime = 0;
    targetTagName = targetElement.tagName.toLowerCase();
    if (targetTagName === 'label') {
      Recognizer.fire(targetElement, 'tap', {touchEvent: event});
      var forElement = tapRecognizer.findControl(targetElement);
      if (forElement) {
        tapRecognizer.focus(targetElement);
        targetElement = forElement;
      }
    } else if (tapRecognizer.needFocus(targetElement)) {
      if ((now - startTime) > 100 || (deviceIsIOS && window.top !== window && targetTagName === 'input')) {
        tapRecognizer.element = 0;
        return false;
      }
      tapRecognizer.focus(targetElement);
      Recognizer.isAndroid && tapRecognizer.sendClick(targetElement, event);
      return false;
    }
    if (Recognizer.isIOS) {
      var scrollParent = targetElement.tapScrollParent;
      if (scrollParent && scrollParent.lastScrollTop !== scrollParent.scrollTop) {
        return true;
      }
    }
    if (!tapRecognizer.needClick(targetElement)) {
      event.preventDefault();
      tapRecognizer.sendClick(targetElement, event);
    }
  },
  touchcancel: function() {
    tapRecognizer.startTime = tapRecognizer.element = 0;
  }
};
Recognizer.add('tap', tapRecognizer);
