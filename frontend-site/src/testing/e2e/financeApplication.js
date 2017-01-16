/**
 * Created by gonghuihui on 16/12/29.
 */

// var casper = require('casper').create();

casper.test.begin('Login Test',1, function suite(test) {
    casper.start("http://member-local.yimei180.com:3000/login", function() {
        test.assertTitle('易煤网-互联网+煤炭就是易煤网','打开了登录页面');
    });

    // casper.then(function () {
    //     test.assertExists('a.blue','登录链接存在');
    //     if(this.exists('a.blue')){
    //         casper.mouse.click('a.blue');
    //     }
    // });

    // casper.then(function () {
    //     test.assertTitle('易煤网-互联网+煤炭就是易煤网','打开了登录页面');
    //     test.assertExists('button.submit','登录按钮存在');
    // });

    casper.then(function(){
         this.evaluate(function() {
            document.querySelector('#userPhone').value = '15900648930';
            document.querySelector('#password').value = '111111';
            this.echo(this.getHTML('#userPhone'));
            this.echo(this.getHTML('#password'));
            casper.mouse.click('button.submit');
            this.waitForUrl(/individualCenter/, function() {
                test.assertTitle('易煤网','我的易煤网');
                this.echo('redirected to /account/individualCenter');
            });
        });
    })
    casper.run(function() {
        test.done();
    });
});





