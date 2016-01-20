'use strict';
var React = require('react');
var LinkedStateMixin = require('react-addons-linked-state-mixin');
var LoginForm  =  React.createClass ({
  mixins: [LinkedStateMixin],
  getInitialState: function() {
    return {email: '',password:'',remember:false};
  },
  submit:function(){
	  console.debug(this.state);
  },
  render :function(){
    return (
    <div className="col-md-4 col-md-offset-4">
	<div className="login-panel panel panel-default">
	<div className="panel-heading">
		<h3 className="panel-title">请登入</h3>
	</div>
	<div className="panel-body">
		<form id="login-form" role="form" action="login" method="post">
			<fieldset>
				<div className="form-group">
					<input className="form-control" placeholder="邮箱" name="email"
						type="email" autofocus valueLink={this.linkState('email')}/>
				</div>
				<div className="form-group">
					<input className="form-control" placeholder="密码" name="password"
						type="password" valueLink={this.linkState('password')} />
				</div>
				<div className="checkbox">
					<label> <input name="remember" type="checkbox"
						checkedLink={this.linkState('remember')}/>记住我
					</label>
				</div>
				<input type="button" className="btn btn-lg btn-success btn-block"
					id="login" value="登入" onClick={this.submit}/>
			</fieldset>
		</form>
	</div>
</div>
</div>);
  }
});
module.exports = LoginForm;