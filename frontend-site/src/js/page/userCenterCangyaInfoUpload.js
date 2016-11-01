/**
 * Created by JinWYP on 8/12/16.
 */

import  {jQuery as $} from 'js/jquery-plugin/bootstrap.js';
import avalon from 'avalon2';
import WebUploader from 'webuploader';



var financeInfo = (query)=> {

    var url = window.location.href.match(/\/financing\/\d{1,8}/);
    if (url){var financeInfoId = Number(url[0].substr(11))}

    var vm = avalon.define({
        $id   : 'financeInfo',
        financeInfo : {},
        upFilesList:[],
        downFilesList:[],
        annexFilesList:[],
        css : {
            status : false
        }
    });

    var getFinanceInfo = (id) => {

        $.ajax({
            url      : '/api/financing/apply/' + id,
            method   : 'GET',
            dataType : 'json',
            success  : (data)=> {
                console.log(data);
                if (data.success){
                    vm.financeInfo = data.data;
                }else{

                }
            }
        });
    };

    // getFinanceInfo(financeInfoId);


    //    上游上传文件---------------------------------------------
    var userLoader = WebUploader.create({

        // 自动上传。
        // auto: true,
        // swf文件路径
        swf: '/static/site/js/jquery-plugin/webuploader.swf',

        // 文件接收服务端。
        server: 'http://webuploader.duapp.com/server/fileupload.php',

        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: '#picker',

        // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
        resize: false
    });

    // 当有文件被添加进队列的时候
    userLoader.on( 'fileQueued', function( file ) {
        console.log(file);
        vm.upFilesList.push({
            name:file.name,
            ext : file.ext,
            size : file.size,
            type : file.type
        });

    });
    userLoader.on( 'uploadSuccess', function( file ) {
        "use strict";
        console.log(file)
    });

    userLoader.on( 'uploadError', uploadError);
    userLoader.on( 'uploadComplete', uploadComplete);

    //    下游游上传文件---------------------------------------------
    var ensureLoader = WebUploader.create({

        // 自动上传。
        // auto: true,
        // swf文件路径
        swf: '/static/site/js/jquery-plugin/webuploader.swf',

        // 文件接收服务端。
        server: 'http://webuploader.duapp.com/server/fileupload.php',

        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: '#picker2',

        // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
        resize: false
    });

    // 当有文件被添加进队列的时候
    ensureLoader.on( 'fileQueued', function( file ) {
        console.log(file);
        vm.downFilesList.push({
            name:file.name,
            ext : file.ext,
            size : file.size,
            type : file.type
        });

    });
    ensureLoader.on( 'uploadSuccess', function( file ) {

    });

    ensureLoader.on( 'uploadError', uploadError);
    ensureLoader.on( 'uploadComplete', uploadComplete);

    //    附件上传文件---------------------------------------------
    var annexLoader = WebUploader.create({

        // 自动上传。
        // auto: true,
        // swf文件路径
        swf: '/static/site/js/jquery-plugin/webuploader.swf',

        // 文件接收服务端。
        server: 'http://webuploader.duapp.com/server/fileupload.php',

        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: '#picker3',

        // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
        resize: false
    });

    // 当有文件被添加进队列的时候
    annexLoader.on( 'fileQueued', function( file ) {
        console.log(file);
        vm.annexFilesList.push({
            name:file.name,
            ext : file.ext,
            size : file.size,
            type : file.type
        });

    });
    annexLoader.on( 'uploadSuccess', function( file ) {

    });

    annexLoader.on( 'uploadError', uploadError);
    annexLoader.on( 'uploadComplete', uploadComplete);


    var uploadComplete = (file) => console.log(file);
    var uploadError = (file) => {
        "use strict";
        console.error(file)
    };

//    编辑合同弹窗
    $(".editSell").click(function(){
        $(".editSellContact").modal();
    })
    $(".editBuy").click(function(){
        $(".editBuyContact").modal();
    })







};


financeInfo();

export default financeInfo;

