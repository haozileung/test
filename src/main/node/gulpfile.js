'use strict';
var gulp = require('gulp');
var browserify = require('browserify');
var babelify = require('babelify');
var reactify = require('reactify');
var source = require('vinyl-source-stream');
var webserver = require('gulp-webserver');
var gulpif = require('gulp-if');
var uglify = require('gulp-uglify');
var less = require('gulp-less');
var source = require('vinyl-source-stream');
var rename = require('gulp-rename');
var glob = require('glob');
var es = require('event-stream');
var wiredep = require('wiredep').stream;
function done(err){
    if(err){
    console.log(err);}else {console.log("DONE");}
}

gulp.task('bower', function () {
  gulp.src('./views/index.html')
    .pipe(wiredep())
    .pipe(gulp.dest('./public'));
});

gulp.task('less', function () {
  return gulp.src('./assets/less/*.less')
    .pipe(less())
    .pipe(gulp.dest('./public/assets/css'));
});

gulp.task('browserify', function () {
    glob('assets/js/*.*', function (err, files) {
        if (err) { done(err); }
        var tasks = files.map(function (entry) {
            return browserify({ entries: [entry] }).transform({ global: true }, reactify).transform(babelify)
                .bundle()
                .pipe(source(entry))
                .pipe(gulpif("production" === process.env.NODE_ENV, uglify()))
                .pipe(rename({
                    extname: '.bundle.js'
                })).pipe(gulp.dest('public'));
        });
        es.merge(tasks).on('end', done);
    })
});

gulp.task('watch', function () {
    gulp.watch('assets/**/*.js', ['browserify'])
});

gulp.task('webserver', function () {
    gulp.src('./')
        .pipe(webserver({
            host: '127.0.0.1',
            livereload: true
        }));
});

gulp.task('default', ['browserify', 'watch', 'webserver']);