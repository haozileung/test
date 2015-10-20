var M = {}
M.init = function(url, searchData) {
	M.table = new Vue({
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
					if (window.confirm("确认删除吗？")) {
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
		}
	});

	M.form = new Vue({
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
			M.form.$set("pageNo", n);
		}
		$.ajax({
			type : "get",
			url : url,
			dataType : 'json',
			data : M.form.$data,
			success : function(data) {
				M.table.$data = data;
				M.form.$set("pageNo", data.curPage);
			},
			error : function() {
				alert("查询失败！");
			}
		});
	}
	$('#search-btn').click();
}
module.exports = M;