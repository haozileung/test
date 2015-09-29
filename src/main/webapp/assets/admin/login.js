require("./css/login.css");
var $ = require('jquery');
require('form')($);
$('#login-form').ajaxForm({
	beforeSubmit : function(params, fm, options) {
		$('#login').prop('disabled', true);
	},
	success : function(data) {
		if(data.code == '0000'){
		}
	},
	error : function(data) {
		$('#login').prop('disabled', false);
	}
});