var express = require('express');
var logger = require('morgan');
var app = express();
app.set('view engine', 'ejs');
app.use(logger('dev', {
	skip : function(req, res) {
		return res.statusCode < 400
	}
}));
app
		.get(
				"/",
				function(req, res) {
					var down = 2;
					res
							.render(
									'index',
									function() {
										setTimeout(
												function() {
													res
															.write('<script>loaded("pagelet1", "I am pagelet 1!")</script>');

													if (--down === 0) {
														res
																.end('</body></html>');
													}
												}, 4000);

										setTimeout(
												function() {
													res
															.write('<script>loaded("pagelet2", "I am pagelet 2!")</script>');
													if (--down === 0) {
														res
																.end('</body></html>');
													}
												}, 3000);
									});
				});
app.use(function(err, req, res, next) {
	console.error(err.stack);
	var data = {
		error : 'Something blew up!'
	};
	if (req.xhr) {
		res.status(500).send(data);
	} else {
		res.status(500).send(data.error);
	}
});
app.use(function(req, res, next) {
	res.status(404).send('Sorry cant find that!');
});
module.exports = app;
