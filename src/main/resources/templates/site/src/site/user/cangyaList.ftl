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
        <div class="financeCon ms-controller" ms-controller="cangyaList">
            <div class="application ">
                <div class="borderB clearfix">
                    <h4><span></span>融资详情 - 煤易贷 </h4>
                    <div class="floatR">
                        <!--<span><em></em>了解业务类型</span>-->
                        <a href="/finance">《&nbsp;返回易煤金融</a>
                    </div>
                </div>

                <form>
                    <label for="startDate">申请时间:</label>
                    <div class="time"><input type="text" id="startDate" class="iIpt iIpt_sm startDate"placeholder="yyyy-mm-dd" ms-duplex="@searchQuery.startDate" /></div>

                    <label for="endDate">到</label>
                    <div class="time"><input type="text" id="endDate" class="iIpt iIpt_sm endDate" placeholder="yyyy-mm-dd" ms-duplex="@searchQuery.endDate" /></div>

                    <label for="approveStateId">审核状态:</label>
                    <div class="positionR selectDiv">
                        <input type="text" value="全部" name="approveStateId" id="approveStateId" class="margin-l" ms-duplex="@searchQuery.approveStateId" readonly="readonly" />
                        <ul class="select">
                            <li ms-click="@clickStatus('全部')">全部</li>
                            <li ms-click="@clickStatus('待回款赎货')">待回款赎货</li>
                            <li ms-click="@clickStatus('已结束(业务超时未处理)')">已结束(业务超时未处理)</li>
                            <li ms-click="@clickStatus('已结束(港口超时未处理)')">已结束(港口超时未处理)</li>
                            <li ms-click="@clickStatus('已结束(监管超时未处理)')">已结束(监管超时未处理)</li>
                            <li ms-click="@clickStatus('已结束(资金方未放款)')">已结束(资金方未放款)</li>
                            <li ms-click="@clickStatus('待缴纳保证金')">待缴纳保证金</li>
                            <li ms-click="@clickStatus('已完成回款赎货')">已完成回款赎货</li>
                            <li ms-click="@clickStatus('放款审核未通过')">放款审核未通过</li>
                            <li class="lastLi"  ms-click="@clickStatus('货权已处置')">货权已处置</li>
                        </ul>
                        <span class="trigger"></span>
                    </div>

                    <br/>

                    <label for="sourceId">业务编号:</label>
                    <input type="text" id="sourceId" class="margin-l" ms-duplex="@searchQuery.sourceId">

                    <!--<label for="applyType">业务类型:</label>-->
                    <!--<div class="positionR selectDiv">-->
                        <!--<input type="text" value="全部" name="applyType" id="applyType" class="margin-l" readonly="readonly" ms-duplex="@searchQuery.applyType" />-->
                        <!--<ul class="select">-->
                            <!--<li ms-click="@clickType('全部')">全部</li>-->
                            <!--<li ms-click="@clickType('煤易融')">煤易融</li>-->
                            <!--<li ms-click="@clickType('煤易贷')">煤易贷</li>-->
                            <!--<li class="lastLi" ms-click="@clickType('煤易购')">煤易购</li>-->
                        <!--</ul>-->
                        <!--<span class="trigger"></span>-->
                    <!--</div>-->

                    <label>申请人:</label>
                    <input type="text" placeholder="请输入申请人姓名" class="margin-l">
                    <!--<input type="text" placeholder="请输入申请人姓名" class="margin-l" ms-duplex="@searchQuery.requestUsername">-->
                    <input type="button" value="查询" ms-click="@searchFinanceOrder()">
                    <input type="button" value="导出Excel" id="excel" class="excel">
                </form>

                <!--<div class="loading" ms-visible="@cangyaList.length===0"><img src="${staticPath}/css/images/finance/loading.gif" alt="loading"></div>-->


                <table class="list">
                    <tr class="border">
                        <th>业务编号</th>
                        <th>业务类型</th>
                        <th>申请时间</th>
                        <th>抵押数量<br/>(吨)</th>
                        <th>抵押货值<br/>(万元)</th>
                        <th>融资金额<br/>(万元)</th>
                        <th>存放港口</th>
                        <th>已赎回数量<br/>(吨)</th>
                        <th>已归还金额<br/>(万元)</th>
                        <th>剩余赎回数量<br/>(吨)</th>
                        <th>资金方审核状态</th>
                        <th>业务状态</th>
                        <th>期限<br/>(天)</th>
                        <th>操作</th>
                    </tr>
                    <tr class="borderB" ms-for="(index, order) in @cangyaList">
                        <td>{{order.sourceId}}</td>
                        <td>
                            <span ms-visible="order.applyType==='MYR'">煤易融</span>
                            <span ms-visible="order.applyType==='MYG'">煤易购</span>
                            <span ms-visible="order.applyType==='MYD'">煤易贷</span>
                            <span ms-visible="order.applyType===''">/</span>
                        </td>

                        <td>{{order.createTime || '/'}}</td>
                        <td>
                            <span ms-visible="order.financingAmount===null">/</span>
                            <span ms-visible="order.financingAmount!=null">{{order.financingAmount}}</span>

                        </td>
                        <td>{{order.expectDate || '/'}}</td>

                        <td>
                            <span class="gray" ms-visible="order.approveStateId===10">审核不通过</span>
                            <span class="green" ms-visible="order.approveStateId===2">待审核</span>
                            <span class="bold" ms-visible="order.approveStateId===8">审核通过</span>
                            <span class="bold" ms-visible="order.approveStateId===4">审核中</span>
                            <span class="bold" ms-visible="order.approveStateId===6">审核中<br/><b>(补充材料)</b></span>
                            <span ms-visible="order.approveStateId===''">/</span>
                        </td>

                        <td>
                            <!--审核不通过-->
                            <a  class="detailA blueA" ms-visible="order.approveStateId===10" ms-attr="{href:'/finance/user/financing/'+order.id}" >查看详情</a>
                            <!--审核通过-->
                            <a  class="detailA orangeA" ms-visible="order.approveStateId===8" ms-attr="{href:'/finance/user/financing/'+order.id}" >查看详情</a>
                            <!--待审核-->
                            <a  class="detailA grayA" ms-visible="order.approveStateId===2">查看详情
                                <!--<span class="btnTips">等待我们帮您完善材料</span>-->
                            </a>
                            <!--审核中-->
                            <a  class="detailA orangeA" ms-visible="order.approveStateId===4" ms-attr="{href:'/finance/user/financing/'+order.id}" >查看详情</a>
                            <!--审核中(补充材料)-->
                            <a  class="detailA orangeA" ms-visible="order.approveStateId===6" ms-attr="{href:'/finance/user/financing/'+order.id}" >查看详情</a>
                        </td>
                    </tr>
                    <tr ms-visible="@cangyaList.length===0">
                        <td colspan="7">当前无融资申请记录！</td>
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
    <script src="${staticPath}/js/page/userCenterCangyaList.bundle.js"></script>
</#if>

<!--<script src="${staticPath}/js/page-temp-bundle/dependencies.bundle.js"></script>-->
<!--<script src="${staticPath}/js/page-temp-bundle/userCenterFinanceList.bundle.js"></script>-->
<script>
    System['import']('${staticPath}/js/page/userCenterCangyaList.js')
</script>


</body>
</html>
