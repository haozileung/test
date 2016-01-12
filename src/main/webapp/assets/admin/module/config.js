'use strict';
var m = [ {
	html : '/admin/user.html',
	url : "admin/user",
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
	url : "admin/dictionary",
	search : {
		code : "",
		value : "",
		parentCode : "",
		status : 1
	},
	edit : {
		id : "",
		code : "",
		value : "",
		parentCode : "000",
		status : 1
	}
}, {
	html : '/admin/resource.html',
	url : "admin/resource",
	search : {
		code : "",
		name : "",
		groupId : 0,
		type : "",
		status : 1
	},
	edit : {
		id : "",
		code : "",
		name : "",
		url : "",
		orderNo : "",
		groupId : 0,
		type : 0,
		status : 1
	}
} ];
module.exports = m;