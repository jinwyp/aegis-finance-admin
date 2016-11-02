/**
 * Created by JinWYP on 8/11/16.
 */

import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import {GlobalPromiseHttpCatch, HttpResponse, API } from './http';


class FundCompany{
    id : number;            //序列id
    name : string;          //资金方公司名称
    type : number;          //类型
    adminName : string;     //帐号
}

@Injectable()
class FundService {

    constructor(
        private http: Http
    ) { }


    getFundCompanyList(name:string, account:string, page:number,) {
        // let url = `${API.fund}/fund?page=${page||1}&name=${name||''}&account=${account||''}`;
        let url = `${API.fund}/fund`;
        return this.http.get(url).toPromise()
            .then(response => response.json() as HttpResponse)
            .catch(GlobalPromiseHttpCatch);
    }

    getFundCompanyById(id: number) {
        return this.http.get(API.fund + '/' + id).toPromise()
            .then(response => response.json() as HttpResponse)
            .catch(GlobalPromiseHttpCatch);
    }

    deleteFundCompanyById(id: number) {
        return this.http.delete(API.fund + '/' + id).toPromise()
            .then(response => response.json() as HttpResponse)
            .catch(GlobalPromiseHttpCatch);
    }

    add(fundCompany: FundCompany) {
        let headers = new Headers({'Content-Type': 'application/json'});

        return this.http.post(API.fund, JSON.stringify(fundCompany), {headers: headers}).toPromise()
            .then(res => res.json() as HttpResponse )
            .catch(GlobalPromiseHttpCatch);
    }

    update(fundCompany: FundCompany) {
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');

        let url = `${API.fund}/${fundCompany.id}`;

        return this.http.put(url, JSON.stringify(fundCompany), {headers: headers}).toPromise()
            .then(res => res.json() as HttpResponse )
            .catch(GlobalPromiseHttpCatch);
    }

    save(fundCompany: FundCompany)  {
        if (fundCompany.id) {
            return this.update(fundCompany);
        }
        return this.add(fundCompany);
    }

}



export { FundService, FundCompany }