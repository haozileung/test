define(function(require) {
	var $ = require('jquery');
	require("bootstrap");
	$(document).ready(function() {
		console.debug($.now());
	});
});