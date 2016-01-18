var ExtractTextPlugin = require('extract-text-webpack-plugin');
var webpack = require('webpack-stream').webpack;
var AssetsPlugin = require('assets-webpack-plugin');
var assetsPluginInstance = new AssetsPlugin();
var extractTextPluginInstance = new ExtractTextPlugin('[name]_[hash].css');
var cmmonsChunkPluginInstance = new webpack.optimize.CommonsChunkPlugin(
		'common', 'common_[hash].js');
var definePluginInstance = new webpack.DefinePlugin({
	'process.env' : {
		'NODE_ENV' : '"production"'
	}
});
var HtmlWebpackPlugin = require('html-webpack-plugin');
var htmlWebpackPluginInstance = new HtmlWebpackPlugin({
	inject : true,
	template : 'index.html'
});
module.exports = {
	entry : {
		// common : [ 'react', 'react-dom' ],
		app : './src/js/index.js'
	},
	output : {
		filename : '[name]_[hash].js'
	},
	plugins : [ assetsPluginInstance, extractTextPluginInstance,
			definePluginInstance, cmmonsChunkPluginInstance,
			htmlWebpackPluginInstance ],
	resolve : {
		modulesDirectories : [ 'src', 'node_modules' ]
	},
	externals : {
		'moment' : true,
		'jquery' : 'jQuery',
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