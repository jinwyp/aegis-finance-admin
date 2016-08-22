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
                                <td ms-visible="@financeInfo.applyType==='MYR'">煤易融</td>
                                <td ms-visible="@financeInfo.applyType==='MYD'">煤易贷</td>
                                <td ms-visible="@financeInfo.applyType==='MYG'">煤易购</td>
                                <th>业务编号:</th>
                                <td>{{@financeInfo.sourceID}}</td>
                            </tr>
                            <tr>
                                <th>申请人:</th>
                                <td>{{@financeInfo.applyUserName}}</td>
                                <th>申请时间:</th>
                                <td>{{@financeInfo.applyDateTime}}</td>
                            </tr>
                        </table>
                    </div>
                    <div class="paddingTable">
                        <table>
                            <tr>
                                <th>拟使用资金时间:</th>
                                <td>{{@financeInfo.expectedDate}}天</td>
                                <th colspan="2">预期此笔业务量:</th>
                                <td>{{@financeInfo.businessAmount}}万吨</td>
                            </tr>
                            <tr>
                                <th>拟融资金额:</th>
                                <td colspan="2">
                                    <span class="red">{{@financeInfo.financingAmount | number(2)}}</span>万元
                                    <br/>
                                    <span class="gray">({{@financeInfo.financingAmount | switchTxt}})</span>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div class="paddingTable">
                        <!--融--2-->
                        <table ms-visible="@financeInfo.applyType==='MYR'">
                            <tr>
                                <th>签约单位全称:</th>
                                <td>{{@financeInfo.contractors}}</td>
                                <th>下游签约单位全称:</th>
                                <td>{{@financeInfo.downstreamContractors}}</td>
                            </tr>
                            <tr>
                                <th>用煤终端:</th>
                                <td>{{@financeInfo.terminalServer}}</td>
                                <th>运输方式:</th>
                                <td>{{@financeInfo.transportMode}}</td>
                            </tr>
                            <tr>
                                <th>预计单吨销售价:</th>
                                <td colspan="2">{{@financeInfo.sellingPrice}}元/吨</td>
                            </tr>
                        </table>
                        <!--购-->
                        <table ms-visible="@financeInfo.applyType==='MYG'">
                            <tr>
                                <th>上游资源方全称:</th>
                                <td>{{@financeInfo.upstreamResource}}</td>
                                <th>中转港口/地全称:</th>
                                <td>{{@financeInfo.transferPort}}</td>
                            </tr>
                            <tr>
                                <th>运输方式:</th>
                                <td>{{@financeInfo.transportMode}}</td>
                                <th>单吨采购价:</th>
                                <td>{{@financeInfo.procurementPrice}}元/吨</td>
                            </tr>
                        </table>
                        <!--贷-->
                        <table ms-visible="@financeInfo.applyType==='MYD'">
                            <tr>
                                <th>煤炭仓储地:</th>
                                <td>{{@financeInfo.storageLocation}}</td>
                                <th>煤炭来源:</th>
                                <td>{{@financeInfo.coalSources}}</td>
                            </tr>
                            <tr>
                                <th>主要煤质指标:</th>
                                <!--------------------------------------------字段无------------------------->
                                <td>{{@financeInfo.transportMode}}</td>
                                <th>单吨市场报价:</th>
                                <td>{{@financeInfo.marketPrice}}元/吨</td>
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
                                    {{@financeInfo.comments}}
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
                                <td><span class="red"><em></em>{{@financeInfo.approveState}}</span></td>
                                <!--<td><span class="green"><em></em>{{@financeInfo.approveState}}</span></td>-->
                            </tr>
                            <tr>
                                <th>备注说明:</th>
                                <td>{{@financeInfo.comments}}</td>
                            </tr>
                        </table>
                    </div>
                    <!--若已上传补充材料 显示 开始-->
                    <div class="table-title">
                        <em></em>补充材料:
                    </div>
                    <div class="approvalInfo">
                        <table>
                            <tr>
                                <th>已上传材料:</th>
                                <td>
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
                            <tr>
                                <th>材料说明:</th>
                                <td>{{@financeInfo.comments}}</td>
                            </tr>
                        </table>
                    </div>
                    <!--补充材料结束-->
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



<#if env == 'staging' || env == 'prod' >
<!-- Remove this statement if you want to run the on the fly transpiler -->
<!-- 生产环境使用 bundle.js 文件 -->
<script src="${staticPath}/js/page/userCenterFinanceInfo.bundle.js"></script>
</#if>

<#--<script src="${staticPath}/js/page2/userCenterFinanceInfo.bundle.js"></script>-->
<script>
    System['import']('${staticPath}/js/page/userCenterFinanceInfo.js')
</script>


</body>
</html>
