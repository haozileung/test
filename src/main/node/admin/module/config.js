'use strict';
var m = [ {
	html : '/admin/user.html',
	url : "http://localhost:8080/admin/user",
	search : {
		name : "",
		status : 1
	},
	edit : {
		id : "",
		name : "",
		email : "",
		password : "",
		status : 1
	}
}, {
	html : '/admin/dictionary.html',
	url : "http://localhost:8080/admin/dictionary",
	search : {
		code : "",
		value : "",
		parentCode : "000",
		status : 1
	},
	edit : {
		id : "",
		code : "",
		value : "",
		parentCode : "000",
		status : 1
	}
} ];
module.exports = m;