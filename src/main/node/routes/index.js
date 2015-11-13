var router      = require('koa-router')();
router.get("/",function *(next) {
	yield this.render('index');
});
router.get("/login",function *(next) {
    this.body = "xixi"
});
module.exports = router;
