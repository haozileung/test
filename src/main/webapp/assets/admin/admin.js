require("./css/admin.css");
require("./lib/metisMenu");
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
$('.ajax').click(function(event) {
	var url = $(this).attr('href');
	url = url.replace('#', '');
	loadModule(url);
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
var loading = require("./module/loading");
loading.init($('#loading'), {
	imagePath : '/assets/images/bloading.gif',
	imagePadding : 4,
	maskOpacity : .5,
	fullScreen : true,
	overlay : {
		show : true
	}
});
$.ajaxSetup({
	beforeSend : function() {
		loading.show();
	},
	complete : function() {
		loading.hide();
	}
});
function loadModule(url) {
	url = url.replace('#', '');
	$.ajax({
		url : url,
		type : 'get',
		cache : true,
		success : function(html) {
			$('#page-wrapper').html(html);
			var loader = require("./module/loader");
			loader.init(url);
		},
		error : function() {
			alert("网络异常！");
		}
	});
}
Vue.directive('dic', {
	params : [ 'type', 'value' ],
	update : function() {
		console.log(this.params.type)
		if (this.params.value == 1) {
			$(this.el).text("禁用");
		}
		if (this.params.value == 0) {
			$(this.el).text("启用");
		}
	}
});
