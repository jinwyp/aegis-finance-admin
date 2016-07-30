<html>
<head>
    <title>Base Layout</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="${static('/styles/main.css')}"/>
    <#--<link rel="stylesheet" type="text/css" href="${static('/bower_components/angular-pagedown/angular-pagedown.css')}"/>-->
    <#--<link rel="stylesheet" type="text/css"-->
          <#--href="${static('/bower_components/bootstrap3-datetimepicker/build/css/bootstrap-datetimepicker.min.css')}"/>-->
    <#--<link rel="stylesheet" type="text/css"-->
          <#--href="${static('/bower_components/angular-ui-tree/dist/angular-ui-tree.min.css')}"/>-->
    <#--<link rel="stylesheet" type="text/css" href="${static('/bower_components/AngularJS-Toaster/toaster.min.css')}"/>-->
</head>
<body>
<header id="header_tab" class="navbar-inverse bs-docs-nav"
        style="position: fixed; z-index: 1000;  width: 100%; height: auto; margin-top: 0px; padding: 0px; top:0px;"
        role="banner" ng-controller="index.Index">
    <div class="container" style="padding-left: 0px;padding-right: 0px;">
        <nav class="collapse navbar-collapse bs-navbar-collapse" role="navigation">
            <ul class="nav navbar-nav">
                <li><a auth href="/mainPage">供应链金融</a></li>
                <li><a auth href="/logout">退出</a></li>
            </ul>
        </nav>
    </div>
</header>
<toaster-container></toaster-container>
<div class="container" id="container_tab" ng-view="" style="position: relative;"></div>
<#--<script>-->
    <#--(function () {-->
        <#--var header = document.getElementById("header_tab");-->
        <#--document.getElementById("container_tab").style.top = header.offsetHeight + "px";-->
    <#--})();-->
<#--</script>-->

<!-- 异常弹出框 -->
<!-- server error msg begin -->
<!-- $('#modal-server-error').modal(true); -->
<div id="modal-server-error" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true" data-backdrop="false" style="z-index: 1060;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">×</span></button>
                <h4 class="modal-title" id="mySmallModalLabel">温馨提示：</h4>
            </div>
            <div class="modal-body">
                <div class="alert alert-danger" style="margin-bottom: 0px;" role="alert">
                    <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                    <span class="sr-only">Error:</span>
                    <span id="server-error-msg">您访问的网页不存在...</span>
                </div>
            </div>
            <div class="modal-footer">
                <div class="col-xs-offset-8 col-md-offset-8">
                    <button type="button" class="col-xs-7 col-md-7 btn btn-inverse" data-dismiss="modal">
                        确定
                    </button>
                </div>
            </div>
        </div>
    </div><!-- /.modal-dialog -->
</div>

<!-- 错误弹出框 -->
<div class="clearfix">
    <div class="modal fade" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="false"
         id="errorRemindDialog" role="dialog" style="z-index: 1050;">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="false">
                        &times;
                    </button>
                    <h4 class="modal-title">
                        温馨提示
                    </h4>
                </div>
                <div class="modal-body" style="font-size: 16px;">
                    <div class="alert alert-danger" style="margin-bottom: 0px;" role="alert">
                        <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                        <span class="sr-only">Error:</span>
                        <span id="errorInformation"></span>
                    </div>
                </div>
                <div class="modal-footer">
                    <div class="col-xs-offset-8 col-md-offset-8">
                        <button type="button" class="col-xs-7 col-md-7 btn btn-inverse" data-dismiss="modal">
                            确定
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 成功提示框 -->
<div class="clearfix">
    <div class="modal fade" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="false"
         id="global_SuccessRemindDialog" role="dialog" style="z-index: 1050;">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="false">
                        &times;
                    </button>
                    <h4 class="modal-title">
                        温馨提示
                    </h4>
                </div>
                <div class="modal-body" style="font-size: 16px;">
                    <div class="alert alert-success" style="margin-bottom: 0px;" role="alert">
                        <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                        <span id="global_Success_Information"></span>
                    </div>
                </div>
                <div class="modal-footer">
                    <div class="col-xs-offset-8 col-md-offset-8">
                        <button type="button" class="col-xs-7 col-md-7 btn btn-inverse" data-dismiss="modal">
                            确定
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 事件确认框 -->
<div class="modal fade" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="false"
     id="global_Event_ConfirmDialog" role="dialog"  style="z-index: 1040;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="false">
                    &times;
                </button>
                <h4 id="global_dialog_confirm_title" class="modal-title">
                </h4>
            </div>
            <div id="global_dialog_confirm_body" class="modal-body" style="font-size: 16px;">
            </div>
            <div id="global_dialog_confirm_errorInfo" class="errorInfoStyle"></div>
            <div class="modal-footer">
                <div class="col-xs-offset-7 col-md-offset-7">
                    <button type="button" class="col-xs-5 col-md-5 col-xs-offset-1 col-md-offset-1 btn btn-primary"
                            id="global_dialog_confirm_click">
                        确认
                    </button>
                    <button type="button" class="col-xs-5 col-md-5 btn btn-inverse" data-dismiss="modal">
                        取消
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 上传文件弹出框 -->
<div class="clearfix">
    <div class="modal fade" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="false"
         id="global_dialog_uploadImageDialog" role="dialog" style="z-index: 1040;">
        <div class="modal-dialog">
            <div class="modal-content">
                <form method="post" action="" enctype="multipart/form-data" id="global_dialog_uploadImageDialogForm"
                      name="global_dialog_uploadImageDialogForm">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="false">
                            &times;
                        </button>
                        <h4 class="modal-title" id="dialog_head">上传文件</h4>
                    </div>
                    <div class="modal-body" style="font-size: 16px;">
                        <input id="global_dialog_imageFile" name="file" type="file">
                    </div>
                    <div class="modal-body">
                        <span id="global_dialog_ImageFileErrorInfo" style="color: red;"></span>
                    </div>
                    <div class="modal-footer">
                        <div class="col-xs-offset-7 col-md-offset-7">
                            <button type="button"
                                    class="col-xs-5 col-md-5 col-xs-offset-1 col-md-offset-1 btn btn-default"
                                    data-dismiss="modal">
                                取消
                            </button>
                            <button type="button" id="global_dialog_ImageUploadClick"
                                    class="col-xs-5 col-md-5 btn btn-primary">
                                确认
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="${static('/bower_components/uri.js/src/URI.min.js')}"></script>
<script type="text/javascript" src="${static('/bower_components/requirejs/require.js')}"></script>
<script type="text/javascript" src="${static('/scripts/config.js')}"></script>
<script type="text/javascript" src="${static('/scripts/rindex.js')}"></script>

<script type="text/javascript" src="${static('/scripts/ueditor.config.js')}"></script>
<#--<script type="text/javascript" src="${static('/bower_components/ueditor/ueditor.all.js')}"></script>-->

<#--<script type="text/javascript" src="${static('/bower_components/ueditor/ueditor.all.min.js')}"></script>-->
<#--<script type="text/javascript" src="/scripts/ueditor/lang/zh-cn/zh-cn.js"></script>-->

</body>
</html>
