webpackJsonp([0],[,function(e,t){var a={};a.init=function(e,t,n){a.vm=new Vue({el:"#module",data:{firstPage:0,lastPage:0,curPage:0,slider:[],nextPage:0,previousPage:0,list:[],searchData:$.extend(!0,{pageNo:1},t),editData:$.extend(!0,{},n)},methods:{search:function(t){var a=this;isNaN(t)||(a.searchData.pageNo=t),$.ajax({type:"get",url:e,dataType:"json",data:a.searchData,success:function(e){e.list.forEach(function(e){e.selected=!1}),a.firstPage=e.firstPage,a.lastPage=e.lastPage,a.curPage=e.curPage,a.slider=e.slider,a.nextPage=e.nextPage,a.previousPage=e.previousPage,a.list=e.list,a.searchData.pageNo=e.curPage},error:function(){layer.msg("\u67e5\u8be2\u5931\u8d25\uff01")}})},reset:function(){this.searchData=$.extend(!0,{pageNo:1},t)},onSave:function(){var t=this;$.ajax({url:e,dataType:"json",type:this.editData.id?"post":"put",data:this.editData,success:function(e){"0000"===e.code?($("#editModal").modal("toggle"),this.editData=$.extend(!0,{},n),t.search()):layer.msg(e.error)}})},onSelect:function(e){var t=this.list.find(function(t){return t.id==e?!0:!1});return t.selected?t.selected=!1:t.selected=!0,!1},changeStatus:function(t,a,n){var o=this;a=1==a?0:1;var r=this.list.find(function(e){return e.id==t?!0:!1});r.status=a,$.ajax({url:e,type:"post",data:r,success:function(e){o.search()}}),n.stopPropagation()},toPage:function(e,t){return $(t.target).parent().hasClass("disabled")?!1:void this.search(e)},onDelete:function(){var t=[];this.list.forEach(function(e){e.selected&&t.push(e.id)});var a=t.join(",");if(a.length>0){var n=this;layer.confirm("\u786e\u8ba4\u5220\u9664\u5417\uff1f",{btn:["\u786e\u8ba4","\u53d6\u6d88"]},function(t){$.ajax({url:e+"?id="+a,type:"delete",success:function(e){layer.close(t),n.search()}})})}else layer.msg("\u8bf7\u9009\u62e9\u6570\u636e\uff01")},onNew:function(){this.editData=$.extend(!0,{},n),$("#editModal").modal("toggle")},onEdit:function(){var e;this.list.every(function(t){return t.selected?(e=t,!1):!0}),e?(this.editData=$.extend(!0,{},e),$("#editModal").modal("toggle")):layer.msg("\u8bf7\u9009\u62e9\u6570\u636e\uff01")}}})},e.exports=a}]);