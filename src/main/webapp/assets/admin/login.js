require("./css/login.css");
$('#login-form').ajaxForm({
	beforeSubmit : function(params, fm, options) {
		$('#login').prop('disabled', true);
	},
	success : function(data) {
		if(data.code == '0000'){
			window.location.href='/admin.html';
		}else{
			alert(data.message);
		}
	},
	error : function(data) {
		$('#login').prop('disabled', false);
	}
});