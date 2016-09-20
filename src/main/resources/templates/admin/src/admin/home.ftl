<!DOCTYPE html >
<html lang="zh-cmn-Hans">
<head>

    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" /> <!-- 优先使用 IE 最新版本和 Chrome -->
    <meta name="renderer" content="webkit"> <!--360浏览器就会在读取到这个标签后，立即切换对应的内核。并将这个行为应用于这个二级域名下所有网址。-->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Cache-Control" content="no-siteapp" />

    <title>供应链金融管理平台 - 管理首页</title>

    <link rel="stylesheet" type="text/css" href="/static/admin/js/libs/bootstrap/dist/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/static/admin/css/stylesheets/main.css"/>
    <link rel="stylesheet" type="text/css" href="/static/admin/css/stylesheets/page/adminHome.css"/>

    <base href="/finance/admin/home">
</head>
<body>

<page-admin>加载中...</page-admin>


<!-- 1. Load libraries -->
<!-- Polyfill(s) for older browsers -->
<script src="/static/admin/js/libs/core-js/client/shim.min.js"></script>
<script src="/static/admin/js/libs/zone.js/dist/zone.min.js"></script>
<script src="/static/admin/js/libs/reflect-metadata/Reflect.js"></script>
<script src="/static/admin/js/libs/systemjs/dist/system.js"></script>


<!-- 2. Configure SystemJS -->
<script src="/static/admin/js/systemjs.config.js"></script>

<#if env == 'dev' || env == 'staging' || env == 'prod' >
<!-- Remove this statement if you want to run the on the fly transpiler -->
<!-- 生产环境使用 bundle.js 文件 -->
<script src="/static/admin/js/page/home.bundle.js"></script>

</#if>

<script>
    System.import('jsoutput/page/home.js').catch(function(err){ console.error(err); });
</script>






</body>
</html>
