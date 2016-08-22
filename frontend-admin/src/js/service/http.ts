/**
 * Created by tttt on 8/22/16.
 */


export class HttpResult {

    success :boolean;
    error : {
        code : number,
        field : string,
        message : string
    };
    meta : {
        page : number,
        count : number,
        offset : number,
        total : number
    };
    data : any;

}