'use strict';
var React = require('react');
var Menu  =  React.createClass ({  
  getInitialState: function() {
    return {active:0};
  },  
  handlerClick: function(i){
      if(this.state.active ===(i+1)){
          this.setState({active:0});
      }else{
          this.setState({active:i+1});
      }
  },
  render :function(){
      var level = parseInt(this.props.level);
      var cx = require('classnames');
      var ulclasses = cx({
          'nav': true,
          'nav-second-level collapse': level > 0,
          'in': this.props.isOpen
      });
      var menuList = this.props.menus.map(function(menu,i){
               var liclasses = cx({'active': this.state.active == (i+1)});             
               if(Object.prototype.toString.call(menu.value) === '[object Array]'){
                  var open = this.state.active === (i+1);
                   var sumMenu = <Menu menus={menu.value} level={level+1} isOpen={open} />;
                   return <li key={i} className={liclasses}><a href="javascript:;" onClick={this.handlerClick.bind(this,i)}>{menu.name}<span className="fa arrow"></span></a>{sumMenu}</li>;
               }else{
                   return <li key={i} className={liclasses}><a href={menu.value}>{menu.name}</a></li>;
               }
           },this);
    return (<ul className={ulclasses}>{menuList}</ul>);
  }
});
module.exports = Menu;