var path = require("path");
var ExtractTextPlugin = require('extract-text-webpack-plugin');
var webpack = require('webpack-stream').webpack;
var extractTextPluginInstance = new ExtractTextPlugin('[name].css');
var cmmonsChunkPluginInstance = new webpack.optimize.CommonsChunkPlugin({
	name: 'common',
});
var definePluginInstance = new webpack.DefinePlugin({
	'process.env': {
		'NODE_ENV': JSON.stringify(process.env.NODE_ENV)
	}
});
var HtmlWebpackPlugin = require('html-webpack-plugin');
var htmlWebpackPluginInstance = new HtmlWebpackPlugin({
	inject: true,
	template: './views/index.html'
});
var resolverPluginInstance = new webpack.ResolverPlugin(
	new webpack.ResolverPlugin.DirectoryDescriptionFilePlugin(
		".bower.json", ["main"]))
module.exports = {
	entry: {
		common: ['react', 'react-dom', './assets/js/lib/common.js'],
		app: './assets/js/index.js'
	},
	output: {
		filename: '[name].js'
	},
	plugins: [extractTextPluginInstance, definePluginInstance,
		cmmonsChunkPluginInstance, htmlWebpackPluginInstance,
		resolverPluginInstance],
	resolve: {
		modulesDirectories: ['assets/js', 'node_modules', 'bower_components'],
		extensions: ['', '.js', '.jsx', '.json']
	},
	externals: {
		'moment': true,
		'jquery': 'jQuery',
		'bootstrap': true,
		'layer': true,
		'ladate': true
	},
	module: {
		loaders: [
			{
				test: /\.css$/,
				loader: ExtractTextPlugin.extract('style', ['css'])
			},
			{
				test: /\.(js|jsx)$/,
				loader: 'babel',
				query: {
					presets: ['react', 'es2015']
				}
			},
			{
				test: /\.less$/,
				loader: ExtractTextPlugin.extract('less',
					['css', 'less'])
			},
			{
				test: /\.(jpe?g|png|gif|svg)$/i,
				loaders: [
					'image?{bypassOnDebug: true, progressive:true, optimizationLevel: 3, pngquant:{quality: "65-80"}}',
					'url?limit=10000']
			}, {
				test: /\.(woff|eot|ttf)$/i,
				loader: 'url'
			}]
	},
};