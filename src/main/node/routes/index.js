var router      = require('koa-router')();
var f = require("../lib/fibonacci");
router.get("/",function *(next) {
    this.body = "Hello ";
    yield next;
  },function *(next) {
    this.body= this.body+"World! "+f['rust'](40);
  });
router.get("/login",function* (next) {
    this.body = "xixi"
});
module.exports = router;
