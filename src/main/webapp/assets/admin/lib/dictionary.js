var Dictionary = {};
var dic = {};
Dictionary.get = function(type, value, callback) {
	Dictionary.getList(type, function(data) {
		if (isFunction(callback)) {
			callback(data[value]);
		}
	});
}
Dictionary.getList = function(type, callback) {
	if (dic && dic[type]) {
		if (isFunction(callback)) {
			callback(dic[type]);
		}
	} else {
		$.ajax({
			url : "/admin/dictionary",
			dataType : 'json',
			data : {
				parentCode : type,
				isAll : true
			},
			success : function(data) {
				if (data) {
					data.forEach(function(d) {
						if (!(d.parentCode in dic)) {
							dic[d.parentCode] = {};
						}
						dic[d.parentCode][d.code] = d.value;
					});
				}
				if (isFunction(callback)) {
					callback(dic[type]);
				}
			}
		});
	}
}
Dictionary.getType = function(callback) {
	return Dictionary.getList('000', callback);
}
Dictionary.refresh = function() {
	dic = {};
}
function isFunction(functionToCheck) {
	var getType = {};
	return functionToCheck
			&& getType.toString.call(functionToCheck) === '[object Function]';
}
module.exports = Dictionary;