var Loader = {}
Loader.init = function(url) {
	if (url === '/admin/user.html') {
		require([ '../lib/M.js' ], function(M) {
			M.init("/admin/user", {
				pageNo : 1,
				name : "",
				status : 1
			}, {
				id : "",
				name : "",
				email : "",
				password : "",
				status : 1
			});
		});
	}
	if (url === '/admin/dictionary.html') {
		require([ '../lib/M.js' ], function(M) {
			M.init("/admin/dictionary", {
				pageNo : 1,
				code : "",
				value : "",
				parentCode : "000",
				status : 1
			}, {
				id : "",
				code : "",
				value : "",
				parentCode : "000",
				status : 1
			});
		});
	}
}
module.exports = Loader;