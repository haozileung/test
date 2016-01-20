'use strict';
require('../css/login.css');
var React = require('react');
var ReactDOM = require('react-dom');
require('./lib/common');

var LoginForm = require('./components/loginForm');
ReactDOM.render(<LoginForm />, document.getElementById('content'));