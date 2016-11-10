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
    <!--<link rel="stylesheet" href="${staticPath}/jspm_packages/npm/webuploader@0.1.8/css/webuploader.css">-->
    <link rel="stylesheet" href="${staticPath}/css/stylesheets/main.css">



    <!-- HTMLshiv for IE -->
    <!--[if lte IE 9]>
    <script src="${staticPath}/jspm_packages/html5shiv/dist/html5shiv.min.js"></script>
    <![endif]-->

    <link type="image/x-icon" rel="shortcut icon" href="${staticPath}/css/images/favicon.ico" />

    <meta name="keywords" content="易煤网">
    <meta name="description" content="易煤网">

    <link rel="stylesheet" href="${staticPath}/css/stylesheets/page/financeInfoContact.css">
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
        <div class="financeCon ms-controller" ms-controller="financeInfoController">
            <div class="application">
                <h4><span></span>融资详情 - 下载合同 </h4>
                <div class="contact" >
                    <div class="contact-title">
                        <em></em>下载合同
                    </div>
                    <div class="contactList">

                        <h3>上游采购合同:</h3>
                        <ul>
                            <li>
                                <div class="borderBottom">
                                    <span><em></em>《仓押上游采购合同》</span>
                                    <p class="floatR">
                                        <a ms-attr="{href:'/finance/user/'+@financeInfoId+'/contract/1/download'}"><em></em>下载</a>
                                        |
                                        <a ms-attr="{href:'/finance/user/'+@financeInfoId+'/contract/1/preview'}">预览</a>
                                    </p>
                                </div>
                            </li>
                        </ul>

                        <h3>下游采购合同:</h3>
                        <ul>
                            <li>
                                <div class="borderBottom">
                                    <span><em></em>《仓押下游采购合同》</span>
                                    <p class="floatR">
                                        <a ms-attr="{href:'/finance/user/'+@financeInfoId+'/contract/2/download'}"><em></em>下载</a>
                                        |
                                        <a ms-attr="{href:'/finance/user/'+@financeInfoId+'/contract/2/preview'}">预览</a>
                                    </p>
                                </div>
                            </li>
                        </ul>
                        <div >
                            <h3>上游合同附件:</h3>
                            <ul>
                                <li ms-for="(index, annex) in @financeInfo.attachmentList1">
                                    <div class="borderBottom">
                                        <span><em></em>《{{annex.name}}》</span>
                                        <p class="floatR">
                                            <a ms-attr="{href:annex.url}"><em></em>下载</a>
                                        </p>
                                    </div>
                                </li>
                            </ul>
                        </div>
                        <div ms-visible="@financeInfo.attachmentList1 != '' ">
                            <h3>下游合同附件:</h3>
                            <ul>
                                <li ms-for="(index, annex) in @financeInfo.attachmentList2">
                                    <div class="borderBottom">
                                        <span><em></em>《{{annex.name}}》</span>
                                        <p class="floatR">
                                            <a ms-attr="{href:annex.url}"><em></em>下载</a>
                                        </p>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>

                    <a ms-attr="{href:'/finance/user/financing/'+@financeInfoId}" class="btn back">返回</a>
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
    System['import']('${staticPath}/js/page/userCenterFinanceInfoContract.js')
</script>


</body>
</html>
