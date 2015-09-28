require("./css/login.css");
var $ = require('jquery');
var form = require('form')($);
$('#login-form').ajaxForm({
	beforeSubmit : function(params, fm, options) {
		$('#login').prop('disabled', true);
	},
	success : function(data) {
		if(code == '0000'){
		}
	},
	error : function(data) {
		$('#login').prop('disabled', false);
	}
});