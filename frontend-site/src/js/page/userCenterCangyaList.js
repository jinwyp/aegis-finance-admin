/**
 * Created by JinWYP on 8/1/16.
 */


import 'js/jquery-plugin/jQuery.fn.datePicker';
import avalon from 'avalon2';
import 'js/common-libs/pagination';
import  {jQuery as $} from 'js/jquery-plugin/bootstrap.js';

var url = 'http://192.168.1.180:2403/orders';

var financeList = () => {

    var applyTypeList = [
        {'name' : '', text : '全部'},
        {'name' : 'MYR', text : '煤易融'},
        {'name' : 'MYG', text : '煤易购'},
        {'name' : 'MYD', text : '煤易贷'},
    ];
    var statusList = [
        {
            "name": "风控人员审批通过",
            "statusNo": 11,
            "displayName": "风控审批通过流程结束 等待上传合同模版",
            "id": "009867dab8483895"
        },
        {
            "name": "风控完成上传合同模版-煤易贷",
            "statusNo": 12,
            "displayName": "等待融资方,港口,监管方上传合同",
            "id": "42f2f6e1d969490a"
        },
        {
            "name": "用户完成上传合同",
            "statusNo": 21,
            "displayName": "用户融资方已上传合同并已缴纳保证金",
            "id": "7f89e47e9bf60a40"
        },
        {
            "name": "用户未上传合同，流程结束",
            "statusNo": 91,
            "displayName": "用户未上传合同，流程结束",
            "id": "aa177891dafac8fc"
        },
        {
            "name": "港口完成上传合同",
            "statusNo": 31,
            "displayName": "港口已上传合同",
            "id": "68022f91b4a88812"
        },
        {
            "name": "港口未上传合同，流程结束",
            "statusNo": 92,
            "displayName": "港口未上传合同，流程结束",
            "id": "c0f7599fb9498862"
        },
        {
            "name": " 监管方完成上传合同",
            "statusNo": 41,
            "displayName": "监管方已上传合同",
            "id": "9367042b8f13b850"
        },
        {
            "name": "监管方未上传合同，流程结束",
            "statusNo": 93,
            "displayName": "监管方未上传合同，流程结束",
            "id": "2543a452a78279b1"
        },
        {
            "name": "资金方未审核，流程结束",
            "statusNo": 95,
            "displayName": "资金方未审核，流程结束",
            "id": "21732897380618f8"
        },
        {
            "name": "资金方审核不合格，流程结束",
            "statusNo": 96,
            "displayName": "资金方审核不合格，流程结束",
            "id": "78801d998527da6e"
        },
        {
            "name": "资金方已审核，待放款",
            "statusNo": 51,
            "displayName": "待放款",
            "id": "590337512f5c3a47"
        },
        {
            "name": "资金方财务已放款",
            "statusNo": 52,
            "displayName": "已放款，等待用户赎回货物",
            "id": "d0878ad8763888b5"
        },
        {
            "name": "用户已汇款（赎回货物）",
            "statusNo": 53,
            "displayName": "等待资金方返回货物",
            "id": "0b5bcbacef83fb8e"
        },
        {
            "name": "已全部回款 资金方返回货物",
            "statusNo": 54,
            "displayName": "资金方已返回等值货物 等待港口确认",
            "id": "bfcd8d064982fb77"
        },
        {
            "name": "港口确认返回货物 流程结束",
            "statusNo": 81,
            "displayName": "港口确认返回货物 流程结束",
            "id": "ae737121dfe1f8bb"
        },
        {
            "name": "资金方扣押货物 流程结束",
            "statusNo": 82,
            "displayName": "资金方已扣押货物，流程结束",
            "id": "0ba8e207a388f851"
        },
        {
            "name": "部分回款 资金方已返部分货物",
            "statusNo": 56,
            "displayName": "资金方已返回等值货物 等待港口确认",
            "id": "128dab7ff29dab43"
        },
        {
            "name": "港口确认返回货物",
            "statusNo": 57,
            "displayName": "港口已确认返回货物",
            "id": "c132a4ae1954f93b"
        }
    ];

    var vm = avalon.define({
        $id   : 'financeList',
        financeList : [],
        css : {
            status : false
        },
        searchQuery : {
            // requestUsername : '',
            approveStateId : '全部',
            applyType : '全部',
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
                    applyType : applyTypeList.find(type => { return type.text === vm.searchQuery.applyType}).name,
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

        clickType : (value)=>{
            vm.searchQuery.applyType = value;
        },

        searchFinanceOrder : (event)=>{
             getFinanceList({
                // requestUsername : vm.searchQuery.requestUsername,
                approveStateId : statusList.find(status => { return status.text === vm.searchQuery.approveStateId}).name,
                applyType : applyTypeList.find(type => { return type.text === vm.searchQuery.applyType}).name,
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
            url      : url + '?startDate=JR201610170001', //http://localhost:2403/orders?sourceId=JR201610170001&requestUser=user1
            method   : 'GET',
            dataType : 'json',
            data     : params,
            success  : (data)=> {
                if (data.success){
                    vm.financeList = data.data;
                    vm.configPagination.totalPages = Math.ceil(data.meta.total / data.meta.count);
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


    //导出excel
    $('#excel').click(()=>{
        location.href="/finance/user/financing/excel"
    })




};


financeList();

export default financeList;

