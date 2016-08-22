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
<#include "common/header.ftl" >
<!-- header stop -->

<!--nav start-->
<#include "common/indexnav.ftl" >
<!--nav stop-->

<!--banner start-->
<div class="banner_finance">
    <a href="javascript:void(0);" class="banner_link">
        <span class="link_light"></span>
        <span class="link_circle"></span>
        <span class="link_trigle"></span>
    </a>
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
        <div id="slide">
            <div class="filter">
                <span class="leftFilter"></span>
                <span class="rightFilter"></span>
            </div>
            <ul class="tuul">
                <li class="no0 type-buy" ms-duplex="@financeList.applyType==='MYG'"><a href="javascript:void(0);"></a><img src="${staticPath}/css/images/finance/type-buy.jpg" /></li>
                <li class="no1 type-melt"><a href="javascript:void(0);"></a><img src="${staticPath}/css/images/finance/type-melt.jpg" /></li>
                <li class="no2 type-loan"><a href="javascript:void(0);"></a><img src="${staticPath}/css/images/finance/type-loan.jpg" /></li>
                <li class="no3 type-buy"><a href="javascript:void(0);"></a><img src="${staticPath}/css/images/finance/type-buy.jpg" /></li>
                <li class="no4 type-melt"><a href="javascript:void(0);"></a><img src="${staticPath}/css/images/finance/type-melt.jpg" /></li>
                <li class="no5 waiting type-loan"><a href="javascript:void(0);"></a><img src="${staticPath}/css/images/finance/type-loan.jpg" /></li>

            </ul>

        </div>
        <div class="anniu">
            <span class="leftNav"></span>
            <span class="rightNav"></span>
        </div>

        <!--<div id="posterTvGrid86804" style="margin:40px auto 0 auto;"></div>-->
    </div>
</div>

    <!--融资类型结束-->

<!--footer start-->
<#include "common/footer.ftl" >
<!--footer stop-->

<!--modal start-->
<#include "common/modal.ftl" >
<!--modal stop-->


<script src="${staticPath}/jspm_packages/system.js"></script>
<script src="${staticPath}/js/config.js"></script>



<#if env == 'staging' || env == 'prod' >
<!-- Remove this statement if you want to run the on the fly transpiler -->
<!-- 生产环境使用 bundle.js 文件 -->
<script src="${staticPath}/js/page/financeHome.bundle.js"></script>

</#if>


<!--<script src="${staticPath}/js/page2/financeHome.bundle.js"></script>-->
<script>
    System["import"]("${staticPath}/js/page/financeHome.js")
</script>

</body>
</html>
