var Loader = {}
Loader.init = function(url) {
	if (url === '/admin/user.html') {
		require([ './M.js' ], function(M) {
			M.init("/admin/user", {
				pageNo : 1,
				name : "",
				status : 0
			});
		});
	}
	if (url === '/admin/dictionary.html') {
		require([ './M.js' ], function(M) {
			M.init("/admin/dictionary", {
				pageNo : 1,
				code : "",
				value : "",
				parentCode : "",
				status : 0
			});
		});
	}
}
module.exports = Loader;