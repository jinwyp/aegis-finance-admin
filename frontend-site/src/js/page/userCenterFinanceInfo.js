/**
 * Created by JinWYP on 8/12/16.
 */

import  {jQuery as $} from 'js/jquery-plugin/bootstrap.js';
import avalon from 'avalon2';

var financeInfo = (query)=> {

    var url = window.location.href.match(/\/financing\/\d{1,8}/);
    if (url){var financeInfoId = Number(url[0].substr(11))}

    var vm = avalon.define({
        $id   : 'financeInfo',
        financeInfo : {},
        css : {
            status : false
        }
    });

    var getFinanceInfo = (id) => {

        $.ajax({
            url      : '/api/financing/applyInfo/' + id,
            method   : 'GET',
            dataType : 'json',
            success  : (data)=> {
                if (data.success){
                    vm.financeInfo = data.data;
                }else{

                }
            }
        });
    };

    getFinanceInfo(financeInfoId);

};


financeInfo();

export default financeInfo;

