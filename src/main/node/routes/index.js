var router = require('koa-router')();
var f = require("../lib/fibonacci");
var c = require("../lib/cache");
router.get("/", function* (next) {
	this.body = "Hello ";
	yield next;
}, function* (next) {
	var ctx = this;
	c.get("fibonacci", function(err, value) {
		if (!err) {
			if (value == undefined) {
				value = f.fibonacci(40);
				c.set("fibonacci", value);
			}
			ctx.body = ctx.body + "World! " + value;
		}
	});
});
module.exports = router;
