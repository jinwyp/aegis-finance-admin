define(['angular', 'kitt', 'kitt/user/user','kitt/tender/tender','kitt/tender/bid','kitt/custmanager/list','kitt/activity/activity', 'kitt/article/indnews','kitt/article/hotnews','kitt/article/videos','kitt/article/adverpic','kitt/article/focusimage','kitt/chart/chart','kitt/chart/market','kitt/supply/supply','kitt/demand','kitt/finance/certificate','kitt/dealer','kitt/order/order','kitt/customer','kitt/finance/finance','kitt/timeTask','kitt/supplier','kitt/order/mallOrder','kitt/operateauth','kitt/system/menu','kitt/system/configUser', 'kitt/system/center','kitt/port','kitt/android','kitt/aboutUs','kitt/publicPic','kitt/dataCenter/dataCenter','kitt/dataCenter/pricePort','kitt/userinfo/login', 'kitt/shop/shop', 'kitt/userinfo/weblog', 'kitt/customer/partner','kitt/quicktrade','kitt/system/weixin','kitt/user/cooperation','kitt/customer/friendlylink','kitt/customer/reservation','kitt/meeting/meeting','kitt/logistics/logistics','kitt/regionym', 'kitt/message/list', 'kitt/tender/bidTemplate', 'kitt/shop/coalmine', 'kitt/userinfo/fundAccount'],function(angular, kitt){
  return angular.module('index', ['kitt', 'kitt.user.user','kitt.tender.tender','kitt.tender.bid','kitt.custmanager.list','kitt.activity.list','kitt.article.indnews','kitt.article.hotnews','kitt.article.adverpic','kitt.article.focusimage','kitt.chart.chart','kitt.chart.market','kitt.article.videos','kitt.Supply.supply','kitt.demand','kitt.finance.certificate','kitt.dealer','kitt.order.order','kitt.customer','kitt.finance.finance','kitt.timeTask','kitt.supplier','kitt.order.mallOrder','kitt.operateauth','kitt.system.menu','kitt.system.configUser', 'kitt.center','kitt.port','kitt.android','kitt.aboutUs','kitt.publicPic','kitt.dataCenter.dataCenter','kitt.dataCenter.pricePort','kitt.userinfo.login', 'kitt.shop.shop', 'kitt.userinfo.weblog', 'kitt.customer.partner','kitt.quickTrade','kitt.system.weixin','kitt.user.cooperation','kitt.customer.friendlylink','kitt.customer.reservation','kitt.meeting.meeting','kitt.logistics.logistics','kitt.regionym', 'kitt.message.list', 'kitt.tender.bidtemplate', 'kitt.shop.coalmine', 'kitt.userinfo.fundAccount'])
    .config(function($routeProvider){
      $routeProvider
        .when('/', {
          templateUrl:'views/index.html',
          controller:'index.Index'})
        .otherwise({
          redirectTo:'/'
        })
    })
    .controller('index.Index', function($scope,$route){
        /*service({
          url:'/admin/getUserAuth',
          success:function(data){
           var operatecodeList = data.operatecodeList;
            if(operatecodeList != null) {
              window.operatecodeList = operatecodeList;
            }
          }
        });*/


    });


});
