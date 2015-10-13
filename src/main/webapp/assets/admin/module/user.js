var User = {}
User.init = function(url) {
	$('#search-form').ajaxForm({
		dataType : 'json',
		success : function(data) {
			if (data) {
				demo.$data = data;
			}
		}
	});
	var demo = new Vue({
		el : '#data-table',
		data : {
			pageNo : 1,
			firstPage : 0,
			lastPage : 0,
			curPage : 0,
			slider : [],
			nextPage : 0,
			previousPage : 0,
			list : []
		},
		methods : {
			toPage : function(n, e) {
				e.preventDefault();
				if (n) {
					this.pageNo = n;
				}
				$('#search-form').submit();
			},
			onDelete : function() {
				var ids = $("input[name='selected-id']:checked").map(
						function() {
							return this.value;
						}).get().join(",");
				if (ids.length > 0) {
					$.ajax({
						url : url + "?id=" + ids,
						type : 'delete',
						success : function(data) {
							$('#search-btn').click();
						}
					});
				}
			}
		}
	});
}

module.exports = User;