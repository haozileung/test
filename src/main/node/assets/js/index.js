'use strict';
require('../less/admin.less');
var ReactDOM = require('react-dom');

var Menu = require('./components/menu');
var menus = [{name:'test',value:'test'},{name:'test',value:'test'},{name:'test',value:[{name:'test',value:'test'},{name:'test',value:'test'}]}];
ReactDOM.render(<Menu menus={menus} level='0' />, document.getElementById('myMenu'));