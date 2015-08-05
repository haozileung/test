seajs.config({
	alias : {
		'jquery' : 'lib/jquery/jquery.min.js',
		'form' : 'lib/jquery/jquery.form.min.js',
		'bootstrap' : '../../plugins/bootstrap/js/bootstrap.min.js'
		'moment':'lib/moment.js'
	},
	preload : 'bootstrap',
	charset : 'utf-8'
});
seajs.use('controller/main');