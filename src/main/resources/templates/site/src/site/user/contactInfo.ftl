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

    <link rel="stylesheet" href="${staticPath}/css/stylesheets/page/contactInfo.css">
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
                <h4><span></span>查看合同</h4>
                <div class="tab">
                    <ul class="tabNav">
                        <li class="active">
                            Section 1
                        </li>
                        <li>
                            Section 2
                        </li>
                        <li>
                            Section 3
                        </li>
                        <li>
                            Section 4
                        </li>
                    </ul>
                    <div class="tabCon">
                        <div class="tabDiv active tab-pane">
                            <div class="contract">
                                <div class="contractCon">
                                    1111111111111
                                </div>
                            </div>
                            <div class="annex">
                                附件1 :
                                <a href="#" class="pull"><em></em>下载附件</a>
                            </div>
                            <div class="annex">
                                附件2 :
                                <a href="#" class="pull"><em></em>下载附件</a>
                            </div>
                            <!--<a href="" class="btn save">保存</a>-->
                            <!--<a href="" class="btn downLoadContract"><em></em>下载合同</a>-->
                            <a href="/finance/user/cangya/11" class="btn back">返回</a>
                        </div>
                        <div class="tabDiv tab-pane">
                            <div class="contract">
                                <div class="contractCon">
                                    2222222222
                                </div>
                            </div>
                            <div class="annex">
                                附件1 :
                                <a href="#" class="pull"><em></em>下载附件</a>
                            </div>
                            <div class="annex">
                                附件2 :
                                <a href="#" class="pull"><em></em>下载附件</a>
                            </div>
                            <a href="/finance/user/cangya/11" class="btn back">返回</a>
                        </div>
                        <div class="tabDiv tab-pane">
                            <div class="contract">
                                <div class="contractCon">
                                    3333333333
                                </div>
                            </div>
                            <div class="annex">
                                附件1 :
                                <a href="#" class="pull"><em></em>下载附件</a>
                            </div>
                            <div class="annex">
                                附件2 :
                                <a href="#" class="pull"><em></em>下载附件</a>
                            </div>
                            <a href="/finance/user/cangya/11" class="btn back">返回</a>
                        </div>
                        <div class="tabDiv tab-pane">
                            <div class="contract">
                                <div class="contractCon">
                                    444444444
                                </div>
                            </div>
                            <div class="annex">
                                附件1 :
                                <a href="#" class="pull"><em></em>下载附件</a>
                            </div>
                            <div class="annex">
                                附件2 :
                                <a href="#" class="pull"><em></em>下载附件</a>
                            </div>
                            <a href="/finance/user/cangya/11" class="btn back">返回</a>
                        </div>
                    </div>
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
    System['import']('${staticPath}/js/page/userCenterContactInfo.js')
</script>


</body>
</html>
