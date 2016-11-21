/**
 * Created by JinWYP on 8/1/16.
 */


import avalon from 'avalon2';
import 'js/common-libs/pagination';
import  {jQuery as $} from 'js/jquery-plugin/bootstrap.js';

import {getContractList} from 'js/service/http.js';


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
                getList({
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
            getList({
                applyType : applyTypeList.find(type => { return type.text === vm.searchQuery.applyType}).name,
                sourceId : vm.searchQuery.sourceId,
                page : vm.searchQuery.page
            });
        }

    });


    //查询
    var getList = (query) => {
        console.log('查询参数:', query);

        getContractList(query).done(function(data, textStatus, jqXHR) {
            if (data.success){
                vm.contactList = data.data;
                vm.configPagination.totalPages = Math.ceil(data.meta.total / data.meta.count);
            }else{

            }
        })
    };

    getList();



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

