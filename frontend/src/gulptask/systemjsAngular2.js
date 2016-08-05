/**
 * Created by JinWYP on 8/1/16.
 */


var gulp   = require('gulp');
var eslint = require('gulp-eslint');
var shell  = require('gulp-shell');
var rev    = require('gulp-rev');


var sourcePath = {
    'ts'            : 'js/**/*.ts',
    'tsOutput'      : 'jsoutput/**/*.js',
    'jsConfig'      : 'js/systemjs.config.js',
    'components'    : 'node_modules/**/*'
};

var distPath = {
    'js'         : '../dist/jsoutput/',
    'jsConfig'   : '../dist/js/',
    'components' : '../dist/node_modules/',
    "manifest"   : "../dist/rev/"
};



// Lint JavaScript
gulp.task('esLint', function() {
    return gulp.src(sourcePath.ts)
    // eslint() attaches the lint output to the "eslint" property
    // of the file object so it can be used by other modules.
        .pipe(eslint({
            "env": {
                "browser": true,
                "es6": true
            },
            "parser": "typescript-eslint-parser",
            "parserOptions": {
                "ecmaVersion": 7,
                "sourceType": "module",
                "ecmaFeatures": {
                    "experimentalObjectRestSpread": true
                }
            },
            "extends": "eslint:recommended",
            "rules": {
                "no-console":0,
                "indent"          : ["error", 4],
                "linebreak-style" : ["error", "unix"],
                "quotes"          : ["error", "single"],
                "semi"            : ["error", "always"],
                "comma-dangle"    : ["error", "never"]
            }
        }))
        // eslint.format() outputs the lint results to the console.
        // Alternatively use eslint.formatEach() (see Docs).
        .pipe(eslint.format())
        // To have the process exit with an error code (1) on
        // lint error, return the stream and pipe to failAfterError last.
        .pipe(eslint.failAfterError());
});


gulp.task("ts", shell.task(['tsc']));


gulp.task('components', function() {
    gulp.src(sourcePath.components)
        .pipe(gulp.dest(distPath.components));
    gulp.src(sourcePath.jsConfig)
        .pipe(gulp.dest(distPath.jsConfig));
});




gulp.task('js-release', ['ts', 'components'], function(){
    return gulp.src(sourcePath.tsOutput)
        .pipe(rev())
        .pipe(gulp.dest(distPath.js))
        .pipe(rev.manifest('rev-manifest-js.json'))
        .pipe(gulp.dest(distPath.manifest) );
});



gulp.task('watchJs', ['ts'], function() {
    gulp.watch(sourcePath.ts, ['esLint', 'ts']);
});




