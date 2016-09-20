/**
 * Created by liushengbin on 16/8/15.
 */


import {Component, Input, Output, EventEmitter} from '@angular/core';

import {Page} from '../../../service/task';

declare var __moduleName:string;


//通过 setter 截听输入属性值的变化

@Component({
    selector :    'pagination',
    moduleId :    module.id,
    templateUrl : 'pagination.component.html',

})

export class PaginationComponent {

    @Output()
    pageClick:any = new EventEmitter();


    private totalPage:number   = 0;
            pageItems:any[] = [];

    private paginationSize : number = 5;

    _pageObj: Page = new Page;

    @Input()
    set pageObj(obj: Page) {
        this._pageObj = obj;
        this.getPaginationData();
    }
    get pageObj() { return this._pageObj; }


    itemClick(arg:number) {
        this._pageObj.page = arg;
        this.getPaginationData();
        this.pageClick.emit(this._pageObj);
    }

    previous() {
        this._pageObj.page--;
        this.getPaginationData();
        this.pageClick.emit(this._pageObj);
    }

    next() {
        this._pageObj.page++;
        this.getPaginationData();
        this.pageClick.emit(this._pageObj);
    }

    getPaginationData() {
        this.pageItems.splice(0,this.pageItems.length);
        if (this._pageObj.total % this._pageObj.count === 0) {
            this.totalPage = this._pageObj.total / this._pageObj.count;
        } else {
            this.totalPage = (this._pageObj.total - this._pageObj.total % this._pageObj.count) / this._pageObj.count + 1;
        }
        if(this.totalPage < 10){
            for (let i = 1; i <= this.totalPage; i++) {
                this.pageItems.push({value:i,isdisabled:false});
            }
        }else{
            if(this._pageObj.page < this.paginationSize + 1){
                this.pageItems.push({value:1,isdisabled:false});
                this.pageItems.push({value:2,isdisabled:false});
                this.pageItems.push({value:3,isdisabled:false});
                this.pageItems.push({value:4,isdisabled:false});
                this.pageItems.push({value:5,isdisabled:false});
                this.pageItems.push({value:6,isdisabled:false});
                this.pageItems.push({value:7,isdisabled:false});
                this.pageItems.push({value:'...',isdisabled:true});
                this.pageItems.push({value:this.totalPage,isdisabled:false});
            }else if(this._pageObj.page < this.totalPage-this.paginationSize){
                this.pageItems.push({value:1,isdisabled:false});
                this.pageItems.push({value:'...',isdisabled:true});
                this.pageItems.push({value:this._pageObj.page-2,isdisabled:false});
                this.pageItems.push({value:this._pageObj.page-1,isdisabled:false});
                this.pageItems.push({value:this._pageObj.page,isdisabled:false});
                this.pageItems.push({value:this._pageObj.page+1,isdisabled:false});
                this.pageItems.push({value:this._pageObj.page+2,isdisabled:false});
                this.pageItems.push({value:'...',isdisabled:true});
                this.pageItems.push({value:this.totalPage,isdisabled:false});
            }else{
                this.pageItems.push({value:1,isdisabled:false});
                this.pageItems.push({value:'...',isdisabled:true});
                this.pageItems.push({value:this.totalPage-6,isdisabled:false});
                this.pageItems.push({value:this.totalPage-5,isdisabled:false});
                this.pageItems.push({value:this.totalPage-4,isdisabled:false});
                this.pageItems.push({value:this.totalPage-3,isdisabled:false});
                this.pageItems.push({value:this.totalPage-2,isdisabled:false});
                this.pageItems.push({value:this.totalPage-1,isdisabled:false});
                this.pageItems.push({value:this.totalPage,isdisabled:false});
            }
        }
    }

}

