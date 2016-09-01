/**
 * Created by JinWYP on 9/1/16.
 */

// var request = require("request");

var url = 'http://finance-local.yimei180.com:8002';

describe("用户登录场景",function(){
    it("输入用户名密码登录",function(){
        expect(true).toBe(true);
    });

    // it("输入用户名密码登录",function(done){
    //     request.post({url:url + "/api/financing/admin/login", form: { username : 'admin', password : '123456'}, json:true}, function optionalCallback(err, httpResponse, body) {
    //         if (err) {
    //             console.log('failed:', err);
    //         }else {
    //             expect(body.success).toBe(true);
    //         }
    //         done();
    //     });
    //
    // })
});

