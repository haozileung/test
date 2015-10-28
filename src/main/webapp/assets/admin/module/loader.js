var Loader = {}
Loader.init = function(url) {
	if (url === '/admin/user.html') {
		require([ '../lib/M.js' ], function(M) {
			M.init("/admin/user", {
				name : "",
				status : 1
			}, {
				id : "",
				name : "",
				email : "",
				password : "",
				status : 1
			});
			$('#search-btn').click();
		});
	}
	if (url === '/admin/dictionary.html') {
		require([ '../lib/M.js' ], function(M) {
			M.init("/admin/dictionary", {
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
			$('#search-btn').click();
		});
	}
}
module.exports = Loader;