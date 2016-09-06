/**
 * Created by JinWYP on 9/5/16.
 */


import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

import {GlobalObservableHttpCatch, HttpResponse, API } from './http';


@Injectable()
class FileUploadService {

    constructor(
        private http: Http
    ) {}


    upload(fileToUpload: any) {
        let input = new FormData();
        input.append("file", fileToUpload, fileToUpload.name);

        return this.http.post("/api/financing/admin/files", input)
            .map( response => {return response.json();} )
            .catch(GlobalObservableHttpCatch);
    }

}




export {FileUploadService}