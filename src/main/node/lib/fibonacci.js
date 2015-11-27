var ffi = require('ffi');
var path = require('path');

var lib = ffi.Library(path.join(__dirname, '../../rust/target/release/rs'), {
	fibonacci : [ 'int', [ 'int' ] ],
	process : [ 'string', [ 'string' ] ]
});

function fibonacci(n) {
	if (n <= 2) {
		return 1;
	} else {
		return fibonacci(n - 1) + fibonacci(n - 2);
	}
}

module.exports = {
	string : lib.process,
	js : fibonacci,
	rust : lib.fibonacci
};