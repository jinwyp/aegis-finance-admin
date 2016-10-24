/**
 * Created by JinWYP on 24/10/2016.
 */

import avalon from 'avalon2';
import $ from 'jquery';
import 'bootstrap/dist/js/bootstrap.js';

var login = () => {

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

export default login;

