<!DOCTYPE html >
<html lang="zh-cmn-Hans">
<head>

    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" /> <!-- 优先使用 IE 最新版本和 Chrome -->
    <meta name="renderer" content="webkit"> <!--360浏览器就会在读取到这个标签后，立即切换对应的内核。并将这个行为应用于这个二级域名下所有网址。-->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Cache-Control" content="no-siteapp" />

    <title>供应链金融管理平台 - 管理首页</title>

    <link rel="stylesheet" href="/static/node_modules/bootstrap/dist/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/static/css/stylesheets/main.css"/>
    <link rel="stylesheet" type="text/css" href="/static/css/stylesheets/page/adminHome.css"/>

</head>

<body>

    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container-fluid">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">Project name</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">
            <li><a href="#">Dashboard</a></li>
            <li><a href="#">Settings</a></li>
            <li><a href="#">Profile</a></li>
            <li><a href="#">Help</a></li>
          </ul>
          <form class="navbar-form navbar-right">
            <input type="text" class="form-control" placeholder="Search...">
          </form>
        </div>
      </div>
    </nav>


    <div class="container-fluid">

        <div class="row"> <div class="col-lg-12">
            <page-admin>加载中...</page-admin>
        </div></div>


    </div><!-- /.container -->

    <!-- 1. Load libraries -->
    <!-- Polyfill(s) for older browsers -->
    <script src="/static/node_modules/core-js/client/shim.min.js"></script>
    <script src="/static/node_modules/zone.js/dist/zone.js"></script>
    <script src="/static/node_modules/reflect-metadata/Reflect.js"></script>
    <script src="/static/node_modules/systemjs/dist/system.src.js"></script>

    <!-- 2. Configure SystemJS -->
    <script src="/static/js/systemjs.config.js"></script>
    <script>
      System.import('jsoutput/page/home.js').catch(function(err){ console.error(err); });
    </script>

</body>
</html>
