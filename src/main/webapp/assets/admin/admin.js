require("./css/admin.css");
require("./lib/metisMenu");
require("./lib/vue-directives");
var loader = require("./module/loader");
$('#side-menu').metisMenu();
$(window).bind(
		"load resize",
		function() {
			topOffset = 50;
			width = (this.window.innerWidth > 0) ? this.window.innerWidth
					: this.screen.width;
			if (width < 768) {
				$('div.navbar-collapse').addClass('collapse');
				topOffset = 100;
			} else {
				$('div.navbar-collapse').removeClass('collapse');
			}

			height = ((this.window.innerHeight > 0) ? this.window.innerHeight
					: this.screen.height) - 1;
			height = height - topOffset;
			if (height < 1)
				height = 1;
			if (height > topOffset) {
				$("#page-wrapper").css("min-height", (height) + "px");
			}
		});
var index = null;
$.ajaxSetup({
	beforeSend : function() {
		index = layer.load();
	},
	complete : function() {
		layer.close(index);
	},
	error : function(jqXHR, textStatus, errorThrown) {
		switch (jqXHR.status) {
		case (500):
			layer.alert('服务器系统内部错误', {
				icon : 2
			});
			break;
		case (401):
			layer.confirm('未登陆', {
				btn : [ '登陆' ]
			// 按钮
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
var url = window.location;
var menu = $('ul.nav a').filter(function() {
	return this.href == url || url.href.indexOf(this.href) == 0;
});
var element = menu.addClass('active').parent().parent().addClass('in').parent();
if (element.is('li')) {
	element.addClass('active');
}
if (url.hash.length > 0) {
	loadModule(url.hash);
}
$('.ajax').click(function(event) {
	var url = $(this).attr('href');
	loadModule(url);
});
function loadModule(url) {
	url = url.replace('#', '');
	$.ajax({
		url : url,
		type : 'get',
		cache : true,
		success : function(html) {
			$('#page-wrapper').html(html);
			loader.init(url);
		}
	});
}