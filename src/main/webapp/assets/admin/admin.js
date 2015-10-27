require("./css/admin.css");
require("./lib/metisMenu");
require("./lib/loading");
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
	url = url.replace('#', '');
	loadModule(url);
});
function loadModule(url) {
	var dic = require("./module/dictionary");
	dic.init(function() {
		url = url.replace('#', '');
		$.ajax({
			url : url,
			type : 'get',
			cache : true,
			success : function(html) {
				$('#page-wrapper').html(html);
				loader.init(url);
			},
			error : function() {
				alert("网络异常！");
			}
		});
	});
}