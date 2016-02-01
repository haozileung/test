'use strict';

$('#side-menu').metisMenu();

$(window).bind("load resize", function () {
    var topOffset = 50;
    var width = (this.window.innerWidth > 0) ? this.window.innerWidth : this.screen.width;
    if (width < 768) {
        $('div.navbar-collapse').addClass('collapse');
        topOffset = 100; // 2-row-menu
    } else {
        $('div.navbar-collapse').removeClass('collapse');
    }

    var height = ((this.window.innerHeight > 0) ? this.window.innerHeight : this.screen.height) - 1;
    height = height - topOffset;
    if (height < 1) height = 1;
    if (height > topOffset) {
        $("#page-wrapper").css("min-height", (height) + "px");
    }
});

var url = window.location;
var element = $('ul.nav a').filter(function () {
    return this.href == url || url.href.indexOf(this.href) == 0;
}).addClass('active').parent().parent().addClass('in').parent();
if (element.is('li')) {
    element.addClass('active');
}

var index = null;
$.ajaxSetup({
    beforeSend: function () {
        index = layer.load();
    },
    complete: function () {
        layer.close(index);
    },
    error: function (jqXHR) {
        switch (jqXHR.status) {
            case (500):
                layer.alert('服务器系统内部错误', {
                    icon: 2
                });
                break;
            case (401):
                layer.confirm('未登陆', {
                    btn: ['登陆']
                }, function () {
                    window.location.href = 'login.html';
                });
                break;
            case (403):
                layer.alert('无权限执行此操作', {
                    icon: 2
                });
                break;
            case (404):
                layer.alert('未找到资源', {
                    icon: 2
                });
                break;
            case (408):
                layer.alert('请求超时', {
                    icon: 2
                });
                break;
            default:
                layer.alert('未知错误', {
                    icon: 2
                });
        }
    },
});