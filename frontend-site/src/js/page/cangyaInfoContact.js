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
        filesList:[],
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


    //    上传文件
    var uploader = WebUploader.create({

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
    uploader.on( 'fileQueued', function( file ) {
        console.log(file);
        vm.filesList.push({
            name:file.name,
            ext : file.ext,
            size : file.size,
            type : file.type
        });

    });
    uploader.on( 'uploadSuccess', function( file ) {

    });

    uploader.on( 'uploadError', function( file ) {
        $( '#'+file.id ).find('p.state').text('上传出错');
    });

    uploader.on( 'uploadComplete', function( file ) {
        console.log(file)
    });







};


financeInfo();

export default financeInfo;

