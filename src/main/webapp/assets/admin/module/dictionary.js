var Dictionary = {};
var dic = {};
Dictionary.init = function() {
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
		}
	});
}
Dictionary.get = function(type, value) {
	if (dic && dic[type]) {
		return dic[type][value];
	}
}
Dictionary.getList = function(type) {
	if (dic) {
		return dic[type];
	}
}
Dictionary.getType = function() {
	if (dic) {
		return dic['000'];
	}
}
Dictionary.refresh = function() {
	dic = {};
	Dictionary.init();
}
module.exports = Dictionary;