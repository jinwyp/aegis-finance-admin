/**
 * Created by JinWYP on 8/1/16.
 */


import 'js/jquery-plugin/slide.js';
import  {jQuery as $} from 'js/jquery-plugin/bootstrap.js';


var financeHome = () => {
    // $('.test').html('jquery works');
    //$('#type li').eq(1).addClass('active');

    // jQuery(".slideTxtBox").slide();
    // var $assetPath = "/static";

    //轮播图图片及链接
    var posterTvGrid86804 = new posterTvGrid('posterTvGrid86804', {className : 'posterTvGrid'}, [
            {'img' : '/static/site/css/images/finance/type-loan.jpg',
                'title' : '煤易贷',
                'url' : '/static/site/css/images/finance/type-loan.jpg'
            },
            {'img' : '/static/site/css/images/finance/type-melt.jpg',
                'title' : '煤易融',
                'url' : '/static/site/css/images/finance/type-melt.jpg'
            },
            {'img' : '/static/site/css/images/finance/type-buy.jpg',
                'title' : '煤易购',
                'url' : '/static/site/css/images/finance/type-buy.jpg'
            },
            {'img' : '/static/site/css/images/finance/type-loan.jpg',
                'title' : '煤易贷',
                'url' : '/static/site/css/images/finance/type-loan.jpg'
            }

        ]
    );

    //modal
    $('.banner_link').click(function () {
        $('.modal_1').modal();
    });


};


financeHome();

export default financeHome;

