/**
 * Created by JinWYP on 8/12/16.
 */

import  {jQuery as $} from 'js/jquery-plugin/bootstrap.js';
import avalon from 'avalon2';

var financeInfo = (query)=> {

    var params = $.extend({}, query);

    $.ajax({
        url      : '/api/financing/applyInfo/11',
        method   : "GET",
        dataType : "json",
        data     : params,
        success  : (data)=> {
            if (data.success){
                vm.financeList = data.data;
            }else{

            }
        }
    })
    var vm = avalon.define({
        $id   : 'financeInfo',
        financeList : [],
        // css : {
        //     status : false
        // },

    });

};


financeInfo();

export default financeInfo;

