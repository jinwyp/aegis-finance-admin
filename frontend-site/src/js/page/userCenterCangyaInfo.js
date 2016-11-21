/**
 * Created by JinWYP on 8/12/16.
 */

import  {jQuery as $} from 'js/jquery-plugin/bootstrap.js';
import avalon from 'avalon2';
import '../common-libs/avalonFilter';

var cangyaInfo = (query)=> {

    var urlPrefix = "http://192.168.1.180:2403/orders/";
    var url = window.location.href.match(/\/cangya\/\d{1,8}/);
    if (url){var id = Number(url[0].split('/')[2])}

    var vm = avalon.define({
        $id   : 'cangyaInfo',
        cangyaInfo : {},
        css : {
            status : false
        },
        money:''
    });

    var getFinanceInfo = (id) => {

        $.ajax({
            url      : urlPrefix + id,
            method   : 'GET',
            dataType : 'json',
            success  : (data)=> {
                console.log(data);
                if (data.success){
                    vm.cangyaInfo = data.data;
                }else{

                }
            }
        });
    };

    getFinanceInfo(id);


//    赎回modal

    $("#getBack").click(() => {
        $(".getBack").modal();

    });



};


cangyaInfo();

export default cangyaInfo;

