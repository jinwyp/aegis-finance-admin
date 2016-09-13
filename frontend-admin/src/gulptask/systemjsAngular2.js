/**
 * Created by JinWYP on 8/1/16.
 */


var gulp   = require('gulp');
var eslint = require('gulp-eslint');
var exec   = require('child_process').exec;
var rev    = require('gulp-rev');

var ng2Templates = require('gulp-angular-embed-templates');
var minify = require('html-minifier').minify;

var sourcePath = {
    'ts'                 : 'js/**/*.ts',
    'tsOutput'           : 'jsoutput/**/*.js',
    'jsConfig'           : 'js/systemjs.config.js',
    'componentsTemplate' : 'js/components/**/*.html',
    'libs'         : 'node_modules/**/*'
};

var distPath = {
    'ts'                 : 'tssource-temp/',
    'js'                 : '../dist/jsoutput/',
    'jsConfig'           : '../dist/js/',
    'componentsTemplate' : 'jsoutput/components/',
    'componentsTemplateDist' : '../dist/jsoutput/components/',
    'libs'               : '../dist/node_modules/',
    "manifest"           : "../dist/rev/"
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
    exec('tsc', function (err, stdout, stderr) {
        console.log(stdout);
        console.log(stderr);
        cb(err);
    });
});


gulp.task('libs', function() {
    gulp.src(sourcePath.libs)
        .pipe(gulp.dest(distPath.libs));
    gulp.src(sourcePath.jsConfig)
        .pipe(gulp.dest(distPath.jsConfig));
});

gulp.task('componentsTemplate', function() {
    gulp.src(sourcePath.componentsTemplate)
        .pipe(gulp.dest(distPath.componentsTemplate));
});


gulp.task('injectTemplate', function() {

    function minifyTemplate(path, ext, file, cb) {
        try {
            var minifiedFile = minify(file, {
                collapseWhitespace: true,
                caseSensitive: true,
                removeComments: true,
                removeRedundantAttributes: true
            });
            cb(null, minifiedFile);
        }
        catch (err) {
            cb(err);
        }
    }

    function customFilePath(ext, file) {
        console.log("22222----: " ,ext, file)
        return file;
    }

    var defaultOptions = {
        base: '/js',                  // Angular2 application base folder
        target: 'es6',              // Can swap to es5
        indent: 4,                  // Indentation (spaces)
        useRelativePaths: false,     // Use components relative assset paths
        removeLineBreaks: false,     // Content will be included as one line
        templateExtension: '.html', // Update according to your file extension
        templateFunction: false,    // If using a function instead of a string for `templateUrl`, pass a reference to that function here
        // templateProcessor: minifyTemplate,
        // styleProcessor: function (path, ext, file, callback) {/* ... */},
        customFilePath: customFilePath,
        supportNonExistentFiles: false // If html or css file do not exist just return empty content
    };

    return gulp.src(sourcePath.ts)
        .pipe(inlineNg2Template(defaultOptions))
        .pipe(gulp.dest(distPath.ts));

});




gulp.task('js-release', ['componentsTemplate', 'ts', 'libs'], function(){
    return gulp.src(sourcePath.tsOutput)
        //.pipe(rev())
        .pipe(gulp.dest(distPath.js))
        .pipe(rev.manifest('rev-manifest-js.json'))
        .pipe(gulp.dest(distPath.manifest) );
});



gulp.task('watchJs', ['componentsTemplate', 'ts' ], function() {
    gulp.watch([sourcePath.ts, sourcePath.componentsTemplate], ['componentsTemplate', 'ts']);
});




