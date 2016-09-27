<!DOCTYPE html >
<html lang="zh-cmn-Hans">
<head>

    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" /> <!-- 优先使用 IE 最新版本和 Chrome -->
    <meta name="renderer" content="webkit"> <!--360浏览器就会在读取到这个标签后，立即切换对应的内核。并将这个行为应用于这个二级域名下所有网址。-->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Cache-Control" content="no-siteapp" />

    <title>供应链金融管理平台 - 登录</title>

    <link rel="stylesheet" type="text/css" href="/static/admin/css/stylesheets/main.css"/>
    <link rel="stylesheet" type="text/css" href="/static/admin/libs/bootstrap/dist/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/static/admin/css/stylesheets/page/login.css"/>

    <link type="image/x-icon" rel="shortcut icon" href="/static/admin/css/images/favicon.ico" />
    
</head>
<body>


<page-login>加载中...</page-login>


<!-- 1. Load libraries -->
<!-- Polyfill(s) for older browsers -->
<script src="/static/admin/libs/core-js/client/shim.min.js"></script>
<script src="/static/admin/libs/zone.js/dist/zone.min.js"></script>
<script src="/static/admin/libs/reflect-metadata/Reflect.js"></script>
<script src="/static/admin/libs/systemjs/dist/system.js"></script>


<!-- 2. Configure SystemJS -->
<script src="/static/admin/jsoutput/systemjs.config.js"></script>


<#if env == 'dev' || env == 'staging' || env == 'prod' >
<!-- 生产环境使用 bundle.js 文件 -->
<script src="/static/admin/jsoutput/page/dependencies.bundle.js"></script>
<script src="/static/admin/jsoutput/page/login.bundle.js"></script>

</#if>

<script>
    System.import('jsoutput/page/login.js').catch(function(err){ console.error(err); });
</script>






</body>
</html>
