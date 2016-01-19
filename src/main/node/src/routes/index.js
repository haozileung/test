var router = require('koa-router')();
var c = require("../lib/cache");
router.get("/", function* (next) {
	this.body = "Hello ";
	yield next;
});
module.exports = router;
