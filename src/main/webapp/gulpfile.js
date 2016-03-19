'use strict';
var gulp = require('gulp');
var browserify = require('browserify');
var rename = require('gulp-rename');
var babelify = require('babelify');
var source = require('vinyl-source-stream');
var webserver = require('gulp-webserver');
var less = require('gulp-less');
var source = require('vinyl-source-stream');
var glob = require('glob');
var es = require('event-stream');
var wiredep = require('wiredep').stream;
function done(err) {
    if (err) {
        console.log(err);
    } else {
        console.log("DONE");
    }
}
//{ 'ignorePath': '../public/' }
gulp.task('bower', function () {
    gulp.src('./views/**/*.html')
        .pipe(wiredep({ 'ignorePath': '../' }))
        .pipe(gulp.dest('WEB-INF/views'));
});

gulp.task('less', function () {
    return gulp.src('src/less/*.less')
        .pipe(less())
        .pipe(gulp.dest('assets/css'));
});

gulp.task('browserify', function () {
    glob('./src/js/*.js', function (err, files) {
        if (err) { done(err); }
        var tasks = files.map(function (entry) {
            return browserify({ entries: [entry] }).transform("babelify", { presets: ["es2015", "react"] })
                .bundle()
                .pipe(source(entry)).pipe(rename({dirname: "js"}))
                .pipe(gulp.dest('assets'));
        });
        es.merge(tasks).on('end', done);
    })
});

gulp.task('watch', function () {
    gulp.watch('assets/js/*.js', ['browserify']);
    gulp.watch('assets/less/*.less', ['less']);
    gulp.watch('views/**/*.html', ['bower']);
});

gulp.task('webserver', function () {
    gulp.src('./public/')
        .pipe(webserver({
            host: '127.0.0.1',
            livereload: true
        }));
});

gulp.task('default', ['browserify', 'less', 'bower', 'watch', 'webserver']);