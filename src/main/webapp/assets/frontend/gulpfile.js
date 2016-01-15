// 载入外挂
var gulp = require('gulp'),    
    autoprefixer = require('gulp-autoprefixer'),
    minifycss = require('gulp-cssnano'),
    jshint = require('gulp-jshint'),
    uglify = require('gulp-uglify'),
    imagemin = require('gulp-imagemin'),
    rename = require('gulp-rename'),
    clean = require('gulp-rimraf'),
    concat = require('gulp-concat'),
    cache = require('gulp-cache'),
    livereload = require('gulp-livereload'),
	webpack = require('webpack-stream'),
	config = require('./webpack.config');
 
// 样式
gulp.task('styles', function() { 
  return gulp.src('src/css/**/*.css')
    .pipe(concat('app.css'))
    .pipe(autoprefixer('last 2 version', 'safari 5', 'ie 8', 'ie 9', 'opera 12.1', 'ios 6', 'android 4'))
    .pipe(rename({ suffix: '.min' }))
    .pipe(minifycss())
    .pipe(gulp.dest('dist/'));
});
 
// 脚本
gulp.task('scripts', function() { 
  return gulp.src('src/js/index.js')
    .pipe(jshint('.jshintrc'))
    .pipe(jshint.reporter('default'))
	.pipe(webpack(config))
    .pipe(rename({ suffix: '.min' }))
    .pipe(uglify())
    .pipe(gulp.dest('dist/'));
});
 
// 图片
gulp.task('images', function() { 
  return gulp.src('src/img/**/*')
    .pipe(cache(imagemin({ optimizationLevel: 3, progressive: true, interlaced: true })))
    .pipe(gulp.dest('dist/'));
});
 
// 清理
gulp.task('clean', function() { 
  return gulp.src(['dist/'], {read: false})
    .pipe(clean());
});
 
// 预设任务
gulp.task('default', ['clean'], function() { 
    gulp.start('styles', 'scripts', 'images');
});
 
// 看守
gulp.task('watch', function() {
 
  // 看守所有.scss档
  gulp.watch('src/css/**/*.css', ['styles']);
 
  // 看守所有.js档
  gulp.watch('src/js/**/*.js', ['scripts']);
 
  // 看守所有图片档
  gulp.watch('src/img/**/*', ['images']);
 
  // 建立即时重整伺服器
  var server = livereload();
 
  // 看守所有位在 dist/  目录下的档案，一旦有更动，便进行重整
  gulp.watch(['dist/**']).on('change', function(file) {
    server.changed(file.path);
  });
 
});