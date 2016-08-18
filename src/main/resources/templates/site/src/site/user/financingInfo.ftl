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

    <link rel="stylesheet" href="${staticPath}/css/stylesheets/page/financingInfo.css">
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


<!--供应链金融导航开始-->
<div id="subHeader">
    <div class="subHeaderWrap">
        <div class="subHeader1000">
            <div class="logo-title">
                <a href="#"></a>
                <span class="subHeaderTit">我的易煤网</span>
            </div>
            <div class="subNavList" id="subNavList">
                <ul>
                    <li class="active">
                        <a href="#">供应链金融</a>
                    </li>
                    <li>
                        <a href="#">业务管理</a>
                    </li>
                    <li>
                        <a href="#">财务管理</a>
                    </li>
                    <li class="lastLi">
                        <a href="#">账户设置</a>
                    </li>
                </ul>
            </div>
        </div>

    </div>
</div>
<!--供应链金融导航结束-->

<!--融资管理开始-->
<div class="financeManageWrap">
    <div class="financeManage clearfix">
        <!--侧边栏开始-->
        <div class="finance_sideBar">
            <h3>融资管理</h3>
            <ul>
                <li class="active"><a href="#">我的申请 </a><span class="trigle"></span></li>
                <!--<li><a href="#">我的申请</a><span class="trigle"></span></li>-->
            </ul>
        </div>
        <!--侧边栏结束-->
        <!--右侧主内容开始-->
        <div class="financeCon ms-controller" ms-controller="financeInfo">
            <div class="application">
                <!--面包屑开始-->
                <div>
                    <ol class="breadcrumb">
                        <li><a href="#">用户中心</a></li>
                        <li><a href="#">融资管理</a></li>
                        <li><a href="#">我的融资</a></li>
                        <li class="active">融资详情</li>
                    </ol>
                </div>
                <!--面包屑结束-->
                <h4><span></span>融资详情</h4>
                <div class="table" >
                    <div class="table-title">
                        <em></em>申请信息:
                    </div>
                    <div class="paddingTable">
                        <table>
                            <tr>
                                <th>融资类型:</th>
                                <td ms-visible="@financeList.applyType==='MYR'">煤易融</td>
                                <td ms-visible="@financeList.applyType==='MYD'">煤易贷</td>
                                <td ms-visible="@financeList.applyType==='MYG'">煤易购</td>
                                <th>业务编号:</th>
                                <td>{{@financeList.sourceID}}</td>
                            </tr>
                            <tr>
                                <th>申请人:</th>
                                <td>{{@financeList.applyUserName}}</td>
                                <th>申请时间:</th>
                                <td>{{@financeList.applyDateTime}}</td>
                            </tr>
                        </table>
                    </div>
                    <div class="paddingTable">
                        <table>
                            <tr>
                                <th>拟使用资金时间:</th>
                                <td>{{@financeList.expectedDate}}天</td>
                                <th colspan="2">预期此笔业务量:</th>
                                <td>{{@financeList.businessAmount}}万吨</td>
                            </tr>
                            <tr>
                                <th>拟融资金额:</th>
                                <td colspan="2"><span class="red">{{@financeList.financingAmount}}</span>万元</td>
                            </tr>
                        </table>
                    </div>
                    <div class="paddingTable">
                        <!--融--2-->
                        <table ms-visible="@financeList.applyType==='MYR'">
                            <tr>
                                <th>签约单位全称:</th>
                                <td>{{@financeList.contractors}}</td>
                                <th>下游签约单位全称:</th>
                                <td>{{@financeList.downstreamContractors}}</td>
                            </tr>
                            <tr>
                                <th>用煤终端:</th>
                                <td>{{@financeList.terminalServer}}</td>
                                <th>运输方式:</th>
                                <td>{{@financeList.transportMode}}</td>
                            </tr>
                            <tr>
                                <th>预计单吨销售价:</th>
                                <td colspan="2">{{@financeList.sellingPrice}}元/吨</td>
                            </tr>
                        </table>
                        <!--购-->
                        <table ms-visible="@financeList.applyType==='MYG'">
                            <tr>
                                <th>上游资源方全称:</th>
                                <td>{{@financeList.upstreamResource}}</td>
                                <th>中转港口/地全称:</th>
                                <td>{{@financeList.transferPort}}</td>
                            </tr>
                            <tr>
                                <th>运输方式:</th>
                                <td>{{@financeList.transportMode}}</td>
                                <th>单吨采购价:</th>
                                <td>{{@financeList.procurementPrice}}元/吨</td>
                            </tr>
                        </table>
                        <!--贷-->
                        <table ms-visible="@financeList.applyType==='MYD'">
                            <tr>
                                <th>煤炭仓储地:</th>
                                <td>{{@financeList.storageLocation}}</td>
                                <th>煤炭来源:</th>
                                <td>{{@financeList.coalSources}}</td>
                            </tr>
                            <tr>
                                <th>主要煤质指标:</th>
                                <!--------------------------------------------字段无------------------------->
                                <td>{{@financeList.transportMode}}</td>
                                <th>单吨市场报价:</th>
                                <td>{{@financeList.marketPrice}}元/吨</td>
                            </tr>
                        </table>
                    </div>
                    <div class="paddingTable">
                        <table>
                            <tr>
                                <th>已上传单据:</th>
                                <td colspan="3">
                                    <ul>
                                        <li class="paddingL0">
                                            <p>税务单据</p>
                                            <img src="" alt="">
                                        </li>
                                        <li>
                                            <p>税务单据</p>
                                            <img src="" alt="">
                                        </li>
                                        <li>
                                            <p>税务单据</p>
                                            <img src="" alt="">
                                        </li>
                                        <li>
                                            <p>税务单据</p>
                                            <img src="" alt="">
                                        </li>
                                    </ul>
                                </td>

                            </tr>

                        </table>
                    </div>
                    <div class="paddingTable">
                        <table>
                            <tr>
                                <th>备注说明:</th>
                                <td colspan="3">
                                    {{@financeList.comments}}
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div class="table-title">
                        <em></em>审批信息:
                    </div>
                    <div class="approvalInfo">
                        <table>
                            <tr>
                                <th>审批状态:</th>
                                <td><span class="red"><em></em>{{@financeList.approveState}}</span></td>
                                <!--<td><span class="green"><em></em>{{@financeList.approveState}}</span></td>-->
                            </tr>
                            <tr>
                                <th>备注说明:</th>
                                <td>{{@financeList.comments}}</td>
                            </tr>
                        </table>
                    </div>
                    <input type="button" value="返回" class="back">
                </div>
            </div>
        </div>
        <!--右侧主内容结束-->
    </div>
</div>
<!--融资管理结束-->

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
<script src="${staticPath}/js/page/userCenterFinanceInfo.bundle.js"></script>
</#if>

<script>
    System.import('${staticPath}/js/page/userCenterFinanceInfo.js')
</script>


</body>
</html>
