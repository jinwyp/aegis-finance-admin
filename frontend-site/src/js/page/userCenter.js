/**
 * Created by JinWYP on 8/12/16.
 */

import  {jQuery as $} from 'js/jquery-plugin/bootstrap.js';
import avalon from 'avalon2';

var userCenter = ()=> {

    var vm = avalon.define({
        $id   : 'test',
        // name  : '司徒正美',
        array : [
            {}
            ]
    });

};


userCenter();

export default userCenter;

