'use strict';
var React = require('react');
var Menu  =  React.createClass ({  
  getInitialState: function() {
    return {current:''};
  },  
  handlerClick: function(level,i){
      if(level > 0){
          this.setState({current:level});
      }else{
          this.setState({current:i});
      }
  },  
  render :function(){
      var level = this.props.level;
      var cx = require('classnames');
      var classes = cx({
          'nav': true,
          'collapse': level > 0,
          'in': level ===this.state.current
      });
      console.debug(level+"___"+this.state.current);
      var click = this.handlerClick;
      var _this = this;
      var menuList = this.props.menus.map(function(menu,i){
               return Object.prototype.toString.call(menu.value) === '[object Array]'?
               <li key={i} onClick={click.bind(_this,level,i)}><a href="javascript:;">{menu.name}<span className="fa arrow"></span></a><Menu menus={menu.value} level={i} /></li>:
               <li key={i} onClick={click.bind(_this,level,i)}><a href={menu.value}>{menu.name}</a></li>;
           });
    return (<ul className={classes}>{menuList}</ul>);
  }
});
module.exports = Menu;