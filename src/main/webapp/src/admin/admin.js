require("./css/admin.css");
var $ = require("jquery");
require("bootstrap");
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
var url = window.location;
var element = $('ul.nav a').filter(function() {
	return this.href == url || url.href.indexOf(this.href) == 0;
}).addClass('active').parent().parent().addClass('in').parent();
if (element.is('li')) {
	element.addClass('active');
}
$('.ajax').click(function(event){
$('#page-wrapper').html("<div class='col-md-12 text-center'><span class='glyphicon glyphicon-refresh glyphicon-refresh-animate'></span></div>");
var u = $(this).attr('href');
$.ajax( {
    url:u,
    type:'get',
    cache:true,
    success:function(data) {
        $('#page-wrapper').html(data);
    },
    error : function() {
        alert("网络异常！");
    }
 });
 return false;
});