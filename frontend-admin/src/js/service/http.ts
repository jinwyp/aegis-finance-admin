/**
 * Created by tttt on 8/22/16.
 */

var API = {
    login : '/api/financing/admin/login',
    logout : '/api/financing/admin/logout',
    session : '/api/financing/admin/session',
    users : '/api/financing/admin/user',
    groups : '/api/financing/admin/group'
};


class HttpResponse {

    success : boolean;
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


export {HttpResponse, API}