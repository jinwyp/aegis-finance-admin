/**
 * Created by JinWYP on 8/1/16.
 */


var gulp         = require('gulp');
var runSequence  = require('run-sequence');
var spritesmith  = require('gulp.spritesmith');
var sass         = require('gulp-sass');
var autoprefixer = require('gulp-autoprefixer');
var cleanCss     = require('gulp-clean-css');
var rev          = require('gulp-rev');



var sourcePath = {
    'html'          : '../../src/main/resources/templates/site/src/**/*',
    'images'        : 'css/images/**/*',
    'imagesSprites' : 'css/images/sprite/icon/**/*',
    'scss'          : 'css/sass/**/*.scss',
    'css'           : 'css/stylesheets'
};

var distPath = {
    'html'                             : '../../src/main/resources/templates/site/dist/',
    'images'                           : '../dist/css/images/',
    'imagesSprites'                    : './css/images/sprite/auto-sprite.png',
    'imagesSpritesOutput'              : 'css/sass/helpers/_auto_sprite.scss',
    'imagesSpritesOutputReferringPath' : '/static/site/css/images/sprite/auto-sprite.png',
    'css'                              : '../dist/css/stylesheets/',
    'manifest'                         : '../dist/rev/'
};





// Html Views
gulp.task('htmlTemplate', function() {
    gulp.src(sourcePath.html)
        .pipe(gulp.dest(distPath.html))
});


// Optimize images
gulp.task('images', function() {
    gulp.src(sourcePath.images)
        .pipe(gulp.dest(distPath.images))
});



gulp.task('sprite', function () {
    var spriteData = gulp.src(sourcePath.imagesSprites).pipe(spritesmith({
        imgName:  distPath.imagesSprites ,
        imgPath: distPath.imagesSpritesOutputReferringPath,
        cssName:  distPath.imagesSpritesOutput ,
        cssFormat:  'scss',
        algorithm : 'alt-diagonal',
        padding: 20
    }));
    return spriteData.pipe(gulp.dest(''));
});


// Compile css and automatically prefix stylesheets
gulp.task('sass', ['sprite'], function() {
    gulp.src(sourcePath.scss)
        .pipe(sass({
            precision       : 10,
            outputStyle     : 'compact',
            errLogToConsole : true
        }).on('error', sass.logError))
        //.pipe(autoprefixer({
        //    browsers: ['> 1%', 'Last 2 versions', 'IE 8'],
        //    cascade: false
        //}))
        //.pipe(cleanCss({compatibility: 'ie8'}))
        .pipe(gulp.dest(sourcePath.css))
});



gulp.task('sass-release', ['htmlTemplate', 'sprite'], function(done) {

    runSequence('images', function() {
        gulp.src(sourcePath.scss)
            .pipe(sass({
                precision       : 10,
                outputStyle     : 'compressed',
                errLogToConsole : true
            }).on('error', sass.logError))
            //.pipe(autoprefixer({
            //    browsers: ['> 1%', 'Last 2 versions', 'IE 8'],
            //    cascade: false
            //}))
            //.pipe(cleanCss({compatibility: 'ie8'}))
            .pipe(rev())
            .pipe(gulp.dest(distPath.css))
            .pipe(rev.manifest('rev-manifest-css.json'))
            .pipe(gulp.dest(distPath.manifest) );
        done();
    });

});







gulp.task('watchSass', function() {
    gulp.watch(sourcePath.scss, ['sass']);
});