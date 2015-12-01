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
module.exports = app;
