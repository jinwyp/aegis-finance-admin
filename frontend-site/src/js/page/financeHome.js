/**
 * Created by JinWYP on 8/1/16.
 */


import  {jQuery as $} from 'js/jquery-plugin/bootstrap.js';


var financeHome = () => {

    //轮播图图片及链接
    var posterTvGrid86804 = new window.posterTvGrid('posterTvGrid86804', {className : 'posterTvGrid'}, [
        {
            'img'   : '/static/site/css/images/finance/type-loan.jpg',
            'title' : '煤易贷',
            'url'   : '/static/site/css/images/finance/type-loan.jpg'
        },
        {
            'img'   : '/static/site/css/images/finance/type-melt.jpg',
            'title' : '煤易融',
            'url'   : '/static/site/css/images/finance/type-melt.jpg'
        },
        {
            'img'   : '/static/site/css/images/finance/type-buy.jpg',
            'title' : '煤易购',
            'url'   : '/static/site/css/images/finance/type-buy.jpg'
        },
        {
            'img'   : '/static/site/css/images/finance/type-loan.jpg',
            'title' : '煤易贷',
            'url'   : '/static/site/css/images/finance/type-loan.jpg'
        }]
    );

    var postApplyInfo = (query) => {

        var params = $.extend({}, query);
        $.ajax({
            url         : '/api/financing/applyInfo',
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

    //modal
    $('.banner_link').click(function () {
        $('.modal_1').modal();
    });

    $('#md_ok_1').click(function () {
        postApplyInfo({
            applyType : ''
        });
    });


};


financeHome();

export default financeHome;

