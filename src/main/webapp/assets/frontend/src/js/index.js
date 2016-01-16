'use strict';
require('../css/main.css');
var plus = require('./plus');
console.log(plus(2, 9));
var React = require('react');
var ReactDOM = require('react-dom');
var Hello = require('./hello');

ReactDOM.render(<Hello name="Haozi" />, document.getElementById('example'));
