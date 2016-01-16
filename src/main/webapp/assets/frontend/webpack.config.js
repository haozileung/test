var ExtractTextPlugin = require('extract-text-webpack-plugin');
module.exports = {
	entry : {
		app : './src/js/index.js',
	},
	output : {
		filename : '[name].js',
		publicPath : "assets/admin/dist/"
	},
	plugins : [ new ExtractTextPlugin('[name].css') ],
	resolve : {
		modulesDirectories : [ 'src', 'node_modules' ]
	},
	module : {
		loaders : [ {
			test : /\.css$/,
			loader : ExtractTextPlugin.extract('style-loader', 'css-loader')
		} ]
	},
};