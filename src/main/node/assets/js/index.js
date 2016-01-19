'use strict';
require('../css/login.css');
var React = require('react');
var ReactDOM = require('react-dom');
require(['./components/loginForm'],function(LoginForm){
	ReactDOM.render(<LoginForm />, document.getElementById('example'));
});

