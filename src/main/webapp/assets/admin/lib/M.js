var M = {}
M.init = function(url, searchData, editData) {
	M.editForm = new Vue({
		el : "#edit-modal",
		data : $.extend(true, {}, editData),
		methods : {
			onSave : function() {
				$.ajax({
					url : url,
					dataType : 'json',
					type : M.editForm.id ? 'post' : 'put',
					data : M.editForm.$data,
					success : function(data) {
						if (data.code === '0000') {
							$('#editModal').modal('toggle');
							M.editForm.$data = $.extend(true, {}, editData);
							_search();
						} else {
							alert(data.error);
						}
					}
				});
			}
		}
	});
	M.dataTable = new Vue({
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
			onSelect : function(e) {
				var input = $(e.target).parent().children().first().children()
						.first();
				input.prop("checked", input.prop('checked') ? false : true);
				return false;
			},
			changeStatus : function(id, status, e) {
				status = status == 1 ? 0 : 1;
				$.ajax({
					url : url,
					type : 'post',
					data : {
						id : id,
						status : status
					},
					success : function(data) {
						_search();
					}
				});
				e.stopPropagation();
			},
			toPage : function(n, e) {
				if ($(e.target).parent().hasClass("disabled")) {
					return false;
				}
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
				} else {
					alert("请选择数据！");
				}
			},
			onNew : function() {
				M.editForm.$data = $.extend(true, {}, editData);
				$('#editModal').modal('toggle');
			},
			onEdit : function() {
				var id = $("input[name='selected-id']:checked:first").val();
				if (id) {
					$.ajax({
						type : "get",
						url : url,
						dataType : 'json',
						data : {
							id : id
						},
						success : function(data) {
							M.editForm.$data = data;
							$('#editModal').modal('toggle');
						}
					});

				} else {
					alert("请选择数据！");
				}
			}
		}
	});

	M.searchForm = new Vue({
		el : '#search-form',
		data : $.extend(true, {}, searchData),
		methods : {
			search : function() {
				_search(1);
			},
			reset : function() {
				M.searchForm.$data = $.extend(true, {}, searchData);
			}
		}
	});

	function _search(n) {
		if (n) {
			M.searchForm.$set("pageNo", n);
		}
		$.ajax({
			type : "get",
			url : url,
			dataType : 'json',
			data : M.searchForm.$data,
			success : function(data) {
				M.dataTable.$data = data;
				M.searchForm.$set("pageNo", data.curPage);
			},
			error : function() {
				alert("查询失败！");
			}
		});
	}
	$('#search-btn').click();
}
module.exports = M;