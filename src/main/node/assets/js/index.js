'use strict';
require('../css/main.css');
require([ './lib/plus' ], function(plus) {
	console.log(plus(2, 9));
});
var React = require('react');
var ReactDOM = require('react-dom');
var Hello = require('./lib/hello');
var $ = require('jquery');
var moment = require('moment');
console.debug($.fn.jquery);
console.debug(moment());
ReactDOM.render(<Hello name="Haozi" />, document.getElementById('example'));