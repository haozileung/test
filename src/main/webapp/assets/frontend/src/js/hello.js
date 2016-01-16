var React = require('react');
var ReactDOMServer = require('react-dom');
var HelloWorldComponent  =  React.createClass ({
  render :function(){
    return <div>Hello World {this.props.name}</div>;
  }
});
module.exports = HelloWorldComponent;