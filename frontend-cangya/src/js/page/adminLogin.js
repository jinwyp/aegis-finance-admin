/**
 * Created by JinWYP on 24/10/2016.
 */

var avalon = require('avalon2') ;
var $ = require('jquery') ;
require('bootstrap/dist/js/bootstrap.js');

var login = function() {

    var vm = avalon.define({
        $id: "loginController",
        user : {
            email : '222',
            password : '222'
        }
    });

    $("<div>这是jquery生成的,看看avalon好使不</div>").appendTo("body")

};


login();

module.exports = login;


