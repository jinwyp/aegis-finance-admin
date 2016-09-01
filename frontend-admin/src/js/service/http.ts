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




var GlobalPromiseHttpCatch = (error: any) => {
    if (error.status && error.status === 401) {
        window.location.href = '/finance/admin/login';

    }else if (error && error.status === 400){
        console.error('Http 400 请求发生错误!! ', error);
        return Promise.resolve(error);
    } else if (error && error.status === 404){
        console.error('Http 404 请求发生错误!! ', error);
        return Promise.reject(error);
    }else if (error && error.status === 500){
        console.error('Http 500 请求发生错误!! ', error);
        return Promise.reject(error);
    }else {
        console.error('Http 请求发生错误!! ', error);
        return Promise.reject(error);
    }
};



export {HttpResponse, API, GlobalPromiseHttpCatch}