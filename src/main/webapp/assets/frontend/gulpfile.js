var gulp = require('gulp');
var clean = require('gulp-rimraf');
var autoprefixer = require('gulp-autoprefixer');
var minifycss = require('gulp-cssnano');
var jshint = require('gulp-jshint');
var uglify = require('gulp-uglify');
var rename = require('gulp-rename');
var livereload = require('gulp-livereload');
var webpack = require('webpack-stream');
var filter = require('gulp-filter');
var config = require('./webpack.config');
gulp.task('build', function() {
	var jsFilter = filter('**/*.js', {
		restore : true
	});
	var cssFilter = filter('**/*.css', {
		restore : true
	});
	return gulp.src('src/js/**/*.js').pipe(jshint('.jshintrc')).pipe(
			jshint.reporter('default')).pipe(webpack(config)).pipe(rename({
		suffix : '.min'
	})).pipe(jsFilter).pipe(uglify()).pipe(jsFilter.restore).pipe(cssFilter)
			.pipe(
					autoprefixer('last 2 version', 'safari 5', 'ie 8', 'ie 9',
							'opera 12.1', 'ios 6', 'android 4')).pipe(
					minifycss()).pipe(cssFilter.restore).pipe(
					gulp.dest('dist/'));
});

gulp.task('dev', function() {
	var jsFilter = filter('**/*.js', {
		restore : true
	});
	var cssFilter = filter('**/*.css', {
		restore : true
	});
	return gulp.src('src/js/**/*.js').pipe(jshint('.jshintrc')).pipe(
			jshint.reporter('default')).pipe(
			webpack({
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
						loader : ExtractTextPlugin.extract('style-loader',
								'css-loader')
					} ]
				},
			})).pipe(rename({
		suffix : '.min'
	})).pipe(gulp.dest('dist/'));
});

gulp.task('html-watch', function() {
	return gulp.src('index.html').pipe(livereload());
});

gulp.task('clean', function() {
	return gulp.src([ 'dist/' ], {
		read : false
	}).pipe(clean());
});

gulp.task('default', [ 'clean' ], function() {
	gulp.start('build');
});
gulp.task('watch', function() {
	livereload.listen();
	gulp.watch('src/**/*', [ 'dev' ]);
	gulp.watch('*.html', [ 'html-watch' ]);

});