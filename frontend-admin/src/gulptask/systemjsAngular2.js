/**
 * Created by JinWYP on 8/1/16.
 */


var gulp   = require('gulp');
var eslint = require('gulp-eslint');
var exec   = require('child_process').exec;
var rev    = require('gulp-rev');

var ng2Templates = require('gulp-angular-embed-templates');
var SystemJS     = require('systemjs-builder');
var uglify       = require('gulp-uglify');

var sourcePath = {
    'ts'                 :  ['js/**/*.ts', '!js/libs/**/*'],
    'tsOutput'           : ['../dist/jsoutput/**/*.js'],
    'componentsTemplate' : 'js/components/**/*.html',
    'systemJsConfig'     : 'js/*.js',
    'libs'               : 'libs/**/*'
};

var distPath = {
    'tsSourceWithHtmlTpl' : 'tssource-temp-prodution/',
    'tsTempOutput'        : 'jsoutput/',
    'componentsTemplate'  : 'jsoutput/components/',
    'systemJsConfig'      : 'jsoutput/',
    'js'                  : '../dist/jsoutput/',
    'libs'                : '../dist/libs/',
    "manifest"            : "../dist/rev/"
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


gulp.task("ts", function (cb) {
    exec('./node_modules/typescript/bin/tsc', function (err, stdout, stderr) {
        console.log(stdout);
        console.log(stderr);
        cb(err);
    });
});

gulp.task('componentsTemplate', function() {
    gulp.src(sourcePath.systemJsConfig)
        .pipe(gulp.dest(distPath.systemJsConfig));
    return gulp.src(sourcePath.componentsTemplate)
        .pipe(gulp.dest(distPath.componentsTemplate));
});






gulp.task('injectTemplate', function() {
    return gulp.src(sourcePath.ts)
        .pipe(ng2Templates({sourceType:'ts'}))
        .pipe(gulp.dest(distPath.tsSourceWithHtmlTpl));
});


gulp.task("ts-release", ['injectTemplate'], function (cb) {
    exec('./node_modules/typescript/bin/tsc --project tsconfig-build.json', function (err, stdout, stderr) {
        console.log(stdout);
        console.log(stderr);
        cb(err);
    });
});

gulp.task('js-bundle', ['ts-release'], function () {
    var builder = new SystemJS('', {
        paths    : {
            'npm:' : './node_modules/'
        },
        map      : {
            'jsoutput' : 'jsoutput', // 'dist',

            // angular bundles
            '@angular/core'                     : 'npm:@angular/core/bundles/core.umd.js',
            '@angular/common'                   : 'npm:@angular/common/bundles/common.umd.js',
            '@angular/compiler'                 : 'npm:@angular/compiler/bundles/compiler.umd.js',
            '@angular/platform-browser'         : 'npm:@angular/platform-browser/bundles/platform-browser.umd.js',
            '@angular/platform-browser-dynamic' : 'npm:@angular/platform-browser-dynamic/bundles/platform-browser-dynamic.umd.js',
            '@angular/http'                     : 'npm:@angular/http/bundles/http.umd.js',
            '@angular/router'                   : 'npm:@angular/router/bundles/router.umd.js',
            '@angular/forms'                    : 'npm:@angular/forms/bundles/forms.umd.js',

            // other libraries
            'rxjs'                       : 'npm:rxjs',
            'angular2-in-memory-web-api' : 'npm:angular2-in-memory-web-api'
        },
        packages : {
            'jsoutput': {defaultExtension: 'js'},
            'rxjs'                       : {defaultExtension : 'js'},
            'angular2-in-memory-web-api' : {main : './index.js', defaultExtension : 'js'}
        }
    });

    return builder.bundle('jsoutput/page/*.js - [jsoutput/**/*.js]', distPath.tsTempOutput + 'page/dependencies.bundle.js', { minify: false, sourceMaps: false}).then(function() {
        console.log('Js-release Build dependencies complete');

        return Promise.all([
            builder.bundle('jsoutput/page/login.js - jsoutput/page/dependencies.bundle.js', distPath.js + 'page/login.bundle.js', { minify: false, sourceMaps: false }),
            builder.bundle('jsoutput/page/home.js - jsoutput/page/dependencies.bundle.js', distPath.js + 'page/home.bundle.js', { minify: false, sourceMaps: false })
        ])
    }).then(function() {
        console.log('Js-release Build complete !!');
    }).catch(function(err) {
        console.log('Js-release Build error !! ');
        console.log(err);
    });

});


gulp.task('libs', ['js-bundle'], function() {
    gulp.src(sourcePath.systemJsConfig).pipe(gulp.dest(distPath.js));
    gulp.src(distPath.tsTempOutput + 'page/dependencies.bundle.js').pipe(gulp.dest(distPath.js + 'page'));
    return gulp.src(sourcePath.libs).pipe(gulp.dest(distPath.libs));
});


gulp.task('js-release', ['libs'], function(){
    return gulp.src(sourcePath.tsOutput)
        .pipe(uglify())
        .pipe(rev())
        .pipe(gulp.dest(distPath.js))
        .pipe(rev.manifest('rev-manifest-js.json'))
        .pipe(gulp.dest(distPath.manifest) );
});


gulp.task('watchJs', ['componentsTemplate', 'ts' ], function() {
    gulp.watch([sourcePath.ts, sourcePath.componentsTemplate], ['componentsTemplate', 'ts']);
});




