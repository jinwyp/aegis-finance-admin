/**
 * Created by JinWYP on 8/1/16.
 */

var gulp = require('gulp');
var del = require( 'del');


var distPath = {
    'all'  : '../dist/**/*',
    'html' : '../../src/main/resources/templates/dist/**/*',
    'stylesheets' : './css/stylesheets/**/*',
};

gulp.task('clean', function() {
    del.sync([distPath.all, distPath.html, distPath.stylesheets], {force:true});
});

