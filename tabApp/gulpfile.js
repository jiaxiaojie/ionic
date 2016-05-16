var gulp = require('gulp');
var gutil = require('gulp-util');
var bower = require('bower');
var concat = require('gulp-concat');
var sass = require('gulp-sass');
var minifyCss = require('gulp-minify-css');
var rename = require('gulp-rename');
var sh = require('shelljs');



    // 引用 http 模块
    var http = require("http");
     
    // 引用 filestream 模块
    var fs = require("fs");
     
    // 引用 url 模块
    var url = require("url")
     
    // 引用 querystring 模块
    var querystring = require("querystring")
     
    http.createServer(function (request, response) {
      var objQuery = querystring.parse(url.parse(request.url).query);
     
      // 读取文件
      if (objQuery.type == "read") {
        // 为什么不是 fs.read
        fs.readFile("./www/js/templates/javscript", function (error, fileData) {
          if (error) {
            write(response, "<h1>读取出现错误</h1>");
          } else {
            write(response, "<h1>读取内容为：</h1>" + fileData);
          }
        });
      }
    }).listen(8100);
     
    function write(response, content) {
      response.writeHead(200, {
        "content-type": "text/html"
      });
      response.write(content);
      response.end();
    }

var paths = {
  sass: ['./scss/**/*.scss']
};

gulp.task('default', ['sass']);

gulp.task('sass', function(done) {
  gulp.src('./scss/ionic.app.scss')
    .pipe(sass())
    .on('error', sass.logError)
    .pipe(gulp.dest('./www/css/'))
    .pipe(minifyCss({
      keepSpecialComments: 0
    }))
    .pipe(rename({ extname: '.min.css' }))
    .pipe(gulp.dest('./www/css/'))
    .on('end', done);
});

gulp.task('watch', function() {
  gulp.watch(paths.sass, ['sass']);
});

gulp.task('install', ['git-check'], function() {
  return bower.commands.install()
    .on('log', function(data) {
      gutil.log('bower', gutil.colors.cyan(data.id), data.message);
    });
});

gulp.task('git-check', function(done) {
  if (!sh.which('git')) {
    console.log(
      '  ' + gutil.colors.red('Git is not installed.'),
      '\n  Git, the version control system, is required to download Ionic.',
      '\n  Download git here:', gutil.colors.cyan('http://git-scm.com/downloads') + '.',
      '\n  Once git is installed, run \'' + gutil.colors.cyan('gulp install') + '\' again.'
    );
    process.exit(1);
  }
  done();
});



gulp.task('build:json', function () {
    var eventPath = './www/templates/javscript';
    var reg = /^*.tpl$/;
    fs.readdir(eventPath, function(err, files) {
        console.info(files)
    });
});
