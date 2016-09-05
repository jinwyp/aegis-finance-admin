/**
 * Created by JinWYP on 9/5/16.
 */


import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import {GlobalPromiseHttpCatch, HttpResponse, API } from './http';


@Injectable()
class FileUploadService {

    constructor(
        private http: Http
    ) {}

    upload(fileToUpload: any) {
        let input = new FormData();
        input.append("file", fileToUpload);

        return this.http.post("/api/financing/admin/upload/file", input);
    }

}





export {FileUploadService}