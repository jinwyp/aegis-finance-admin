<!DOCTYPE html >
<html lang="zh-cmn-Hans">
<head>
    <#include "../common/headcss.ftl" >

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
                                        <a ms-attr="{href:'/finance/user/order/'+@financeInfoId+'/contract/1/download'}"><em></em>下载</a>
                                        |
                                        <a ms-attr="{href:'/finance/user/order/'+@financeInfoId+'/contract/1/preview'}" target="_blank">预览</a>
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
                                        <a ms-attr="{href:'/finance/user/order/'+@financeInfoId+'/contract/2/download'}"><em></em>下载</a>
                                        |
                                        <a ms-attr="{href:'/finance/user/order/'+@financeInfoId+'/contract/2/preview'}" target="_blank">预览</a>
                                    </p>
                                </div>
                            </li>
                        </ul>
                        <div ms-visible="@financeInfo.attachmentList1 != '' ">
                            <h3>上游合同附件:</h3>
                            <ul>
                                <li ms-for="(index, annex) in @financeInfo.attachmentList1">
                                    <div class="borderBottom">
                                        <span>《{{annex.name}}》</span>
                                        <p class="floatR">
                                            <a ms-attr="{href:'/finance/files?url='+annex.url}"><em></em>下载</a>
                                        </p>
                                    </div>
                                </li>
                            </ul>
                        </div>
                        <div ms-visible="@financeInfo.attachmentList2 != '' ">
                            <h3>下游合同附件:</h3>
                            <ul>
                                <li ms-for="(index, annex) in @financeInfo.attachmentList2">
                                    <div class="borderBottom">
                                        <span>《{{annex.name}}》</span>
                                        <p class="floatR">
                                            <a ms-attr="{href:'/finance/files?url='+annex.url}"><em></em>下载</a>
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
<script src="${staticPath}/js/page/userCenterFinanceInfoContract.bundle.js"></script>
</#if>

<!--<script src="${staticPath}/js/page-temp-bundle/dependencies.bundle.js"></script>-->
<!--<script src="${staticPath}/js/page-temp-bundle/userCenterFinanceInfo.bundle.js"></script>-->
<script>
    System['import']('${staticPath}/js/page/userCenterFinanceInfoContract.js')
</script>


</body>
</html>
