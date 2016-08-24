/**
 * Created by JinWYP on 8/1/16.
 */

import slider from 'js/jquery-plugin/slide.js';
import {jQuery as $} from 'js/jquery-plugin/bootstrap.js';


var financeHome = () => {

    slider();

    var postApplyInfo = (query) => {

        var params = $.extend({}, query);
        $.ajax({
            url         : '/api/financing/apply',
            method      : 'POST',
            contentType : 'application/json;charset=utf-8',
            dataType    : 'json',
            data        : JSON.stringify(params),
            success     : (data)=> {
                if (data.success) {
                    $('.modal_1').modal('hide');
                    setTimeout(()=>{
                        $('.modal_2').modal();
                        $('#modalImg_2').removeClass('attention').addClass('yes');
                        $('#modalInfo_2').html('申请成功!');
                        $('#md_ok_2').val('确定').modal('hide');
                        $('#md_ok_2').click(()=>{ $('.modal_2').modal('hide') });
                    }, 500);
                } else {
                    window.location.href = data.error.message;
                }
            }
        });
    };


    $('.banner_link').click(()=> {
        $('html, body').animate({scrollTop:'1200px'},'500',function(){});
    });
    //modal
    $('#slide .type-buy').click(()=>{
        $('.modal_1').modal();
        $('#modalInfo_1').html('确定提交申请"煤易购"?');
        $('#md_ok_1').off();
        $('#md_ok_1').click(()=> {
            postApplyInfo({
                applyType : 'MYG'
            });
        });
    });
    $('#slide .type-melt').click(()=>{
        $('.modal_1').modal();
        $('#modalInfo_1').html('确定提交申请"煤易融"?');
        $('#md_ok_1').off();
        $('#md_ok_1').click(()=>{
            postApplyInfo({
                applyType : 'MYR'
            });
        });
    });
    $('#slide .type-loan').click(()=>{
        $('.modal_1').modal();
        $('#modalInfo_1').html('确定提交申请"煤易贷"?');
        $('#md_ok_1').off();
        $('#md_ok_1').click(()=>{
            postApplyInfo({
                applyType : 'MYD'
            });
        });
    });


};


financeHome();

export default financeHome;

