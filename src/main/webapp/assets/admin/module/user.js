var User = {}
User.init = function(url) {
	var table = new Vue({
		el : '#data-table',
		data : {
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
					form.$set("pageNo", n);
				}
				_search(false);
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
							_search(false);
						}
					});
				}
			}
		}
	});

	var form = new Vue({
		el : '#search-form',
		data : {
			pageNo : 1,
			name : "",
			status : 0
		},
		methods : {
			search : function() {
				_search(true);
			}
		}
	});
	
	function _search(isClick){
		if(isClick){
			form.$set("pageNo", 1);
		}
		$.getJSON(url, form.$data, function(data) {
			table.$data = data;
			form.$set("pageNo", data.curPage);
		});
	}
	$('#search-btn').click();
}
module.exports = User;