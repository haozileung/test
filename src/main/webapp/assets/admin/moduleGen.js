module.exports = function(source) {
	if (source.indexOf('Loader.init = function') > -1) {
		var template = "if (url === '{html}') {require([ '../lib/M.js' ], function(M) {M.init('{url}', {search}, {edit});$('#search-btn').click();});}";
		var content = "";
		var m = require('./module');
		m.forEach(function(s) {
			content += template.replace('{html}', s.html).replace('{url}',
					s.url).replace('{edit}', JSON.stringify(s.edit)).replace(
					'{search}', JSON.stringify(s.search));
		});
		return source.replace('CONTENT', content);
	}
	return source;
};