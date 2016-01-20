'use strict';
var layer = require('layer');
var index = null;
$.ajaxSetup({
	beforeSend : function() {
		index = layer.load();
	},
	complete : function() {
		layer.close(index);
	},
	error : function(jqXHR) {
		switch (jqXHR.status) {
		case (500):
			layer.alert('服务器系统内部错误', {
				icon : 2
			});
			break;
		case (401):
			layer.confirm('未登陆', {
				btn : [ '登陆' ]
			}, function() {
				window.location.href = 'login.html';
			});
			break;
		case (403):
			layer.alert('无权限执行此操作', {
				icon : 2
			});
			break;
		case (404):
			layer.alert('未找到资源', {
				icon : 2
			});
			break;
		case (408):
			layer.alert('请求超时', {
				icon : 2
			});
			break;
		default:
			layer.alert('未知错误', {
				icon : 2
			});
		}
	},
});
module.exports = $;