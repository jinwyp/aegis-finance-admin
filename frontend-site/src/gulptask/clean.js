/**
 * Created by JinWYP on 8/1/16.
 */

var gulp = require('gulp');
var del = require( 'del');


var distPath = {
    'all'  : '../dist/**/*',
    'html' : '../../src/main/resources/templates/dist/**/*',
    'stylesheets' : './css/stylesheets/**/*',
    'autoSprite' : './css/images/sprite/auto-sprite.*'
};

gulp.task('clean', function() {
    del.sync([distPath.all, distPath.html, distPath.stylesheets, distPath.autoSprite], {force:true});
});

