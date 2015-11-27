var ffi = require('ffi');
var path = require('path');

var lib = ffi.Library(path.join(__dirname, 'rs'), {
	fibonacci : [ 'int', [ 'int' ] ]
});

function fibonacci(n) {
	if (n <= 2) {
		return 1;
	} else {
		return fibonacci(n - 1) + fibonacci(n - 2);
	}
}

module.exports = {
	js : fibonacci,
	rust : lib.fibonacci
};