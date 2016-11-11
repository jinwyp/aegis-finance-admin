/**
 * Created by JinWYP on 9/5/16.
 */


import {Component, Input, Output, EventEmitter, ViewChild} from '@angular/core';
import {FileUploadService} from '../../service/file-upload';

declare var __moduleName:string;




@Component({
    selector : 'file-upload',
    moduleId : module.id,
    templateUrl : 'file-upload.html'
})
export class FileUploadComponent {

    fileList : FileList[] = [];
    file  = {
        url : ''
    };

    @Input()
    btnText : string;

    @Output()
    onFinished:any = new EventEmitter();

    @ViewChild("fileInput") fileInput;

    constructor(
        private upload: FileUploadService
    ) {}

    addFile(): void {
        this.fileList = this.fileInput.nativeElement.files;

        if (this.fileList && this.fileList[0]) {
            let file = this.fileList[0];

            this.upload.upload(file).subscribe(
                result => { if (result.success){
                    this.file = result.data;
                    this.onFinished.emit({value:result.data}) } },
                error => console.error(error)
            );
        }
    }

}