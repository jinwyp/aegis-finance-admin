/**
 * Created by JinWYP on 8/1/16.
 */


import 'js/jquery-plugin/jQuery.fn.datePicker';
import avalon from 'avalon2';
import 'js/common-libs/pagination';
import  {jQuery as $} from 'js/jquery-plugin/bootstrap.js';



var cangyaList = () => {

    var urlPrefix = 'http://192.168.1.180:2403/orders';

    // var applyTypeList = [
    //     {'name' : '', text : '全部'},
    //     {'name' : 'MYR', text : '煤易融'},
    //     {'name' : 'MYG', text : '煤易购'},
    //     {'name' : 'MYD', text : '煤易贷'},
    // ];
    var statusList = [
        {'name' : '0', text : '全部'},
        {'name' : '2', text : '待回款赎货'},
        {'name' : '4', text : '已结束(业务超时未处理)'},
        {'name' : '6', text : '已结束(港口超时未处理)'},
        {'name' : '8', text : '已结束(监管超时未处理)'},
        {'name' : '10', text : '已结束(资金方未放款)'},
        {'name' : '10', text : '待缴纳保证金'},
        {'name' : '10', text : '已完成回款赎货'},
        {'name' : '10', text : '放款审核未通过'},
        {'name' : '10', text : '货权已处置'},
    ];

    var vm = avalon.define({
        $id   : 'cangyaList',
        cangyaList : [],
        css : {
            status : false
        },
        searchQuery : {
            // requestUsername : '',
            approveStateId : '全部',
            // applyType : '全部',
            sourceId : '',
            startDate : '',
            endDate : '',
            page : 1,
        },
        configPagination : {
            totalPages:1,
            changePageNo : function(page){
                vm.searchQuery.page = page;
                getFinanceList({
                    // requestUsername : vm.searchQuery.requestUsername,
                    approveStateId : statusList.find(status => { return status.text === vm.searchQuery.approveStateId}).name,
                    // applyType : applyTypeList.find(type => { return type.text === vm.searchQuery.applyType}).name,
                    sourceId : vm.searchQuery.sourceId,
                    startDate : vm.searchQuery.startDate,
                    endDate : vm.searchQuery.endDate,
                    page : vm.searchQuery.page
                });
            }
        },
        

        clickStatus : (value)=>{
            vm.searchQuery.approveStateId = value;
        },

        // clickType : (value)=>{
        //     vm.searchQuery.applyType = value;
        // },

        searchFinanceOrder : (event)=>{
             getFinanceList({
                // requestUsername : vm.searchQuery.requestUsername,
                approveStateId : statusList.find(status => { return status.text === vm.searchQuery.approveStateId}).name,
                // applyType : applyTypeList.find(type => { return type.text === vm.searchQuery.applyType}).name,
                sourceId : vm.searchQuery.sourceId,
                startDate : vm.searchQuery.startDate,
                endDate : vm.searchQuery.endDate,
                 page : vm.searchQuery.page
            });
        }

    });


    //查询
    var getFinanceList = (query) => {
        console.log('查询参数:', query);
        var params = $.extend({}, query);

        $.ajax({
            url      : urlPrefix + '?startDate=JR201610170001', //http://localhost:2403/orders?sourceId=JR201610170001&requestUser=user1
            method   : 'GET',
            dataType : 'json',
            data     : params,
            success  : (data)=> {vm.cangyaList = data;
                // if (data.success){
                //
                //     vm.configPagination.totalPages = Math.ceil(data.meta.total / data.meta.count);
                // }else{
                //
                // }
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


    //导出excel
    $('#excel').click(()=>{
        location.href="/finance/user/financing/excel"
    })



};


cangyaList();

export default cangyaList;

