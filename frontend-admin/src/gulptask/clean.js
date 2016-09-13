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
    'tsSourceTemp' : './tssource-temp/**/*'
};

gulp.task('clean', function() {
    del.sync([distPath.all, distPath.html, distPath.stylesheets, distPath.autoSprite, distPath.tsCompileJs, distPath.tsSourceTemp], {force:true});
});

