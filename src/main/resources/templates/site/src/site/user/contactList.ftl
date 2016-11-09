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

    <link rel="stylesheet" href="${staticPath}/css/stylesheets/page/financingList.css">
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
        <div class="financeCon ms-controller" ms-controller="contactList">
            <div class="application ">
                <div class="borderB clearfix">
                    <h4><span></span>合同管理</h4>
                    <div class="floatR">
                        <!--<span><em></em>了解业务类型</span>-->
                        <a href="/finance">《&nbsp;返回易煤金融</a>
                    </div>
                </div>

                <form>
                    <label for="sourceId">合同编号:</label>
                    <input type="text" id="sourceId" class="margin-l" ms-duplex="@searchQuery.sourceId">

                    <label for="applyType">关联业务类型:</label>
                    <div class="positionR selectDiv">
                        <input type="text" value="全部" name="applyType" id="applyType" class="margin-l" readonly="readonly" ms-duplex="@searchQuery.applyType" />
                        <ul class="select">
                            <li ms-click="@clickType('全部')">全部</li>
                            <li ms-click="@clickType('煤易融')">煤易融</li>
                            <li ms-click="@clickType('煤易贷')">煤易贷</li>
                            <li class="lastLi" ms-click="@clickType('煤易购')">煤易购</li>
                        </ul>
                        <span class="trigger"></span>
                    </div>
                    <input type="button" value="查询" ms-click="@searchFinanceOrder()">
                </form>


                <table class="list">
                    <tr class="border">
                        <th>合同编号</th>
                        <th>关联业务编号</th>
                        <th>关联业务类型</th>
                        <th>业务申请时间</th>
                        <th>操作</th>
                    </tr>
                    <tr class="borderB" ms-for="(index, order) in @contactList">
                        <td>{{order.contactNo}}</td>
                        <td>{{order.orderNo}}</td>
                        <td>
                            <span ms-visible="order.type==='MYR'">煤易融</span>
                            <span ms-visible="order.type==='MYG'">煤易购</span>
                            <span ms-visible="order.type==='MYD'">煤易贷</span>
                            <span ms-visible="order.type===''">/</span>
                        </td>

                        <td>{{order.requesTime || '/'}}</td>

                        <td>
                            <a  class="detailA blueA" ms-attr="{href:'/finance/user/contact/'+order.contactNo}" >合同详情</a>
                        </td>
                    </tr>
                    <tr ms-visible="@contactList.length===0">
                        <td colspan="7">当前无合同列表！</td>
                    </tr>

                </table>

                <xmp ms-widget="[{is:'ms-pagination', $id:'pagination'}, @configPagination]"></xmp>
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
    <script src="${staticPath}/js/page/userCenterContactList.bundle.js"></script>
</#if>

<!--<script src="${staticPath}/js/page-temp-bundle/dependencies.bundle.js"></script>-->
<!--<script src="${staticPath}/js/page-temp-bundle/userCenterFinanceList.bundle.js"></script>-->
<script>
    System['import']('${staticPath}/js/page/userCenterContactList.js')
</script>


</body>
</html>
