'use strict';
var gulp = require('gulp');
var gulpif = require('gulp-if');
var watch = require('gulp-watch');
var clean = require('gulp-rimraf');
var jshint = require('gulp-jshint');
var jshintjsx = require('jshint-jsx');
var autoprefixer = require('gulp-autoprefixer');
var minifycss = require('gulp-cssnano');
var uglify = require('gulp-uglify');
var connect = require('gulp-connect');
var webpack = require('webpack-stream');
var filter = require('gulp-filter');
var config = require('./webpack.config');
var jshintconfig = {
	'node' : true,
	'browser' : true,
	'esnext' : true,
	'bitwise' : true,
	'camelcase' : true,
	'curly' : true,
	'eqeqeq' : true,
	'immed' : true,
	'indent' : 4,
	'latedef' : true,
	'newcap' : true,
	'noarg' : true,
	'quotmark' : 'single',
	'regexp' : true,
	'undef' : true,
	'unused' : true,
	'strict' : true,
	'trailing' : true,
	'smarttabs' : true,
	'jquery' : true,
	'linter' : require('jshint-jsx').JSXHINT
};

gulp.task('build', [ 'third' ], function() {
	var jsFilter = filter('**/*.js', {
		restore : true
	});
	var cssFilter = filter('**/*.css', {
		restore : true
	});
	return gulp.src('assets/**/*.js').pipe(jshint(jshintconfig)).pipe(
			jshint.reporter('default')).pipe(webpack(config)).pipe(jsFilter)
			.pipe(gulpif("production" === process.env.NODE_ENV, uglify()))
			.pipe(jsFilter.restore).pipe(cssFilter).pipe(
					autoprefixer('last 2 version')).pipe(
					gulpif("production" === process.env.NODE_ENV, minifycss()))
			.pipe(cssFilter.restore).pipe(gulp.dest('public'));
});

gulp.task('third', function() {
	return gulp.src('./third/**/*.*').pipe(gulp.dest('./public/assets/'));
});

gulp.task('clean', function() {
	return gulp.src([ './public' ], {
		read : false
	}).pipe(clean());
});

gulp.task('default', function() {
	gulp.start('build');
});
gulp.task('connect', function() {
	connect.server({
		root : 'public',
		livereload : true
	});
});
gulp.task('watch', [ 'connect', 'build' ], function() {
	gulp.watch([ './assets/**/*', './views/**/*.html' ], [ 'build' ]);
	watch('./public/**/*').pipe(connect.reload());
});