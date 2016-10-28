/**
 * Created by JinWYP on 8/12/16.
 */

import  {jQuery as $} from 'js/jquery-plugin/bootstrap.js';
import avalon from 'avalon2';

var cangyaInfo = (query)=> {

    var url = window.location.href.match(/\/financing\/\d{1,8}/);
    if (url){var financeInfoId = Number(url[0].substr(11))}

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
            url      : '/api/financing/apply/' + id,
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

    getFinanceInfo(financeInfoId);

    //数字转大写
    avalon.filters.switchTxt = function (n) {
        if (n==''){
            return "";
        }else if(!/^(0|[1-9]\d*)(\.\d+)?$/.test(n)){
            return "数据非法";
        }

        var unit = "千百拾亿千百拾万千百拾元角分", str = "";
        n += "00";
        var p    = n.indexOf('.');
        if (p >= 0)
            n = n.substring(0, p) + n.substr(p + 1, 2);
        unit = unit.substr(unit.length - n.length);
        for (var i = 0; i < n.length; i++)
            str += '零壹贰叁肆伍陆柒捌玖'.charAt(n.charAt(i)) + unit.charAt(i);

        return str.replace(/零(千|百|拾|角)/g, "零").replace(/(零)+/g, "零").replace(/零(万|亿|元)/g, "$1").replace(/(亿)万|壹(拾)/g, "$1$2").replace(/^元零?|零分/g, "").replace(/元$/g, "万元整");
    };

//    赎回modal

    $("#getBack").click(() => {
        $(".getBack").modal();

    });



};


cangyaInfo();

export default cangyaInfo;

