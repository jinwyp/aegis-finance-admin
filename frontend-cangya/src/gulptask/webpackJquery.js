/**
 * Created by JinWYP on 8/1/16.
 */

var exec   = require('child_process').exec;
var gulp   = require('gulp');
var eslint = require('gulp-eslint');

var rev = require('gulp-rev');


var sourcePath = {
    'js'            : 'js/**/*',
    'jsPage'        : 'js/page/**/*',
    'components'    : 'js/libs/**/*'
};

var distPath = {
    'js'                               : '../dist/js/',
    'jsPage'                           : '../dist/js/page/',
    'jsPageDevTemp'                    : 'js/page-temp-bundle/',
    'components'                       : '../dist/js/libs/',
    "manifest"                         : "../dist/rev/"
};



// Lint JavaScript
gulp.task('esLint', function() {
    return gulp.src(sourcePath.js)
        .pipe(eslint({
            "env": {
                "browser": true
            },
            "extends": "eslint:recommended",
            "rules": {
                "no-console":0,
                "indent"          : ["error", 4],
                "linebreak-style" : ["error", "unix"],
                "quotes"          : ["error", "single"],
                "semi"            : ["error", "always", { "omitLastInOneLineBlock": true}],
                "no-extra-semi"   : ["error"],
                "comma-dangle"    : ["error", "never"]
            },
            "parserOptions": {
                "ecmaVersion": 6,
                "sourceType": "module"

            },
        }))
        .pipe(eslint.format())
        .pipe(eslint.failOnError())
});


gulp.task('components', function(cb) {
    exec('npm run build', function (err, stdout, stderr) {
        console.log(stdout);
        console.log(stderr);
        cb(err);
    });

    gulp.src(sourcePath.components)
        .pipe(gulp.dest(distPath.components));
});


gulp.task('js-build-production', ['components'], function(){
    return gulp.src(distPath.jsPageDevTemp+'**/*.js')
        .pipe(rev())
        .pipe(gulp.dest(distPath.jsPage))
        .pipe(rev.manifest('rev-manifest-js.json'))
        .pipe(gulp.dest(distPath.manifest) );
});



gulp.task('js-build-dev', function(cb) {
    exec('npm run webpack', function (err, stdout, stderr) {
        console.log(stdout);
        console.log(stderr);
        cb(err);
    });
});


gulp.task('copydpd', function(cb) {
    exec('npm run build', function (err, stdout, stderr) {
        console.log(stdout);
        console.log(stderr);
        cb(err);
    });

    gulp.src('*.html')
        .pipe(gulp.dest('../mockserver/public/'));
    gulp.src(sourcePath.components)
        .pipe(gulp.dest('../mockserver/public/js/libs/'));
    gulp.src(distPath.jsPageDevTemp+'**/*.js')
        .pipe(gulp.dest('../mockserver/public/js/'));
});


gulp.task('watchJs', [ 'js-build-dev'],function() {
    gulp.watch(sourcePath.js, ['esLint', 'js-build-dev']);
});




