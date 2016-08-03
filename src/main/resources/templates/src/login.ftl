<!DOCTYPE html>
<html>
<head>
    <title>Base Layout</title>
    <link rel="stylesheet" type="text/css" href="/styles/main.css"/>
</head>
<body>
<div>
    <div ng-controller="Login" class="container" style="margin-top: 100px;">
        <span id="errorInfo" style=""></span>
        <form class="form-horizontal" role="form">
            <h2 style="margin-left:4%">网站后台用户登录</h2>
            <hr>
            <!-- <div class="alert alert-danger" ng-show="errors.user.username[0]">{{errors.user.username[0]}}</div> -->
            <div class="form-group">
                <label for="input-username" class="col-sm-2 control-label">用户名</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" id="input-username" ng-model="admin.username" errors/>
                </div>
            </div>
            <div class="form-group">
                <label for="input-password" class="col-sm-2 control-label">密码</label>
                <div class="col-sm-4">
                    <input type="password" class="form-control" id="input-password" ng-model="admin.password"
                           placeholder="Password">
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-4">
                    <button type="submit" class="btn btn-lg btn-primary btn-block" ng-click="Login()">登录</button>
                </div>
            </div>
        </form>
    </div>
</div>

<script type="text/javascript" src="${static('/bower_components/uri.js/src/URI.min.js')}"></script>
<script type="text/javascript" src="${static('/bower_components/requirejs/require.js')}"></script>
<script type="text/javascript" src="${static('/scripts/config.js')}"></script>
<script type="text/javascript" src="${static('/scripts/rlogin.js')}"></script>
</body>
</html>