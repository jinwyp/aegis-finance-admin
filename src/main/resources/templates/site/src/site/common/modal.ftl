<!--modal start-->
<!--两个按钮取消、确认 modal-->
<div class="container modalPublic">
    <div class="row clearfix">
        <div class="modal_1 modal fade" data-backdrop="static" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button"  class="close close_modal" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
                        <h4 class="modal-title" id="modal_title_1">融资申请提示</h4>
                    </div>
                    <div class="modal-body">
                        <div>
                            <div class="bg_img question" id="modalImg_1"></div>
                            <div class="modalInfo">
                                <span id="modalInfo_1" >确定提交该融资申请？</span>
                            </div>
                            <div class="tip">
                                <em>
                                    *申请完成后,我们的服务人员将会与您联系,并帮您完成<br>&nbsp;&nbsp;申请材料的填写.
                                </em>
                                <!--企业信息不完善!  img-attention-->
                                <!--您需要先完善企业信息后才能做融资申请哦～-->
                                <!--按钮 取消 马上去完善-->
                            </div>
                            <div class="buttons">
                                <input type="button" data-dismiss="modal" class="btn cancel" value="取消" id="md_cancel_1">
                                <input type="button" class="btn confirm" value="确认" id="md_ok_1">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!--一个按钮确认 modal-->
<div class="container modalPublic">
    <div class="row clearfix">
        <div class="modal_2 modal fade" data-backdrop="static" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button"  class="close close_modal" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
                        <h4 class="modal-title" id="modal_title_2">融资申请提示</h4>
                    </div>
                    <div class="modal-body">
                        <div>
                            <div class="bg_img attention" id="modalImg_2"></div>
                            <div class="modalInfo">
                                <span id="modalInfo_2" >您当天的申请已超过限定次数!</span>
                            </div>
                            <div class="buttons">
                                <!--<input type="button" data-dismiss="modal" class="btn cancel" value="取消" id="md_cancel_1">-->
                                <input type="button" class="btn confirm ml0" value="我知道了" id="md_ok_2">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<!--无按钮 modal-->
<div class="container modalPublic">
    <div class="row clearfix">
        <div class="modal_3 modal fade" data-backdrop="static" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button"  class="close close_modal" data-dismiss="modal" aria-label="Close"></button>
                        <h4 class="modal-title" id="modal_title_3">提示</h4>
                    </div>
                    <div class="modal-body">
                        <div class="pad-t20">
                            <div class="bg_img attention" id="modalImg_3"></div>
                            <div class="modalInfo">
                                <span id="modalInfo_3" >您确认接受供应商的投标并公示投标吗？</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!--无按钮(有跳转文本) modal-->
<div class="container modalPublic">
    <div class="row clearfix">
        <div class="modal_5 modal fade" data-backdrop="static" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button"  class="close close_modal" data-dismiss="modal" aria-label="Close"></button>
                        <h4 class="modal-title" id="modal_title_5">提示</h4>
                    </div>
                    <div class="modal-body">
                        <div class="pad-t20">
                            <div class="bg_img attention" id="modalImg_5"></div>
                            <div class="modalInfo">
                                <span id="modalInfo_5" >恭喜您，投标提交成功！</span>
                            </div>
                            <div class="sucClose" id="modal_html_5">
                                请关注<span><a class="color-blue" href="/account/myBid">“我的投标”</a></span>中的招标结果。
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!--5s自动关闭 modal-->
<div class="container modalPublic">
    <div class="row clearfix">
        <div class="modal_4 modal fade" data-backdrop="static" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button"  class="close close_modal" data-dismiss="modal" aria-label="Close"></button>
                        <h4 class="modal-title" id="modal_title_4">提示</h4>
                    </div>
                    <div class="modal-body">
                        <div>
                            <div class="bg_img attention" id="modalImg_4"></div>
                            <div class="modalInfo">
                                <span id="modalInfo_4">您确认接受供应商的投标并公示投标吗？</span>
                            </div>
                            <div class="autoClose">
                                <span class="color-red" id="modal_time_4">5s</span>自动关闭
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<!--modal stop-->