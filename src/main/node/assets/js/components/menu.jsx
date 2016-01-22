'use strict';
var React = require('react');
var Menu  =  React.createClass ({  
  getInitialState: function() {
    return {};
  },  
  render :function(){
   var menuList =   this.props.menus.map(function(menu,i){
               return Object.prototype.toString.call(menu.value) === '[object Array]'?
               <li><a href="javascript:;">{menu.name}<span className="fa arrow"></span></a><Menu menus={menu.value} /></li>:
               <li><a href={menu.value}>{menu.name}</a></li>;
           });
    return (<ul className="nav">{menuList}</ul>);
  }
});
module.exports = Menu;