'use strict';
var gulp = require('gulp');
var browserify = require('browserify');
var babelify = require('babelify');
var reactify = require('reactify');
var source = require('vinyl-source-stream');
var webserver = require('gulp-webserver');
var less = require('gulp-less');
var source = require('vinyl-source-stream');
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
    glob('assets/js/*.js', function (err, files) {
        if (err) { done(err); }
        var tasks = files.map(function (entry) {
            return browserify({ entries: [entry] }).transform("babelify", {presets: ["es2015", "react"]})
                .bundle()
                .pipe(source(entry))
                .pipe(gulp.dest('public'));
        });
        es.merge(tasks).on('end', done);
    })
});

gulp.task('watch', function () {
    gulp.watch('assets/**/*.*', ['browserify']);
    gulp.watch('assets/less/*.less', ['less']);
    gulp.watch('views/*.html', ['bower']);
});

gulp.task('webserver', function () {
    gulp.src('./')
        .pipe(webserver({
            host: '127.0.0.1',
            livereload: true
        }));
});

gulp.task('default', ['browserify','less','bower', 'watch', 'webserver']);