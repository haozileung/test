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
var index = require('./routes/index');
app.use(compressor())
app.use(index.routes());
app.use(function *(next){
	  yield next;
	  if (404 != this.status) {return;}
	  this.status = 404;
	  switch (this.accepts('html', 'json')) {
	    case 'html':
	    	yield this.render('404');
	      break;
	    case 'json':
	      this.body = {
	        message: 'Page Not Found'
	      };
	      break;
	    default:
	      this.type = 'text';
	      this.body = 'Page Not Found';
	  }
	});
app.port = 3000;
module.exports = app;
