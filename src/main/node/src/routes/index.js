var router = require('koa-router')();
var c = require("../lib/cache");
router.get("/", function* (next) {
	this.body = {response:{msg:"success"}};
});
module.exports = router;
