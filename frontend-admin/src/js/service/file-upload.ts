/**
 * Created by JinWYP on 9/5/16.
 */


import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';

import { Observable }     from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/toPromise';

import {GlobalPromiseHttpCatch, HttpResponse, API } from './http';


@Injectable()
class FileUploadService {

    constructor(
        private http: Http
    ) {}

    private handleError (error: any) {
        // In a real world app, we might use a remote logging infrastructure
        // We'd also dig deeper into the error to get a better message
        let errMsg = (error.message) ? error.message :
            error.status ? `${error.status} - ${error.statusText}` : 'Server error';
        console.error(errMsg); // log to console instead
        return Observable.throw(errMsg);
    }

    upload(fileToUpload: any) {
        let input = new FormData();
        input.append("file", fileToUpload, fileToUpload.name);

        return this.http.post("/api/financing/admin/files", input)
            .map( response => {return response.json();} )
            .catch(this.handleError);
    }

}




export {FileUploadService}