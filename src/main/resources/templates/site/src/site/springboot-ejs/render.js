/**
 * Created by liuxinjie on 16/7/30.
 */



function toJsonObject(model) {
    var o = {};
    for(var k in model) {
        if(model[k] instanceof Java.type("java.lang.Iterable")) {
            o[k] = Java.from(model[k]);
        } else {
            o[k] = model[k];
        }
    }
    return o;
}


function render(template, model) {
    return ejs.render(template, toJsonObject(model));
}
