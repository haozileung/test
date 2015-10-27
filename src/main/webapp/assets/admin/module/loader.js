var Loader = {}
Loader.init = function(url) {
	if (url === '/admin/user.html') {
		require([ '../lib/M.js' ], function(M) {
			M.init("/admin/user", {
				pageNo : 1,
				name : "",
				status : ""
			}, {
				id : "",
				name : "",
				email : "",
				password : "",
				status : 100
			});
		});
	}
	if (url === '/admin/dictionary.html') {
		require([ '../lib/M.js' ], function(M) {
			M.init("/admin/dictionary", {
				pageNo : 1,
				code : "",
				value : "",
				parentCode : "",
				status : 100
			}, {
				id : "",
				code : "",
				value : "",
				parentCode : 000,
				status : 100
			});
		});
	}
}
module.exports = Loader;