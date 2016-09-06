/* */ 
"format cjs";
function createMap() {
    return Object.create ? Object.create(null): {}
}
function getAttributes(node) {
    var attrs = node.attributes, ret = createMap()
    for (var i = 0; i < attrs.length; i++) {
        var attr = attrs[i]
        if (attr.specified) {
            ret[attr.name] = attr.value
        }
    }
    if (/input|textarea|select/i.test(node.nodeName)) {
        ret.type = node.type
    }
    var style = node.style.cssText
    if (style) {
        ret.style = style
    }
    var className = node.className
    if (className) {
        ret['staic-class'] = className
    }
    //类名 = 去重(静态类名+动态类名+ hover类名? + active类名)
    if (ret.type === 'select-one') {
        ret.selectedIndex = node.selectedIndex
    }
    return ret
}


function renderFormDOM(node) {
    switch (node.nodeType) {
        case 1:

            var ret = {
                nodeName: node.nodeName.toLowerCase(),
                nodeType: 1,
                props: getAttributes(node),
                dom: node,
                children: renderFormDOMs(node.childNodes, node)
            }
            if ('selectedIndex' in ret) {
                node.selectedIndex = ret.selectedIndex
            }
            return ret
        case 3:
            return {
                nodeName: '#text',
                nodeType: 3,
                children: node.nodeValue,
                dom: node
            }
        case 8:
            return {
                nodeName: '#text',
                nodeType: 3,
                children: node.nodeValue,
                dom: node
            }
    }
}
//根据 outerHTML 创建 虚拟DOM
function render(node) {
    return renderFormDOMs([node], null)
}
function renderFormDOMs(nodes, parent) {
    var arr = []
    nodes = avalon.slice(nodes)
    for (var i = 0; i < nodes.length; i++) {
        var node = nodes[i]
        switch (node.nodeType) {
            case 1:
                var value = node.getAttribute('ms-for')
                if (value) {
                    var start = {
                        nodeType: 8,
                        nodeName: '#comment',
                        signature: Math.random(),
                        nodeValue: 'ms-for:' + value,
                        dom: document.createComment('ms-for:' + value)
                    }

                    node.removeAttribute('ms-for')
                    start.template = createNodeHTML(renderFormDOM(node))
                    var end = {
                        nodeType: 8,
                        nodeName: '#comment',
                        signature: start.signature,
                        nodeValue: 'ms-for-end:',
                        dom: document.createComment('ms-for-end:')
                    }
                    arr.push(start, [], end)
                    if (parent) {
                        parent.replaceChild(end.dom, node)
                        parent.insertBefore(start.dom, end.dom)
                    }

                } else {
                    arr.push(renderFormDOM(node))
                }
                break
            case 3:
                if (/\S/.test(node.nodeValue)) {
                    arr.push(renderFormDOM(node))
                } else {
                    remove(node)
                }
                break
            case 8:
                if (node.nodeValue.indexOf('ms-for:')) {
                    var start = renderFormDOM(node)
                    start.signature = Math.random()
                    var newArr = []
                    arr.push(newArr)
                    newArr.arr = arr
                    newArr.start = start
                    arr.push(start)
                    newArr = arr

                } else if (node.nodeValue.indexOf('ms-for-end:')) {
                    var start = arr.start
                    var old = arr
                    arr = arr.arr
                    var end = renderFormDOM(node)
                    end.signature = start.signature
                    start.template = createNodeHTML(old)
                    for (var j = 0; j < old.length; j++) {
                        remove(old[j])
                    }

                } else {
                    arr.push(renderFormDOM(node))
                }
        }
    }
    return arr
}