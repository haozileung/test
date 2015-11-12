var M = {};
M.init = function(url, searchData, editData) {
	M.vm = new Vue({
		el : "#module",
		data : {
			firstPage : 0,
			lastPage : 0,
			curPage : 0,
			slider : [],
			nextPage : 0,
			previousPage : 0,
			list : [],
			searchData : $.extend(true, {
				pageNo : 1
			}, searchData),
			editData : $.extend(true, {}, editData)
		},
		methods : {
			search : function(n) {
				var self = this;
				if (!isNaN(n)) {
					self.searchData.pageNo = n;
				}
				$.ajax({
					type : "get",
					url : url,
					dataType : 'json',
					data : self.searchData,
					success : function(data) {
						data.list.forEach(function(e) {
							e.selected = false;
						});
						self.firstPage = data.firstPage;
						self.lastPage = data.lastPage;
						self.curPage = data.curPage;
						self.slider = data.slider;
						self.nextPage = data.nextPage;
						self.previousPage = data.previousPage;
						self.list = data.list;
						self.searchData.pageNo = data.curPage;
					},
					error : function() {
						layer.msg("查询失败！");
					}
				});
			},
			reset : function() {
				this.searchData = $.extend(true, {
					pageNo : 1
				}, searchData);
			},
			onSave : function() {
				var self = this;
				$.ajax({
					url : url,
					dataType : 'json',
					type : this.editData.id ? 'post' : 'put',
					data : this.editData,
					success : function(data) {
						if (data.code === '0000') {
							$('#editModal').modal('toggle');
							this.editData = $.extend(true, {}, editData);
							self.search();
						} else {
							layer.msg(data.error);
						}
					}
				});
			},
			onSelect : function(id) {
				var e = this.list.find(function(element) {
					if (element.id == id) {
						return true;
					} else {
						return false;
					}
				});
				if (e.selected) {
					e.selected = false;
				} else {
					e.selected = true;
				}
				return false;
			},
			changeStatus : function(id, status, e) {
				var self = this;
				status = status == 1 ? 0 : 1;
				var element = this.list.find(function(o) {
					if (o.id == id) {
						return true;
					} else {
						return false;
					}
				});
				element.status = status;
				$.ajax({
					url : url,
					type : 'post',
					data : element,
					success : function(data) {
						self.search();
					}
				});
				e.stopPropagation();
			},
			toPage : function(n, e) {
				if ($(e.target).parent().hasClass("disabled")) {
					return false;
				}
				this.search(n);
			},
			onDelete : function() {
				var id = [];
				this.list.forEach(function(e) {
					if (e.selected) {
						id.push(e.id);
					}
				});
				var ids = id.join(",");
				if (ids.length > 0) {
					var self = this;
					layer.confirm("确认删除吗？", {
					    btn: ['确认','取消'] //按钮
					}, function(c){
						$.ajax({
							url : url + "?id=" + ids,
							type : 'delete',
							success : function(data) {
								layer.close(c);
								self.search();
							}
						});
					});						
				} else {
					layer.msg("请选择数据！");
				}
			},
			onNew : function() {
				this.editData = $.extend(true, {}, editData);
				$('#editModal').modal('toggle');
			},
			onEdit : function() {
				var element;
				this.list.every(function(e) {
					if (e.selected) {
						element = e;
						return false;
					}
					return true;
				});
				if (element) {
					this.editData = $.extend(true, {}, element);
					$('#editModal').modal('toggle');
				} else {
					layer.msg("请选择数据！");
				}
			}
		}
	});
}
module.exports = M;