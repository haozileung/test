require("./css/admin.css");
var $ = require("jquery");
require("bootstrap");
require("./lib/metisMenu");
require('form')($);
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
		$('.ajax').click(function(event){
        $('#page-wrapper').html("<div class='col-md-12 text-center'><span class='glyphicon glyphicon-refresh glyphicon-refresh-animate'></span></div>");
        var u = $(this).attr('href');
        u = u.replace('#','');
        var Vue = require('vue');
        $.ajax( {
            url:u,
            type:'get',
            cache:true,
            success:function(html) {
                $('#page-wrapper').html(html);
                var demo = new Vue({
                		    el: '#result-list',
                			data: {
                			    list: []
                			}
                });
                $('#search-form').ajaxForm({
                  	dataType:  'json',
                  	success:function(data){
                  		if(data){
                  		    var d = {list:data.list};
                  			demo.$data = d;
                  		}
                  	}
                });
                $('#delete-btn').click(function(){
                	var ids =$("input[name='selected-id']:checked").map(function() {
                                 return this.value;
                             }).get().join(",");
                     if(ids.length >0){
                      $.ajax( {
                                 url:u+"?id="+ids,
                                 type:'delete',
                                 success:function(data) {
                                 $('#search-btn').click();
                      }});}
                });
            },
            error : function() {
                alert("网络异常！");
            }
         });
         return false;
        });
var url = window.location;
var menu =  $('ul.nav a').filter(function() {
        	return this.href == url || url.href.indexOf(this.href) == 0;});
var element =menu.addClass('active').parent().parent().addClass('in').parent();
if (element.is('li')) {
	element.addClass('active');
}
