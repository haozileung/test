var demo = new Vue({
	el : '#result-list',
	data : {
		list : []
	}
});
$('#search-form').ajaxForm({
	dataType : 'json',
	success : function(data) {
		if (data) {
			var d = {
				list : data.list
			};
			demo.$data = d;
		}
	}
});
$('#delete-btn').click(function() {
	var ids = $("input[name='selected-id']:checked").map(function() {
		return this.value;
	}).get().join(",");
	if (ids.length > 0) {
		$.ajax({
			url : u + "?id=" + ids,
			type : 'delete',
			success : function(data) {
				$('#search-btn').click();
			}
		});
	}
});