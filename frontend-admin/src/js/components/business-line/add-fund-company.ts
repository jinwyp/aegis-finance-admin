/**
 * Created by liushengbin on 16/8/15.
 */


import {Component} from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, Router}      from '@angular/router';
import {Subscription} from 'rxjs/Subscription';

import {FundCompany, FundService} from '../../service/fund';


declare var __moduleName:string;


@Component({
    selector :    'add-fund-company',
    moduleId :    module.id,
    templateUrl : 'add-fund-company.html'
})
export class AddFundCompanyComponent {

    private sub:Subscription;

    constructor(
        private location : Location,
        private router : Router,
        private activatedRoute: ActivatedRoute,
        private fundService: FundService,
    ) {}

    css = {
        formActiveForRefresh : true,
        ajaxSuccessHidden : true,
        ajaxErrorHidden : true,
        isSubmitted :      false,
        isHiddenResetModal : true,
        isHiddenMsgModal : true,
        isAddStatus :  false
    };

    errorMsg = '';

    fundCompany = new FundCompany();

    ngOnInit() {
        this.sub = this.activatedRoute.data.subscribe(data => {
            this.fundCompany.type = data['routeType'];
            console.log(data);
        });

        if (this.activatedRoute.routeConfig.path.indexOf('add') > -1) {
            this.css.isAddStatus = true;
        }else{
            this.css.isAddStatus = false;
            this.sub = this.activatedRoute.params.subscribe(params => {
                this.getFundCompany(params['id']);
            });
        }
    }


    ngOnDestroy() {
        if (this.sub) {
            this.sub.unsubscribe();
        }
    }

    getFundCompany(id : number) {

        this.fundService.getFundCompanyById(id).then((result)=> {
            if (result.success) {
                console.log(result.data);
                this.fundCompany.id = result.data.id;
                this.fundCompany.name = result.data.name;
            } else {

            }
        });
    }

    addUser() {
        this.css.isSubmitted     = true;
        this.css.ajaxErrorHidden     = true;
        this.css.ajaxSuccessHidden     = true;

        if (this.css.isAddStatus) {
            this.fundService.add(this.fundCompany).then((result)=> {
                this.css.isSubmitted = false;
                if (result.success) {
                    console.log(result.data.id);
                    this.css.ajaxSuccessHidden=false;
                    this.router.navigate(['users/add', {'companyId' : result.data.id}]);
                } else {
                    this.css.ajaxErrorHidden = false;
                    this.errorMsg = result.error.message;
                }
            });
        } else {
            this.fundService.update(this.fundCompany).then((result)=> {
                this.css.isSubmitted     = false;
                if (result.success) {
                    this.css.ajaxSuccessHidden=false;
                    this.location.back();
                    // if(this.fundCompany.type===1){
                    //     this.router.navigate(['/businesslies']);
                    // }else{
                    //     this.router.navigate(['/fundcompanys']);
                    // }
                } else {
                    this.css.ajaxErrorHidden = false;
                    this.errorMsg = result.error.message;
                }
            });
        }
    }

}

