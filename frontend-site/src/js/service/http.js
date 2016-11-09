

import jQuery from 'jquery';


var prefix = '/api/financing'

var url = {
    financeOrderList : prefix + '/financeOrder'
}


var getFinanceOrderList = (query)=>{

    var params = jQuery.extend({}, query);

    return jQuery.ajax({
        url      : url.financeOrderList,
        method   : 'GET',
        dataType : 'json',
        data     : params,
    });

}

var getFinanceOrderInfo = (id)=>{

    return jQuery.ajax({
        url      : url.financeOrderList + '/' + id,
        method   : 'GET',
        dataType : 'json'
    });

}


export { getFinanceOrderList, getFinanceOrderInfo }