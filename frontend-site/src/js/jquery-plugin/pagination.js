

define(['avalon'], function(avalon){

    function heredoc(fn) {
        return fn.toString().replace(/^[^\/]+\/\*!?\s?/, '').
        replace(/\*\/[^\/]+$/, '').trim().replace(/>\s*</g, '><')
    }

    var paginationTemplate = heredoc(function() {

        /*

         <nav class="pagination-financial" ms-visible="@_isShow">
         <ul class="pagination pageno">
         <li> <a aria-label="Previous" ms-class="{disabled: @isDisabled('prev', 1)}" ms-click="@_changePage($event, @currentPage-1, 'prev' )"> <span aria-hidden="true" > 上一页 </span> </a> </li>

         <li ms-for='(key, page) in @_pageArrayLeft'> <a ms-class="{active : page.value == @currentPage}" ms-click="@_changePage($event, page.value )">{{page.value}}</a> </li>
         <li ms-visible='@_ellipsisLeft'> <a >...</a> </li>


         <li ms-for='(key, page) in @_pageArrayMiddle'> <a ms-class="{active : page.value == @currentPage}" ms-click="@_changePage($event, page.value )">{{page.value}}</a> </li>


         <li ms-visible='@_ellipsisRight'> <a >...</a> </li>
         <li ms-for='(key, page) in @_pageArrayRight'> <a ms-class="{active : page.value == @currentPage}" ms-click="@_changePage($event, page.value)">{{page.value}}</a> </li>


         <li> <a aria-label="Next" ms-class="{disabled: @isDisabled('next', @totalPages)}" ms-click="@_changePage($event, @currentPage+1, 'next')"> <span aria-hidden="true"> 下一页 </span> </a> </li>
         </ul>

         <div class="jump-to-page">
         <span>共 {{@totalPages}}页, 到第</span> <input type="text" placeholder="" ms-duplex="@inputCurrentPages"> <span>页</span>
         <button class="iBtn pagination-button" ms-click="@_changePage($event, @inputCurrentPages)">确定</button>
         </div>
         </nav>


         */

    });

    avalon.component('ms-pagination', {
        template: paginationTemplate,
        defaults: {
            totalPages : 1,
            currentPage : 1,
            inputCurrentPages : 1,
            isShowPagination : true,
            changePageNo : avalon.noop,

            _isShow : false,
            _pageArrayLeft : [],
            _pageArrayRight : [],
            _pageArrayMiddle : [],

            _ellipsisLeft : false,
            _ellipsisRight : false,
            $buttons: {},

            onInit : function() {
                var vm = this;
                vm._showPaginations();
                //console.log('init', this.totalPages);
                this.$watch('totalPages', function(){
                    setTimeout(function(){
                        vm._showPaginations()
                    },2)
                })
            },

            onReady : function(){
                //console.log('ready', this.totalPages);
            },

            onViewChange : function(){
                //console.log('views change', this.totalPages);
            },

            isDisabled: function (name, page) {
                this.$buttons[name] = (this.currentPage === page);
                return this.$buttons[name];
            },

            _changePage : function(event, pageNo, name){
                if (this.$buttons[name] || pageNo === this.currentPage) {
                    return;  //disabled, active不会触发
                }

                event.preventDefault();
                var tempNo = Number(pageNo);

                if (tempNo < 1){
                    tempNo = 1
                }else if (tempNo > this.totalPages){
                    tempNo = this.totalPages
                }
                this.currentPage = tempNo;
                this.changePageNo(tempNo);
                this._showPaginations()
            },

            _showPaginations : function (totalPages) {
                var vm = this;

                if (totalPages) {
                    vm.totalPages = Number(totalPages);
                }

                vm._isShow = vm.totalPages > 0 && vm.isShowPagination;
                console.log('Pagination updated, isShow:', vm._isShow, '. Total Page:', this.totalPages, '. Current Page:', this.currentPage);

                if (vm.currentPage < 1){
                    vm.currentPage = 1
                }else if (vm.currentPage > vm.totalPages){
                    vm.currentPage = vm.totalPages
                }

                vm._pageArrayLeft = [];
                vm._pageArrayRight = [];
                vm._pageArrayMiddle = [];

                vm._ellipsisLeft = false;
                vm._ellipsisRight = false;

                var paginationShowNumberLimit = 8;
                var paginationLeftShowNumber = 2;
                var paginationRightShowNumber = 2;
                var paginationMiddleShowNumber = 3;

                var currentPageShowLeftNumber = paginationMiddleShowNumber + 1;
                var currentPageShowMiddleNumber = Math.ceil(paginationMiddleShowNumber / 2) ;

                for (var i=1; i<= vm.totalPages; i++){

                    if (vm.totalPages <= paginationShowNumberLimit){
                        vm._pageArrayMiddle.push({value:i});
                    }else{

                        //创建左部分的分页 例如 1,2
                        if ( i <= paginationLeftShowNumber ){ vm._pageArrayLeft.push({value:i}); }

                        //创建右部分的分页 例如 99,100
                        if ( i >= vm.totalPages - (paginationRightShowNumber - 1) ){ vm._pageArrayRight.push({value:i}); }

                        //创建中间部分的分页 例如 49,50,51
                        if (i > paginationLeftShowNumber  && i < vm.totalPages - (paginationRightShowNumber - 1) ) {

                            if (vm.currentPage <= currentPageShowLeftNumber && i <= (currentPageShowLeftNumber + 1) ) {
                                vm._ellipsisRight = true;
                                vm._pageArrayMiddle.push({value:i});
                            }

                            if ( vm.currentPage > currentPageShowLeftNumber && vm.currentPage < vm.totalPages - paginationMiddleShowNumber) {
                                vm._ellipsisLeft = true;
                                vm._ellipsisRight = true;

                                if ( i > vm.currentPage - currentPageShowMiddleNumber && i < vm.currentPage + currentPageShowMiddleNumber){
                                    vm._pageArrayMiddle.push({value:i});
                                }
                            }

                            if ( vm.currentPage >= vm.totalPages - paginationMiddleShowNumber && i >= vm.totalPages - paginationMiddleShowNumber - 1) {
                                vm._ellipsisLeft = true;
                                vm._pageArrayMiddle.push({value:i});
                            }
                        }
                    }
                }
            }
        }


    });

    return avalon;
});


