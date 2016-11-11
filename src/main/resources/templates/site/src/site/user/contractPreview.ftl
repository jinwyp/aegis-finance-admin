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
    <style type="text/css">
        body {
            font-family: "SimSun";color:#000;box-sizing: border-box;
            max-width: 990px;margin: 0 auto;
            /*page-break-inside:avoid;*/
        }
        @page {
            size: A4 portrait;
            margin-bottom: 5%;
        }
        i{font-style: normal;}
        .left{text-align: left}
        .right{text-align: right}
        .fr{float:right;}
        .fl{float:left;}
        .word-break{word-wrap: break-word;}
        table{width:100%;border: 1px solid #ddd;border-collapse: collapse;font-size: 18px;
        }
        td{border: 1px solid #ddd;padding: 10px;}
        .table-bordered td p,.table-bordered td p span{padding:0;margin:0}
        ul{margin:0;padding:0;}
        li{box-sizing: border-box;}
        .bg-gray{background: #f5f5f5;white-space: nowrap;}
        .br0{border-right:none}

        p.tenderTit{font-size: 30px;font-weight: bold;text-align: center;}
        .content{font-size: 14px;}
        .contentInfo{overflow: hidden}
        .contentInfo p{display: inline-block;}
        .contentInfo p span{color:#000;}
        .subTit{font-size: 20px;margin: 20px 0;}
        .subTit b{border-left:4px solid #00b7ec;padding-left: 5px;}

        .project-title{width:70px;text-align: center;border: 1px solid #e4e4e4;background: #f9f9f9;font-size: 20px;}
        .td-border {padding:0;border-top: 1px solid #e4e4e4;border-bottom: 1px solid transparent;border-right:none;}
        .td-border ul {float: left;}
        .project-wrapper li {position: relative;top: 1px;float: left;width: 127px;overflow: hidden;border: 1px solid #e4e4e4;border-top: 0;border-left: 0;}
        .project-wrapper li p{font-size: 14px;padding-left:10px;color:#000;text-align: center;}
        .project-wrapper li.larger{border-bottom: 0px;}
        .project-wrapper li.larger-content{width: 768px;border-bottom: 0;}
        .project-wrapper li.larger-content-2 {width: 319px;}
        .project-wrapper li.larger-content span{background: #fff;text-align: left;padding-left: 5px}
        .project-wrapper li.larger-content-2 span{background: #fff;text-align: left;padding-left: 5px}
        .project-wrapper li.bg span {background: #f9f9f9;text-align: center;text-indent: 0;}
        .project-wrapper li span {display: block;height: 40px;border-bottom: 1px solid #e4e4e4;background: #f9f9f9;color: #000;font-size: 16px;line-height: 40px;text-align: center;}
    </style>

</head>
<body>
    <div>
        ${contract}
    </div>
    <div class="clear-level">
        <div class="container">
            <!--<div id="body-head">-->
                <!--<div style="border-left:4px solid #ff624f;height:25px;"><label style="color:#313131;font-size:24px; ">&nbsp;&nbsp;电子合同</label>-->
                <!--</div>-->
                <!--<hr style="color:#dcdcdc;">-->
            <!--</div>-->
            <!--<h5 style="color:#ff624f;margin-bottom: 2%;">请仔细阅读以下信息:</h5>-->
            <div>
                ${contract}
            </div>
        </div>
    </div>
</body>
</html>
