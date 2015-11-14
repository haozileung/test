Vue.directive('select', {
	twoWay : true,
	params : [ 'type' ],
	bind : function() {
		var dic = require('./dictionary');
		var self = this;
		dic.getList(this.params.type, function(types) {
			var data = new Array();
			if (types) {
				Object.keys(types).forEach(function(k) {
					data.push({
						id : k,
						text : types[k]
					});
				});
			}
			$(self.el).select2({
				data : data
			}).on('change', function() {
				self.set(this.value);
			});
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
		var dic = require("./dictionary");
		var self = this;
		dic.get(self.params.type, self.params.value, function(value) {
			if (value) {
				$(self.el).text(value);
			}
		});
	}
});
