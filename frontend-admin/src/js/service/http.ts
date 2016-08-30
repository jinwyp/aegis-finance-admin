/**
 * Created by tttt on 8/22/16.
 */

var API = {
    login : '/api/financing/admin/login',
    logout : '/api/financing/admin/logout',
    session : '/api/financing/admin/session',
    tasks : '/api/financing/admin/tasks',
    tasksMYR : '/api/financing/admin/myr',
    tasksMYG : '/api/financing/admin/myg',
    tasksMYD : '/api/financing/admin/myd',
    users : '/api/financing/admin/users',
    groups : '/api/financing/admin/groups'
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