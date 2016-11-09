/**
 * Created by JinWYP on 8/12/16.
 */

import avalon from 'avalon2';
import '../common-libs/avalonFilter';

import {getFinanceOrderInfo} from 'js/service/http.js';

var financeInfo = ()=> {

    var url = window.location.href.match(/\/user\/\d{1,8}/);
    var financeInfoId = 0;
    if (url) financeInfoId = Number(url[0].split('/')[2]);
    console.log(financeInfoId);


    var vm = avalon.define({
        $id   : 'financeInfoController',
        financeInfoId : financeInfoId,
        financeInfo : {},
        css : {
            status : false
        }
    });

    getFinanceOrderInfo(financeInfoId).done(function( data, textStatus, jqXHR ) {
        if (data.success){
            vm.financeInfo = data.data;
        }else{

        }
    });

};


financeInfo();

export default financeInfo;

