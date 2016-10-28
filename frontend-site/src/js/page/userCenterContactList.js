/**
 * Created by JinWYP on 8/1/16.
 */


import avalon from 'avalon2';
import 'js/common-libs/pagination';
import  {jQuery as $} from 'js/jquery-plugin/bootstrap.js';


var url = 'http://192.168.1.180:2403/contacts';


var contactList = () => {

    var applyTypeList = [
        {'name' : '', text : '全部'},
        {'name' : 'MYR', text : '煤易融'},
        {'name' : 'MYG', text : '煤易购'},
        {'name' : 'MYD', text : '煤易贷'},
    ];

    var vm = avalon.define({
        $id   : 'contactList',
        contactList : [],
        css : {
            status : false
        },
        searchQuery : {
            applyType : '全部',
            sourceId : '',
            page : 1,
        },
        configPagination : {
            totalPages:1,
            changePageNo : function(page){
                vm.searchQuery.page = page;
                getFinanceList({
                    applyType : applyTypeList.find(type => { return type.text === vm.searchQuery.applyType}).name,
                    sourceId : vm.searchQuery.sourceId,
                    page : vm.searchQuery.page
                });
            }
        },



        clickType : (value)=>{
            vm.searchQuery.applyType = value;
        },

        searchFinanceOrder : (event)=>{
            getFinanceList({
                applyType : applyTypeList.find(type => { return type.text === vm.searchQuery.applyType}).name,
                sourceId : vm.searchQuery.sourceId,
                page : vm.searchQuery.page
            });
        }

    });


    //查询
    var getFinanceList = (query) => {
        console.log('查询参数:', query);
        var params = $.extend({}, query);

        $.ajax({
            url      : url,
            method   : 'GET',
            dataType : 'json',
            data     : params,
            success  : (data)=> {
                vm.contactList = data;

                // if (data.success){
                //     vm.contactList = data.data;
                //     vm.configPagination.totalPages = Math.ceil(data.meta.total / data.meta.count);
                // }else{
                //
                // }
            }
        });
    };

    getFinanceList();



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





};


contactList();

export default contactList;

