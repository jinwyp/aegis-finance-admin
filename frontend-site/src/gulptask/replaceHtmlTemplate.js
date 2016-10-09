/**
 * Created by JinWYP on 8/1/16.
 */


var gulp = require('gulp');

var revCollector = require('gulp-rev-collector');


var sourcePath = {
    'html'                             : '../../src/main/resources/templates/site/dist/**/*',
    "manifest"                         : "../dist/rev/*.json"
};


var distPath = {
    'html'                             : '../../src/main/resources/templates/site/dist/'
};



gulp.task('replaceTpl', ['sass-release', 'js-build-production'],  function () {
    // 替换CSS中的图片
    //gulp.src(['rev/**/*.json', 'dist/styles/**/*.css'])
    //    .pipe( plugins.revCollector() )
    //    .pipe( gulp.dest(distPaths.css) );

    // 替换Html模版文件
    gulp.src([sourcePath.manifest, sourcePath.html])
        .pipe( revCollector() )
        .pipe( gulp.dest(distPath.html) );
});
