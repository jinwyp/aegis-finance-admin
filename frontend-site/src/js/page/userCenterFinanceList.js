/**
 * Created by JinWYP on 8/1/16.
 */


import 'js/jquery-plugin/jQuery.fn.datePicker';
import avalon from 'avalon2';
import 'js/common-libs/pagination';
import  {jQuery as $} from 'js/jquery-plugin/bootstrap.js';



var financeList = () => {

    var vm = avalon.define({
        $id   : 'financeList',
        financeList : [],
        css : {
            status : false
        },
        searchQuery : {
            requestUsername : '',
            status : '',
            type : '',
            businessId : '',
            startDate : '',
            endDate : '',
            page : 1
        },

        configPagination : {
            totalPages : 1,
            changePageNo : function(page){
                vm.searchQuery.page = page;

                getFinanceList({
                    requestUsername : vm.searchQuery.requestUsername,
                    status : vm.searchQuery.status,
                    type : vm.searchQuery.type,
                    businessId : vm.searchQuery.businessId,
                    startDate : vm.searchQuery.startDate,
                    endDate : vm.searchQuery.endDate,
                    page : vm.searchQuery.page
                });
            }
        },
        

        clickStatus : (value)=>{
            vm.searchQuery.status = value;
            console.log(value);
        },

        clickType : (value)=>{
            vm.searchQuery.type = value;
            console.log(value);
        },

        searchFinanceOrder : (event)=>{
            getFinanceList({
                requestUsername : vm.searchQuery.requestUsername,
                status : vm.searchQuery.status,
                type : vm.searchQuery.type,
                businessId : vm.searchQuery.businessId,
                startDate : vm.searchQuery.startDate,
                endDate : vm.searchQuery.endDate
            });
        }

    });


    //查询
    var getFinanceList = (query) => {
        console.log("查询参数:", query);
        var params = $.extend({}, query);

        $.ajax({
            url      : '/api/financing/applyInfo',
            method   : 'GET',
            dataType : 'json',
            data     : params,
            success  : (data)=> {
                if (data.success){
                    vm.financeList = data.data;
                }else{

                }
            }
        });
    };

    getFinanceList();


    //datePicker
    var pickerStart = $('.startDate').pickadate({format:'yyyy-mm-dd', max:true, clear: '清空'});
    var pickerEnd = $('.endDate').pickadate({max:true, clear: '清空'});

    pickerStart.on('change', function () {
        // http://amsul.ca/pickadate.js/api/#method-get-select
        if(pickerEnd.pickadate('picker').get('select') && pickerStart.pickadate('picker').get('select').pick > pickerEnd.pickadate('picker').get('select').pick ){
            pickerEnd.pickadate('picker').clear();
        }
        pickerEnd.pickadate('picker').set('min', pickerStart.pickadate('picker').get('select').obj);

        vm.searchQuery.startDate = $('#startDate').val();
    });

    pickerEnd.on('change', function () {
        // http://amsul.ca/pickadate.js/api/#method-get-select
        vm.searchQuery.endDate = $('#endDate').val();
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




};


financeList();

export default financeList;

