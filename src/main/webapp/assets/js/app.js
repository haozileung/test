seajs.config({
	alias : {
		'moment':'lib/moment.js'
	},
	charset : 'utf-8'
});
seajs.use('controller/main',function(m){
	m.init();
});