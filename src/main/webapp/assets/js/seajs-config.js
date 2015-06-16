seajs.config({
	alias : {
		'jquery' : 'lib/jquery/jquery.min.js',
		'form' : 'lib/jquery/jquery.form.min.js',
		'bootstrap' : '../../plugins/bootstrap/js/bootstrap.min.js'
	},
	preload : [ 'bootstrap' ],
	charset : 'utf-8'
});