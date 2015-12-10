var NodeCache = require("node-cache");
var c = new NodeCache({
	stdTTL : 10,
	checkperiod : 60
});
module.exports = c;