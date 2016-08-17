/**
 * Created by JinWYP on 8/1/16.
 */


import 'js/jquery-plugin/jQuery.fn.datePicker';
import  {jQuery as $} from 'js/jquery-plugin/bootstrap.js';
import avalon from 'avalon2';


var financeList = () => {
    //datePicker

    var pickerStart = $('.startDate').pickadate({format:'yyyy-mm-dd', max:true, clear: '清空'});
    var pickerEnd = $('.endDate').pickadate({max:true, clear: '清空'});

    pickerStart.on("change", function () {
        // http://amsul.ca/pickadate.js/api/#method-get-select
        if(pickerEnd.pickadate('picker').get('select') && pickerStart.pickadate('picker').get('select').pick > pickerEnd.pickadate('picker').get('select').pick ){
            pickerEnd.pickadate('picker').clear();
        }
        pickerEnd.pickadate('picker').set('min', pickerStart.pickadate('picker').get('select').obj);
    });


    // 模拟下拉菜单
    $(document.body).on('click', '.selectDiv input', function(){
        var $select = $(this).parent();
        if (!$select.hasClass('select-open')) {
            $select.addClass('select-open');

            setTimeout( ()=>{
                $(document.body).one('click', ()=>{ $select.removeClass('select-open') });
            });
        }
    });


    //融资modal
    $('#finance').click(() => {
        $('.modal_1').modal();
    });


    var getFinanceList = (query) => {
        console.log(query);
        var params = $.extend({}, query);

        $.ajax({
            url      : '/api/financing/applyInfo',
            method   : "GET",
            dataType : "json",
            data     : params,
            success  : (data)=> {
                if (data.success){
                    vm.financeList = data.data;
                }else{

                }
            }
        })
    };

    var vm = avalon.define({
        $id   : 'financeList',
        financeList : [],
        css : {
            status : false
        },
        searchQuery : {
            requestUsername : '',
            status : '',
            type : ''
        },

        clickStatus : (value)=>{
            vm.searchQuery.status = value;
        },

        clickType : (value)=>{
            vm.searchQuery.type = value;
        },

        searchFinanceOrder : (event)=>{
            getFinanceList({
                requestUsername : vm.searchQuery.requestUsername,
                status : vm.searchQuery.status,
                type : vm.searchQuery.type
            })
        }
    });

    getFinanceList();


};


financeList();

export default financeList;

