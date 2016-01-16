var ExtractTextPlugin = require('extract-text-webpack-plugin');
var webpack = require('webpack-stream').webpack;
module.exports = {
	entry : {
		common : [ 'react', 'react-dom' ],
		app : './src/js/index.js'
	},
	output : {
		filename : '[name].js'
	},
	plugins : [ new ExtractTextPlugin('[name].css'), new webpack.DefinePlugin({
		'process.env' : {
			'NODE_ENV' : '"production"'
		}
	}), new webpack.optimize.CommonsChunkPlugin('common', 'common.js') ],
	resolve : {
		modulesDirectories : [ 'src', 'node_modules' ]
	},
	externals : {
		'moment' : true,
		'jquery' : true,
		'bootstrap' : true
	},
	module : {
		loaders : [
				{
					test : /\.css$/,
					loader : ExtractTextPlugin.extract('style-loader',
							'css-loader')
				},
				{
					test : /\.js$/,
					loader : 'babel-loader!jsx-loader?harmony'
				},
				{
					test : /\.less$/,
					loader : ExtractTextPlugin.extract('style-loader',
							'css-loader!less-loader')
				} ]
	},
};