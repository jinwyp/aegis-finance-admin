<!-- header start -->
<div id="paymentHeader">
    <div class="headWrap">
        <div class="wrap1200">
            <ul class="menuList leftMenuList">
                <li class="noborder">24小时贵宾热线</li>
                <li  class="noborder red">400-960-1180</li>
            </ul>

            <ul class="menuList rightMenuList">

                <!--<li>您好!<a href="" class="blue">请登录</a></li>-->
                <!--<li><a href="">免费注册</a></li>-->
                <!--登录状态-->
                <#if session().user?exists >
                    <li>
                        <a href="${ssoMemberUrl!}/account/individualCenter">账户名：
                            <#if session().user.verifystatus == "审核通过">
                                <#if session().user.companyName?length &lt; 9>
                                    ${session().user.companyName}
                                <#else>
                                    ${session().user.companyName?substring(0,8)} ...
                                </#if>
                            <#else>
                                ${(session().user.nickname)!(session().user.securephone)}</a>
                            </#if>
                        </a>
                    </li>
                    <li><a href="/logout">退出</a></li>
                <#else>
                    <li>您好！<a href="${ssoMemberUrl}/login"  class="blue">请登录</a></li>
                    <li><a href="${ssoMemberUrl}/register">免费注册</a></li>
                </#if>
                <li><a href="${sitepage}/account/order/buy">我的易煤网</a></li>
                <li class="hoverDiv">
                    <a href="javascript:void(0);" class="hoverTitle">网站导航</a>
                    <div class="Nav hideMenu">
                        <div class="floatL">
                            <h2><span class="headBg"></span>交易服务</h2>
                            <table>
                                <tr>
                                    <td><a href="${sitepage}/mall">商城</a></td>
                                    <td><a href="${sitepage}/buy">供应专区</a></td>
                                    <td><a href="${sitepage}/sell">需求专区</a></td>
                                </tr>
                                <tr>
                                    <td><a href="/finance">金融</a></td>
                                    <td><a href="${sitepage}/tender">阳光采购</a></td>
                                    <td><a href="${sitepage}/coalzone">煤矿专区</a></td>
                                </tr>
                                <tr>
                                    <td><a href="${sitepage}/news">资讯</a></td>
                                    <td><a href="${sitepage}/logistics/toLogistics">物流</a></td>
                                    <td><a href="${sitepage}/dataCenter">数据中心</a></td>
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
                                    <td><a href="/finance/user/financing">供应链金融</a></td>
                                    <td class="paddingRight0"><a href="${sitepage}/account/order/buy">业务管理中心</a></td>
                                </tr>
                                <tr>
                                    <td><a href="${payUrl}/">财务管理中心</a></td>
                                </tr>
                                <tr>
                                    <td><a href="${ssoMemberUrl}/account/individualCenter">账户设置</a></td>
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
                <li class="noborder paddingRight0"><a href="${sitepage}/aboutUs">关于我们</a></li>
            </ul>
        </div>
    </div>
</div>
<!-- header stop -->