$('select#status').select2({
	ajax : {
		url : "/admin/select",
		dataType : 'json',
		delay : 250,
		data : {
			type : "001"
		},
		processResults : function(data) {
			return {
				results : data
			};
		},
		cache : true
	}
});
console.debug("user");