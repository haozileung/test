var React = require('react');
var ReactDOMServer = require('react-dom');
var LoginForm  =  React.createClass ({
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
						type="email" autofocus />
				</div>
				<div className="form-group">
					<input className="form-control" placeholder="密码" name="password"
						type="password" />
				</div>
				<div className="checkbox">
					<label> <input name="remember" type="checkbox"
						value="RememberMe" />记住我
					</label>
				</div>
				<input type="submit" className="btn btn-lg btn-success btn-block"
					id="login" value="登入" />
			</fieldset>
		</form>
	</div>
</div>
</div>);
  }
});
module.exports = LoginForm;