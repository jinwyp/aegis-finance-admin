/**
 * Created by JinWYP on 8/1/16.
 */

var gulp = require('gulp');
var del = require( 'del');


var distPath = {
    'all'  : '../dist/**/*',
    'html' : '../../src/main/resources/templates/admin/dist/**/*',
    'stylesheets' : './css/stylesheets/**/*',
    'autoSprite' : './css/images/sprite/auto-sprite.*',
    'tsCompileJs' : './jsoutput/**/*',
    'tsSourceWithHtmlTpl' : './tssource-temp-prodution/**/*',
    'tsCompileJsBuild' : './jsoutput-temp-prodution/**/*'
};

gulp.task('clean', function() {
    del.sync([distPath.all, distPath.html, distPath.stylesheets, distPath.autoSprite,
        distPath.tsCompileJs, distPath.tsCompileJsBuild, distPath.tsSourceWithHtmlTpl], {force:true});
});

