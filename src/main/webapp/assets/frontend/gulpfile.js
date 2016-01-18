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
	return gulp.src('src/js/**/*.js').pipe(webpack(config)).pipe(jsFilter)
			.pipe(uglify()).pipe(jsFilter.restore).pipe(cssFilter).pipe(
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
	return gulp.src('src/js/**/*.js').pipe(webpack(config)).pipe(cssFilter)
			.pipe(autoprefixer('last 2 version')).pipe(cssFilter.restore).pipe(
					gulp.dest('dist/')).pipe(livereload());
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