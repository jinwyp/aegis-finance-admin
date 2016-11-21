/**
 * Created by liushengbin on 16/8/15.
 */


import {Component} from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, Router}      from '@angular/router';
import {Subscription} from 'rxjs/Subscription';

import {RiskLine, RiskService} from '../../service/risk';


declare var __moduleName:string;


@Component({
    selector :    'add-risk-line',
    moduleId :    module.id,
    templateUrl : 'add-risk-line.html'
})
export class AddRiskLineComponent {

    private sub:Subscription;

    constructor(
        private location : Location,
        private activatedRoute: ActivatedRoute,
        private riskService: RiskService,
    ) {}

    css = {
        formActiveForRefresh : true,
        ajaxSuccessHidden : true,
        ajaxErrorHidden : true,
        isSubmitted :      false,
        isHiddenResetModal : true,
        isHiddenMsgModal : true,
        isAddStatus :  false,
        isLookStatus :  false,
        isEditStatus :  false
    };

    errorMsg = '';

    riskLine = new RiskLine();

    ngOnInit() {
        this.sub = this.activatedRoute.data.subscribe(data => {
            if(data['routeType']===1){
                this.css.isAddStatus = true;
            }else if(data['routeType']===2){
                this.css.isEditStatus = true;
                this.sub = this.activatedRoute.params.subscribe(params => {
                    this.getRiskLine(params['id']);
                });
            }else if(data['routeType']===3){
                this.css.isLookStatus = true;
                this.sub = this.activatedRoute.params.subscribe(params => {
                    this.getRiskLine(params['id']);
                });
            }
        });
    }


    ngOnDestroy() {
        if (this.sub) {
            this.sub.unsubscribe();
        }
    }

    getRiskLine(id:number) {

        this.riskService.getRiskLineById(id).then((result)=> {
            if (result.success) {
                this.riskLine = result.data;
            }
        });
    }

    addUser() {
        this.css.isSubmitted       = true;
        this.css.ajaxErrorHidden   = true;
        this.css.ajaxSuccessHidden = true;

        console.log(this.riskLine);
        this.riskLine.type = 1;
        console.log(this.riskLine);

        this.riskService.save(this.riskLine).then((result)=> {
            this.css.isSubmitted = false;
            if (result.success) {
                this.css.ajaxSuccessHidden = false;
                this.back();
            } else {
                this.css.ajaxErrorHidden = false;
                this.errorMsg            = result.error.message;
            }
        });
    }


    back(){
        this.location.back();
    }

}

