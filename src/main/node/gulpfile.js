var gulp = require('gulp');
var clean = require('gulp-rimraf');
var autoprefixer = require('gulp-autoprefixer');
var minifycss = require('gulp-cssnano');
var uglify = require('gulp-uglify');
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
	return gulp.src('assets/**/*.js').pipe(webpack(config)).pipe(jsFilter).pipe(
			uglify()).pipe(jsFilter.restore).pipe(cssFilter).pipe(
			autoprefixer('last 2 version')).pipe(minifycss()).pipe(
			cssFilter.restore).pipe(gulp.dest('public'));
});

gulp.task('dev', function() {
	var jsFilter = filter('**/*.js', {
		restore : true
	});
	var cssFilter = filter('**/*.css', {
		restore : true
	});
	return gulp.src('assets/**/*.js').pipe(webpack(config)).pipe(cssFilter).pipe(
			autoprefixer('last 2 version')).pipe(cssFilter.restore).pipe(
			gulp.dest('./public')).pipe(livereload());
});

gulp.task('html-watch', function() {
	return gulp.src('./views/**/*.html').pipe(livereload());
});

gulp.task('clean', function() {
	return gulp.src([ './public' ], {
		read : false
	}).pipe(clean());
});

gulp.task('default', [ 'clean' ], function() {
	gulp.start('build');
});
gulp.task('watch', function() {
	livereload.listen();
	gulp.watch('./assets/**/*', [ 'dev' ]);
	gulp.watch('./views/**/*.html', [ 'html-watch' ]);

});