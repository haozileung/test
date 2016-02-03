var FormUtil = {};
FormUtil.getData = function(id){
    var paramObj = {};
    $.each($('#'+id).serializeArray(), function (_, kv) {
        if (paramObj.hasOwnProperty(kv.name)) {
            paramObj[kv.name] = $.makeArray(paramObj[kv.name]);
            paramObj[kv.name].push(kv.value);
        } else {
            paramObj[kv.name] = kv.value;
        }
    });
    return paramObj;
}
module.exports = FormUtil;