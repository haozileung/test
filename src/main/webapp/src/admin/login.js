require("./css/login.css");
var $ = require('jquery');
var form = require('form')($);
$('#login').click(function() {
	$('#login-form').ajaxSubmit({
		beforeSubmit : function(params, fm, options) {
			$('#login').prop('disabled', true);
		},
		success : function(data) {
		},
		error : function(data) {
			$('#login').prop('disabled', false);
		}
	});
});