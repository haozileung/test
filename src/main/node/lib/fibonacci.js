var ffi = require('ffi');
var path = require('path');

var lib = ffi.Library(path.join(__dirname, '../../rust/target/release/rs'), {
	fibonacci : [ 'int', [ 'int' ] ],
	process : [ 'string', [ 'string' ] ]
});
module.exports = lib;