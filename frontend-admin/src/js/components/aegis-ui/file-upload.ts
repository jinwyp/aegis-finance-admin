/**
 * Created by JinWYP on 9/5/16.
 */


import {Component, Input, Output, EventEmitter, ViewChild} from '@angular/core';
import {FileUploadService} from '../../service/file-upload';

declare var __moduleName:string;




@Component({
    selector : 'file-upload',
    moduleId : __moduleName || module.id,
    templateUrl : 'file-upload.html'
})
export class FileUploadComponent {

    fileList = [];

    @Input()
    limitLength : number = 0;

    @Output()
    onChange:any = new EventEmitter();


    @ViewChild("fileInput") fileInput;

    constructor(
        private upload: FileUploadService
    ) {}

    addFile(): void {
        this.fileList = this.fileInput.nativeElement.files;
        console.log(this.fileInput.nativeElement, this.fileList);

        if (this.fileList && this.fileList[0]) {
            let file = this.fileList[0];
            console.log(file);
            this.upload.upload(file).subscribe(res => {
                console.log(res);
            });
        }
    }

}