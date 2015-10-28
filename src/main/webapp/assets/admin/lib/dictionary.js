var Dictionary = {};
var dic = {};
var flag = true;
Dictionary.init = function(callback) {
	if (flag) {
		$.ajax({
			url : "/admin/dictionary",
			dataType : 'json',
			data : {
				isAll : true
			},
			success : function(data) {
				if (data) {
					data.forEach(function(d) {
						if (d.parentCode === '000' && !(d.code in dic)) {
							if (!('000' in dic)) {
								dic['000'] = {};
							}
							dic['000'][d.code] = d.value;
							dic[d.code] = {};
						} else {
							if (!(d.parentCode in dic)) {
								dic[d.parentCode] = {};
							}
							dic[d.parentCode][d.code] = d.value;
						}
					});
				}
				flag = false;
				callback();
			}
		});
	} else {
		callback();
	}
}
Dictionary.get = function(type, value) {
	if (dic && dic[type] && dic[type][value]) {
		return dic[type][value];
	}
}
Dictionary.getList = function(type) {
	if (dic && dic[type]) {
		return dic[type];
	}
	return {};
}
Dictionary.getType = function() {
	if (dic && dic['000']) {
		return dic['000'];
	}
	return {};
}
Dictionary.refresh = function() {
	dic = {};
	flag = true;
	Dictionary.init();
}
module.exports = Dictionary;