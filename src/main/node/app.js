var compressor = require('koa-compressor');
var koa = require('koa');
var render = require('koa-ejs2x');
var path = require('path');
var app = koa();
render(app, {
  root: path.join(__dirname, 'views'),
  layout: 'template',
  viewExt: 'html',
  cache: true,
  debug: false,
});
var index = require('./src/routes/index');
app.use(compressor())
app.use(index.routes());
app.use(function *(next){
	  yield next;
	  if (404 != this.status) {return;}
	  this.status = 404;
	      this.body = {
	        message: 'Not Found'
	      };
	});
app.port = 3000;
module.exports = app;
