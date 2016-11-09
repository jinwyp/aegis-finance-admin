<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>合同预览页面</title>
</head>
<body>

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

<div class="col-xs-2 col-md-2 col-md-offset-5">
    <a href="http://localhost:8002/finance/admin/finance/25/contract/1/download" type="button" class="btn" style="background:#317EE6;color:#ffffff;width:200px;height: 40px;font-size: 18px;" id="btn-depositConfirm">下载合同</a>
</div>

</body>
</html>