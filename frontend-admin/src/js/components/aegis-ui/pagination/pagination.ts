/**
 * Created by liushengbin on 16/8/15.
 */


import {Component, Input, Output, EventEmitter, forwardRef} from '@angular/core';
import {NG_VALUE_ACCESSOR, ControlValueAccessor} from '@angular/forms';
import {Page} from '../../../service/task';

declare var __moduleName:string;


export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR:any = {
    provide :     NG_VALUE_ACCESSOR,
    useExisting : forwardRef(() => PaginationComponent),
    multi :       true
};

//通过 setter 截听输入属性值的变化

@Component({
    selector :    'pagination',
    moduleId :    __moduleName || module.id,
    templateUrl : 'pagination.component.html',
    providers :   [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR]

})

export class PaginationComponent {

    private pageObj : Page;

    @Output()
    pageClick:any = new EventEmitter();


    private totalPage:number   = 0;
            pageItems:any[] = [];

    private paginationSize : number = 5;


//Placeholders for the callbacks which are later provided by the Control Value Accessor
    private onTouchedCallback:() => {};
    private onChangeCallback:(_:any) => {};

    //get accessor
    get value():any {
        return this.pageObj;
    };

    //set accessor including call the onchange callback
    set value(v:any) {
        if (v !== this.pageObj) {
            this.pageObj = v;
            this.onChangeCallback(v);
        }
    }

    constructor() {
    }

    ngOnInit() {
        this.getPaginationData();
    }

    itemClick(arg:number) {
        this.pageObj.page = arg;
        this.getPaginationData();
        this.pageClick.emit();
    }

    previous() {
        this.pageObj.page--;
        this.getPaginationData();
        this.pageClick.emit();
    }

    next() {
        this.pageObj.page++;
        this.getPaginationData();
        this.pageClick.emit();
    }

    getPaginationData() {
        this.pageItems.splice(0,this.pageItems.length);
        if (this.pageObj.total % this.pageObj.count === 0) {
            this.totalPage = this.pageObj.total / this.pageObj.count;
        } else {
            this.totalPage = (this.pageObj.total - this.pageObj.total % this.pageObj.count) / this.pageObj.count + 1;
        }
        if(this.totalPage < 10){
            for (let i = 1; i <= this.totalPage; i++) {
                this.pageItems.push({value:i,isdisabled:false});
            }
        }else{
            if(this.pageObj.page < this.paginationSize + 1){
                this.pageItems.push({value:1,isdisabled:false});
                this.pageItems.push({value:2,isdisabled:false});
                this.pageItems.push({value:3,isdisabled:false});
                this.pageItems.push({value:4,isdisabled:false});
                this.pageItems.push({value:5,isdisabled:false});
                this.pageItems.push({value:6,isdisabled:false});
                this.pageItems.push({value:7,isdisabled:false});
                this.pageItems.push({value:'...',isdisabled:true});
                this.pageItems.push({value:this.totalPage,isdisabled:false});
            }else if(this.pageObj.page < this.totalPage-this.paginationSize){
                this.pageItems.push({value:1,isdisabled:false});
                this.pageItems.push({value:'...',isdisabled:true});
                this.pageItems.push({value:this.pageObj.page-2,isdisabled:false});
                this.pageItems.push({value:this.pageObj.page-1,isdisabled:false});
                this.pageItems.push({value:this.pageObj.page,isdisabled:false});
                this.pageItems.push({value:this.pageObj.page+1,isdisabled:false});
                this.pageItems.push({value:this.pageObj.page+2,isdisabled:false});
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
        console.log(this.pageObj);
        console.log(this.totalPage);
        console.log(this.pageItems);
    }

}

