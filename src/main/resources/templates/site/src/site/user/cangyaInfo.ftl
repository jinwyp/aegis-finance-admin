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

    <link rel="stylesheet" href="${staticPath}/css/stylesheets/layout/leftmenu.css">
    <link rel="stylesheet" href="${staticPath}/css/stylesheets/page/cangyaInfo.css">
</head>
<body>
<!-- header start -->
<#include "../common/header.ftl" >
<!-- header stop -->

<!--供应链金融导航开始-->
<#include "../common/pagenav.ftl" >
<!--供应链金融导航结束-->

<!--融资管理开始-->
<div class="financeManageWrap">
    <div class="financeManage clearfix">
        <!--侧边栏开始-->
            <#include "../common/leftmenu.ftl" >
        <!--侧边栏结束-->

        <!--右侧主内容开始-->
        <div class="financeCon ms-controller" ms-controller="financeInfo">
            <div class="application">
                <h4><span></span>融资详情 - 煤易贷 </h4>
                <div class="table" >
                    <div class="table-title">
                        <em></em>基本信息:
                    </div>
                    <div class="paddingTable">
                        <table>
                            <tr>
                                <th>融资类型:</th>
                                <td ms-visible="@financeInfo.applyType==='MYR'">煤易融</td>
                                <td ms-visible="@financeInfo.applyType==='MYD'">煤易贷</td>
                                <td ms-visible="@financeInfo.applyType==='MYG'">煤易购</td>
                                <td>煤易购</td>
                                <th>融资用户:</th>
                                <td>嘎嘎嘎</td>
                                <th>业务编号:</th>
                                <td>嘎嘎嘎</td>
                            </tr>
                            <tr>
                                <th>拟融资金额:</th>
                                <td>{{@financeInfo.applyUserName}}</td>
                                <th>申请时间:</th>
                                <td>{{@financeInfo.createTime}}</td>
                                <th>使用时长:</th>
                                <td>{{@financeInfo.createTime}}</td>
                            </tr>
                            <tr>
                                <th>审批放款总额:</th>
                                <td>{{@financeInfo.applyUserName}}</td>
                                <th>抵押货值:</th>
                                <td>{{@financeInfo.createTime}}</td>
                                <th>已缴纳保证金:</th>
                                <td>{{@financeInfo.createTime}}</td>
                            </tr>
                            <tr>
                                <th>已归还金额:</th>
                                <td>{{@financeInfo.applyUserName}}</td>
                                <th>抵押数量:</th>
                                <td>{{@financeInfo.createTime}}</td>
                                <th>已赎回数量:</th>
                                <td>{{@financeInfo.createTime}}</td>
                            </tr>
                            <tr>
                                <th>剩余赎回数量:</th>
                                <td colspan="5">{{@financeInfo.applyUserName}}</td>
                            </tr>
                        </table>
                    </div>

                    <div class="table-title">
                        <em></em>交易信息:
                    </div>
                    <div class="changeInfo">
                        <table>
                            <tr>
                                <th>交易流水</th>
                                <th>交易日期</th>
                                <th>交易类型</th>
                                <th>本次赎货数量(吨)</th>
                                <th>剩余吨数(吨)</th>
                                <th>交易金额</th>
                            </tr>
                            <tr>
                                <td>交易流水</td>
                                <td>交易日期</td>
                                <td>交易类型</td>
                                <td>本次赎货数量(吨)</td>
                                <td>剩余吨数(吨)</td>
                                <td>交易金额</td>
                            </tr>

                        </table>
                    </div>

                    <div class="table-title">
                        <em></em>审批详情:
                    </div>
                    <div class="paddingTable">
                        <table>
                            <tr>
                                <th>审批状态:</th>
                                <td >
                                    <span class="green"><em></em>审核通过</span>
                                    <!--补充材料-->
                                    <!--<span class="red" ms-visible="@financeInfo.approveStateId===6"><em></em>审核中(补充材料)</span>-->
                                    <!--审核不通过-->
                                    <!--<span class="red" ms-visible="@financeInfo.approveStateId===10"><em></em>审核不通过</span>-->
                                    <!--待审核-->
                                    <!--<span class="green" ms-visible="@financeInfo.approveStateId===2"><em></em>待审核</span>-->
                                    <!--审核中-->
                                    <!--<span class="green" ms-visible="@financeInfo.approveStateId===4"><em></em>审核中</span>-->
                                    <!--审核通过-->
                                    <!--<span class="green" ms-visible="@financeInfo.approveStateId===8"><em></em>审核通过</span>-->
                                </td>
                                <th>审批时间:</th>
                                <td>2016-08-28   11:37:08</td>
                            </tr>
                            <tr>
                                <th>审批金额:</th>
                                <td>800万</td>
                                <th>审批人:</th>
                                <td>张三</td>
                            </tr>

                        </table>
                    </div>

                    <div class="table-title">
                        <em></em>业务状态:
                    </div>
                    <div class="text">
                        货权已处置
                    </div>

                    <div class="table-title">
                        <em></em>保证金缴纳:
                    </div>
                    <div class="paddingTable">
                        <table>
                            <tr>
                                <th>需缴纳保证金:</th>
                                <td>30万</td>
                            </tr>
                            <tr>
                                <th>金额大写:</th>
                                <td>三百万整</td>
                            </tr>
                            <tr>
                                <th colspan="2">
                                    <a href="">下载查看补充协议</a>
                                    |
                                    <a href="">上传</a>
                                </th>
                            </tr>
                        </table>
                    </div>

                    <a href="/finance/user/financing" class="back">赎回货物</a>
                    <a href="/finance/user/financing" class="back">下载/上传合同</a>
                    <a href="/finance/user/financing" class="back">返回</a>

                </div>
            </div>
        </div>
        <!--右侧主内容结束-->
    </div>
</div>
<!--融资管理结束-->

<!--footer start-->
<#include "../common/footer.ftl" >
<!--footer stop-->

<!--modal start-->
<#include "../common/modal.ftl" >
<!--modal stop-->

<script src="${staticPath}/jspm_packages/system.js"></script>
<script src="${staticPath}/js/config.js"></script>



<#if env == 'dev' || env == 'staging' || env == 'prod' >
<!-- Remove this statement if you want to run the on the fly transpiler -->
<!-- 生产环境使用 bundle.js 文件 -->
<script src="${staticPath}/js/page/dependencies.bundle.js"></script>
<script src="${staticPath}/js/page/userCenterFinanceInfo.bundle.js"></script>
</#if>

<!--<script src="${staticPath}/js/page-temp-bundle/dependencies.bundle.js"></script>-->
<!--<script src="${staticPath}/js/page-temp-bundle/userCenterFinanceInfo.bundle.js"></script>-->
<script>
    System['import']('${staticPath}/js/page/userCenterFinanceInfo.js')
</script>


</body>
</html>
