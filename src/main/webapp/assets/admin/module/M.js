var M = {}
M.init = function(url, searchData) {
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
				_search(n);
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
							_search();
						}
					});
				}
			}
		}
	});

	var form = new Vue({
		el : '#search-form',
		data : searchData,
		methods : {
			search : function() {
				_search(1);
			}
		}
	});

	function _search(n) {
		if (n) {
			form.$set("pageNo", n);
		}
		$.ajax({
			type : "get",
			url : url,
			dataType : 'json',
			data : form.$data,
			success : function(data) {
				table.$data = data;
				form.$set("pageNo", data.curPage);
			},
			error : function() {
				alert("查询失败！");
			}
		});
	}
	$('#search-btn').click();
}
module.exports = M;