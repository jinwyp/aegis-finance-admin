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

</head>
<body>
    <!--<div>-->
        <!--${contract}-->
    <!--</div>-->
    <div class="clear-level">
        <div class="container">
            <div id="body-head">
                <div style="border-left:4px solid #ff624f;height:25px;"><label style="color:#313131;font-size:24px; ">&nbsp;&nbsp;电子合同</label>
                </div>
                <hr style="color:#dcdcdc;">
            </div>
            <h5 style="color:#ff624f;margin-bottom: 2%;">请仔细阅读以下信息:</h5>
            <div>
                ${contract}
            </div>
        </div>
    </div>
</body>
</html>
