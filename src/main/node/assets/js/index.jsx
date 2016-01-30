'use strict';
var Menu = require('./components/menu.jsx');
var menus = [{name:'test',value:'test'},{name:'test',value:'test'},{name:'test',value:[{name:'test',value:'test'},{name:'test',value:'test'}]}];
ReactDOM.render(<Menu menus={menus} level='0' />, document.getElementById('myMenu'));