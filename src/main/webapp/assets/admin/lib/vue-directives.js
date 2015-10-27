Vue.directive('select', {
	twoWay : true,
	params : [ 'type' ],
	bind : function() {
		var dic = require('../module/dictionary');
		var types = dic.getList(this.params.type);
		var self = this;
		var data = new Array();
		Object.keys(types).forEach(function(k) {
			data.push({
				id : k,
				text : types[k]
			});
		});
		$(self.el).select2({
			data : data
		}).on('change', function() {
			self.set(this.value);
		});
	},
	update : function(value) {
		$(this.el).val(value).trigger('change')
	},
	unbind : function() {
		$(this.el).off().select2('destroy')
	}
});
Vue.directive('dic', {
	params : [ 'type', 'value' ],
	update : function() {
		var dic = require("../module/dictionary");
		$(this.el).text(dic.get(this.params.type, this.params.value));
	}
});
