<!DOCTYPE html >
<html lang="zh-cmn-Hans">
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" /> <!-- 优先使用 IE 最新版本和 Chrome -->
    <meta name="renderer" content="webkit"> <!--360浏览器就会在读取到这个标签后，立即切换对应的内核。并将这个行为应用于这个二级域名下所有网址。-->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Cache-Control" content="no-siteapp" />

    <title>${title}</title>

    <link rel="stylesheet" href="${staticPath}/jspm_packages/github/twbs/bootstrap@3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="${staticPath}/css/stylesheets/main.css">



    <!-- HTMLshiv for IE -->
    <!--[if lte IE 9]>
    <script src="${staticPath}/jspm_packages/html5shiv/dist/html5shiv.min.js"></script>
    <![endif]-->

    <link type="image/x-icon" rel="shortcut icon" href="${staticPath}/css/images/favicon.ico" />

    <meta name="keywords" content="易煤网">
    <meta name="description" content="易煤网">

    <link rel="stylesheet" href="${staticPath}/css/stylesheets/page/financeHome.css">
</head>
<body>
<!-- header start -->
<div id="paymentHeader">
    <div class="headWrap">
            <div class="wrap1200">
                <ul class="menuList leftMenuList">
                    <li class="noborder">24小时贵宾热线</li>
                    <li  class="noborder red">400-960-1180</li>
                </ul>

                <ul class="menuList rightMenuList">
                    
                    <li>您好!<a href="" class="blue">请登录</a></li>
                    <li><a href="">免费注册</a></li>
                    <li><a href="">我的易煤网</a></li>
                    <li class="hoverDiv">
                        <a href="javascript:void(0);" class="hoverTitle">网站导航</a>
                        <div class="Nav hideMenu">
                            <div class="floatL">
                                <h2><span class="headBg"></span>交易服务</h2>
                                <table>
                                    <tr>
                                        <td><a href="">商城</a></td>
                                        <td><a href="">供应专区</a></td>
                                        <td><a href="">需求专区</a></td>
                                    </tr>
                                    <tr>
                                        <td><a href="">金融</a></td>
                                        <td><a href="">阳光采购</a></td>
                                        <td><a href="">煤矿专区</a></td>
                                    </tr>
                                    <tr>
                                        <td><a href="">资讯</a></td>
                                        <td><a href="">物流</a></td>
                                        <td><a href="">数据中心</a></td>
                                    </tr>
                                    <!--<tr>-->
                                        <!--<td colspan="3"><a href="#">易煤指数</a></td>-->

                                    <!--</tr> -->
                                </table>
                            </div>

                            <div class="floatR">
                                <h2><span class="headBg"></span>我的易煤网</h2>
                                <table  class="noborder">
                                    <tr>
                                        <td><a href="">业务管理中心</a></td>
                                    </tr>
                                    <tr>
                                        <td><a href="">财务管理中心</a></td>
                                    </tr>
                                    <tr>
                                        <td><a href="">账户设置</a></td>
                                    </tr>

                                </table>
                            </div>

                        </div>

                    </li>
                    <li  class="hoverDiv">
                        <a href="javascript:void(0);" class="hoverTitle">手机助手</a>
                        <div class="phoneHelper hideMenu">
                            <div class="floatL">
                                <h2>易煤助手</h2>
                                <img src="${staticPath}/css/images/header/yimei_helper.png" alt="易煤助手">
                            </div>
                            <div class="floatR">
                                <h2>易煤资讯</h2>
                                <img src="${staticPath}/css/images/header/yimei_news.png" alt="易煤资讯">
                            </div>
                        </div>
                    </li>
                    <li class="noborder paddingRight0"><a href="">关于我们</a></li>
                </ul>
            </div>
        </div>
</div>
<!-- header stop -->

<!--nav start-->
<div class="g-logo-bar-wrap">
    <div class="g-logo-bar" >
        <div class="g-logo">
            <a href="/" ><img src="${staticPath}/css/images/header/logo.png" width="365" height="45" /></a>
        </div>

        <div class="g-nav">
            <ul class="clearfix">
                <li class=" normalLi"><a href="/" class="navFist">首页</a></li>
                <li class=" slideDl-left"><a href="/mall" class="navFist">商城</a><i class="ico_hot"></i></li>
                <li class="slideDl">
                    <a href="javascript:void(0);" class="navFist">撮合 <i class="ico_down"></i></a>
                    <dl class="slide">
                        <dd class=""><a href="/buy"><i class="block"></i>供应专区</a></dd>
                        <dd class="noborder"><a href="/sell"><i class="block"></i>需求专区</a></dd>
                    </dl>
                </li>
                <li class=" slideDl-right"><a href="/tender" class="navFist">阳光采购</a></li>
                <li class=" normalLi"><a href="/coalzone" class="navFist">煤矿专区</a></li>
                <li class="g-nav-finance  slideDl-left active" ><a href="/finance" class="navFist">金融</a></li>
                <li  class="slideDl">
                    <a href="javascript:void(0);" class="navFist">资讯 <i class="ico_down"></i></a>
                    <dl class="slide">
                        <dd class=""><a href="/news"><i class="block"></i>资讯</a></dd>
                        <dd class="noborder"><a href="/dataCenter"><i class="block"></i>数据</a></dd>
                    </dl>
                </li>
                <li class=" lastLi slideDl-right" ><a href="https://56.yimei180.com/logistics/toLogistics" class="navFist">物流</a></li>

            </ul>
        </div>

    </div>
</div>
<!--nav stop-->

<!--banner start-->
<div class="banner_finance">
    <form action="">
        <button class="banner_link">
            <span class="link_light"></span>
            <span class="link_circle"></span>
            <span class="link_trigle"></span>
        </button>
    </form>
</div>
<!--banner stop-->

<!--申请流程开始-->
<div class="flow-chart">
    <div class="steps">
        <div class="step1">
            <span class="img step1-img"></span>
            <h3>提交申请</h3>
            <p>注册后完善信息并提交申请</p>
        </div>
        <div class="step-trigle"></div>
        <div class="step2">
            <span class="img step1-img"></span>
            <h3>等待审核</h3>
            <p>工作人员需要审核您的资料</p>
        </div>
        <div class="step-trigle"></div>
        <div class="step3">
            <span class="img step1-img"></span>
            <h3>融资成功</h3>
            <p>审核通过后等待融资完成</p>
        </div>
    </div>
</div>
<!--申请流程结束-->
<!--融资类型开始-->
<div class="type-finance">
    <div class="type">
        <h2></h2>

        <div id="posterTvGrid86804" style="margin:40px auto 0 auto;"></div>
    </div>
</div>
<!--融资类型结束-->

<!--footer start-->
<div id="J_intergalFooter">
    <div class="intergalFooter">
        <div class="footWrapper">
            <ul class="mainmenu">
                <li class="maintit">
                    <div class="pd0">
                        <p>新手指导</p>
                        <ul>
                            <li><a href="/teach/register">免费注册认证</a></li>
                            <li><a href="/teach/buy">如何买煤</a></li>
                            <li><a href="/teach/sell">如何卖煤</a></li>
                            <li><a href="/teach/logisticsLink">物流指导</a></li>
                            <li><a href="/teach/questions">常见问题</a></li>
                        </ul>
                    </div>
                </li>
                <li class="maintit">
                    <div>
                        <p>行情资讯</p>
                        <ul>
                            <li><a href="/news/hyyw">行业要闻</a></li>
                            <li><a href="/news/zczz">政策追踪</a></li>
                            <li><a href="/news/djsd">独家视点</a></li>
                        </ul>
                    </div>
                </li>
                <li class="maintit">
                    <div>
                        <p>增值服务</p>
                        <ul>
                            <li><a href="/finance">金融服务</a></li>
                            <li><a href="/group">易煤团购</a></li>
                        </ul>
                    </div>
                </li>
                <li class="maintit">
                    <div>
                        <p>关于我们</p>
                        <ul>
                            <li><a href="/aboutUs/#advantage">易煤优势</a></li>
                            <li><a href="/aboutUs/#guarantee">资金保证</a></li>
                            <li><a href="/aboutUs/invite">招贤纳士</a></li>
                        </ul>
                    </div>
                </li>

                <li class="maintit maintit-kf">
                    <div>
                        <p>客户服务</p>
                        <ul>
                            <li>
                                <a href="http://wpa.b.qq.com/cgi/wpa.php?ln=1&amp;key=XzkzODA0MTkwOV8yOTk2ODhfNDAwOTYwMTE4MF8yXw" target="_blank" class="onlineService" rel="nofollow">
                                    <span></span>在线客服
                                </a>
                            </li>
                            <li class="servicePhone"><span></span>免费热线</li>
                            <li>
                                <i class="serviceTel">400-960-1180</i>
                            </li>
                        </ul>
                    </div>
                </li>
                <li class="maintit weixin">
                    <p>易煤助手</p>
                    <img src="${staticPath}/css/images/header/yimei_helper.png" alt="易煤助手" title="扫一扫易煤助手" width="99" height="99">
                </li>
                <li class="maintit weixin weixin2">
                    <p>易煤资讯</p>
                    <img src="${staticPath}/css/images/header/yimei_news.png" alt="易煤资讯" title="扫一扫易煤资讯"  width="99" height="99">
                </li>
            </ul>
        </div>
    <div class="footerInfo">
    <!--友情链接开始-->
        <ul class="footerInfo-fl">
            <li>友情链接：</li>
            <li><a href="http://www.chd.com.cn/#" target="_blank">中国华电集团公司</a></li>
            <li><a href="http://www.cqcoal.com/" target="_blank">秦皇岛煤炭网</a></li>
            <li><a href="http://www.cwestc.com/#" target="_blank">中国煤炭新闻网</a></li>
            <li><a href="http://www.cctd.com.cn/#" target="_blank">中国煤炭市场网</a></li>
            <li><a href="http://www.sxcoal.com/" target="_blank">中国煤炭资源网</a></li>
            <li><a href="http://www.56kuaiche.com/#" target="_blank">56快车</a></li>
            <li><a href="http://www.nhxh.cn/#" target="_blank">北部湾商品交易网</a></li>
            <li><a href="http://www.snctc.cn/#" target="_blank">陕西煤炭交易中心</a></li>
            <li><a href="http://www.imcec.cn/#" target="_blank">内蒙古煤炭交易中心</a></li>
            <li><a href="http://www.sspp.co/" target="_blank">超级船东</a></li>
            <li><a href="/teach/friendlylink" target="_new">更多...</a></li>
        </ul>
    <!--友情链接结束-->




        <p>© 2015 易煤网 All rights reserved   |   和略电子商务(上海)有限公司 ｜ 沪ICP备14052754号</p>
        <p class="szfw-wrap">
            <a href="https://www.sgs.gov.cn/lz/licenseLink.do?method=licenceView&entyId=1atr5hendjiu232trv8vb6qred7d9yn01mg1nbic8l4vjp1q8m" target="_blank" rel="nofollow">
                <img src="${staticPath}/css/images/footer/shgs.png" height="31"  width="90" />
            </a>
            &nbsp;
            <a id="___szfw_logo___" href="https://credit.szfw.org/CX20150831011158100383.html" target="_blank" rel="nofollow">
                <img src="${staticPath}/css/images/footer/cxwz.png" height="31"  width="90" />
            </a>
        </p>
    </div>
</div>
</div>
<!--footer stop-->

<!--modal start-->
<!--两个按钮取消、确认 modal-->
<div class="container modalPublic">
    <div class="row clearfix">
        <div class="modal_1 modal fade" data-backdrop="static" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button"  class="close close_modal" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
                        <h4 class="modal-title" id="modal_title_1">融资申请提示</h4>
                    </div>
                    <div class="modal-body">
                        <div>
                            <div class="bg_img question" id="modalImg_1"></div>
                            <div class="modalInfo">
                                <span id="modalInfo_1" >确定提交该融资申请？</span>
                            </div>
                            <div class="tip">
                                <em>
                                    *申请完成后,我们的服务人员将会与您联系,并帮您完成<br>&nbsp;&nbsp;申请材料的填写.
                                </em>
                                <!--企业信息不完善!  img-attention-->
                                <!--您需要先完善企业信息后才能做融资申请哦～-->
                                <!--按钮 取消 马上去完善-->
                            </div>
                            <div class="buttons">
                                <input type="button" data-dismiss="modal" class="btn cancel" value="取消" id="md_cancel_1">
                                <input type="button" class="btn confirm" value="确认" id="md_ok_1">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!--一个按钮确认 modal-->
<div class="container modalPublic">
    <div class="row clearfix">
        <div class="modal_2 modal fade" data-backdrop="static" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button"  class="close close_modal" data-dismiss="modal" aria-label="Close"></button>
                        <h4 class="modal-title" id="modal_title_2">融资申请提示</h4>
                    </div>
                    <div class="modal-body">
                        <div>
                            <div class="bg_img attention" id="modalImg_2"></div>
                            <div class="modalInfo">
                                <span id="modalInfo_2" >您当天的申请已超过限定次数!</span>
                            </div>
                            <div class="buttons">
                                <!--<input type="button" data-dismiss="modal" class="btn cancel" value="取消" id="md_cancel_1">-->
                                <input type="button" class="btn confirm ml0" value="我知道了" id="md_ok_2">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!--modal stop-->



<script src="${staticPath}/jspm_packages/system.js"></script>
<script src="${staticPath}/js/config.js"></script>



<#if env == 'staging' || env == 'prod' >
<!-- Remove this statement if you want to run the on the fly transpiler -->
<!-- 生产环境使用 bundle.js 文件 -->
<script src="${staticPath}/js/page/financeHome.bundle.js"></script>
</#if>

<script>
    System.import('${staticPath}/js/page/financeHome.js')
</script>

</body>
</html>
