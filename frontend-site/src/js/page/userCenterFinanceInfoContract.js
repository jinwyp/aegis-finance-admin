/**
 * Created by JinWYP on 8/12/16.
 */

import avalon from 'avalon2';
import '../common-libs/avalonFilter';

import {getContractInfo} from 'js/service/http.js';

var financeInfo = ()=> {

    var url = window.location.href.match(/\/order\/\d{1,8}/);
    var financeInfoId = 0;
    if (url) financeInfoId = Number(url[0].split('/')[2]);


    var vm = avalon.define({
        $id   : 'financeInfoController',
        financeInfoId : financeInfoId,
        financeInfo : {},
        css : {
            status : false
        }
    });

    getContractInfo(financeInfoId).done(function( data, textStatus, jqXHR ) {
        if (data.success){
            vm.financeInfo = data.data;
        }else{

        }
    });

};


financeInfo();

export default financeInfo;

