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
        <div class="finance_sideBar">
            <h3>融资管理</h3>
            <ul>
                <li class="active"><a href="#">我的申请 </a><span class="trigle"></span></li>
                <!--<li><a href="#">我的申请</a><span class="trigle"></span></li>-->
            </ul>
        </div>
        <!--侧边栏结束-->


        <!--右侧主内容开始-->
        <!--<div class="financeCon ms-controller" ms-controller="test">-->
            <!--<input ms-duplex="@name">-->
            <!--<p>Hello,{{@name}}!</p>-->
            <!--<ul>-->
                <!--<li ms-for="($index,el) in @array">{{$index}}--{{el}}</li>-->
            <!--</ul>-->
        <!--</div>-->
        <div class="financeCon ms-controller" ms-controller="financeList">
            <div class="application ">
                <div class="borderB clearfix">
                    <h4><span></span>融资详情</h4>
                    <div class="floatR">
                        <!--<span><em></em>了解业务类型</span>-->
                        <a href="javascript:void(0);" id="finance">我要融资</a>
                    </div>
                </div>

                <form>
                    <label for="startDate">申请时间:</label>
                    <div class="time"><input type="text" id="startDate" class="iIpt iIpt_sm startDate"placeholder="yyyy-mm-dd" ms-duplex="@searchQuery.startDate" /></div>

                    <label for="endDate">到</label>
                    <div class="time"><input type="text" id="endDate" class="iIpt iIpt_sm endDate" placeholder="yyyy-mm-dd" ms-duplex="@searchQuery.endDate" /></div>

                    <label for="status">审核状态:</label>
                    <div class="positionR selectDiv">
                        <input type="text" value="全部" name="status" id="status" class="margin-l" ms-duplex="@searchQuery.status" readonly="readonly" />
                        <ul class="select">
                            <li ms-click="@clickStatus('全部')">全部</li>
                            <li ms-click="@clickStatus('待审核')">待审核</li>
                            <li ms-click="@clickStatus('补充材料')">补充材料</li>
                            <li ms-click="@clickStatus('审核通过')">审核通过</li>
                            <li class="lastLi" ms-click="@clickStatus('审核不通过')">审核不通过</li>
                        </ul>
                        <span class="trigger"></span>
                    </div>

                    <br/>

                    <label for="number">业务编号:</label>
                    <input type="text" id="number" class="margin-l" ms-duplex="@searchQuery.businessId" onkeyup="this.value=this.value.replace(/\D/g,'')">

                    <label for="type">业务类型:</label>
                    <div class="positionR selectDiv">
                        <input type="text" value="全部" name="type" id="type" class="margin-l" readonly="readonly" ms-duplex="@searchQuery.type" />
                        <ul class="select">
                            <li ms-click="@clickType('全部')">全部</li>
                            <li ms-click="@clickType('煤易融')">煤易融</li>
                            <li ms-click="@clickType('煤易贷')">煤易贷</li>
                            <li class="lastLi" ms-click="@clickType('煤易购')">煤易购</li>
                        </ul>
                        <span class="trigger"></span>
                    </div>

                    <label>申请人:</label>
                    <input type="text" placeholder="请输入申请人姓名" class="margin-l" ms-duplex="@searchQuery.requestUsername">
                    <input type="button" value="查询" ms-click="@searchFinanceOrder()">
                    <input type="button" value="导出Excel" id="excel" class="excel">
                </form>



                <table class="list">
                    <tr class="border">
                        <th>序号</th>
                        <th>业务编号</th>
                        <th>业务类型</th>
                        <th>申请时间</th>
                        <th>申请人</th>
                        <th>拟融资总额状态<br/>(万元)</th>
                        <th>使用时间<br/>(天)</th>
                        <th>审核状态</th>
                        <th>操作</th>
                    </tr>
                    <tr class="borderB" ms-for="(key, order) in @financeList">
                        <td>{{key+1}}</td>
                        <td>{{order.sourceID}}</td>
                        <td>{{order.applyType}}</td>
                        <td>{{order.applyDateTime}}</td>
                        <td>{{order.applyUserName}}</td>
                        <td class="bold">{{order.financingAmount | number(2)}}</td>
                        <td class="bold">{{order.expectedDate}}</td>
                        <!--审核不通过-->
                        <!--<td class="gray" ms-visible="@order.approveState==='审核不通过'">{{order.approveState}}</td>-->
                        <!--待审核-->
                        <!--<td class="green" ms-visible="@order.approveState==='待审核'">{{order.approveState}}</td>-->
                        <!--审核通过-->
                        <!--<td class="bold" ms-visible="@order.approveState==='审核通过'">{{order.approveState}}</td>-->

                        <td class="bold">{{order.approveState}}</td>

                        <td class="blueA"><a href="#" >查看详情</a></td>
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




<#if env == 'staging' || env == 'prod' >
<!-- Remove this statement if you want to run the on the fly transpiler -->
<!-- 生产环境使用 bundle.js 文件 -->
<script src="${staticPath}/js/page/userCenterFinanceList.bundle.js"></script>
</#if>


<script src="${staticPath}/js/page2/userCenterFinanceList.bundle.js"></script>
<script>
    System['import']('${staticPath}/js/page/userCenterFinanceList.js')
</script>


</body>
</html>
