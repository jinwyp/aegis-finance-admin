/**
 * Created by gonghuihui on 16/12/14.
 */

var user = require("../../userData.js");
var verify=require('../../verifyData.js');
var util = require('../../util.js');
describe("业务员",function(){
    var loginUserName=element(by.id('inputUsername'));
    var loginPassword=element(by.id('inputPassword'));
    var loginButton=element(by.css('.button'));
    var breadNav = element(by.css('.breadcrumb .active'));

    beforeAll(function(){
        browser.get('/finance/admin/login');
        loginUserName.sendKeys(user.salesman1.username);
        loginPassword.sendKeys(user.salesman1.password);
        loginButton.click();
        util.waitForUrlToChangeTo('/finance/admin/home/tasks/pending');
    });

    describe('业务员-我的待办',function(){
        it('定位相应融资订单', function () {

            var verfiyBtn = element(by.partialButtonText('审核并填写资料'));
            util.presenceOfAll(verfiyBtn, 5000);

            return element(by.css('.info-table tbody')).all(by.css('.item')).filter(function (row, index) {
                console.log('-------行数:' + index);
                // return tr.$$('td').get(0).geText().then(function(td){
                return row.all(by.tagName('td')).get(0).getText().then(function (code) {
                    console.log('-------第一列:' + code);
                    code = code.split('\n');
                    console.log('-------id:' + code[0]);
                    console.log(code[0] === verify.financecode);
                    return code[0] === verify.financecode;
                });
            }).then(function (rows) {
                console.log('-----rows-----'+rows.length);
                rows[0].all(by.tagName('td')).get(0).getText().then(function (code) {
                    code = code.split('\n');
                    expect(code[0]).toBe(verify.financecode);
                });
                rows[0].$('.btn').click();

            });
        });

        it('页面应该有"我的待办"', function() {
            // expect(browser.getTitle()).toEqual('供应链金融管理平台 - 管理首页- 审批');
            expect(breadNav.getText()).toEqual('我的待办');
        });

    });

    describe('业务员-业务审批页面',function(){

        var updownStream =element(by.css('.apply-table')).all(by.css('.item-min')).get(0).element(by.tagName('textarea'));
        var operationMode=element(by.css('.apply-table')).all(by.css('.item-min')).get(1).element(by.tagName('textarea'));
        var logistics=element(by.css('.apply-table')).all(by.css('.item-min')).get(2).element(by.tagName('textarea'));
        var another=element(by.css('.apply-table')).all(by.css('.item-min')).get(3).element(by.tagName('textarea'));
        var supplyMaterial=element(by.css('.apply-table')).all(by.css('.item-min')).get(4).element(by.tagName('textarea'));
        var verifyPass=element(by.css('.apply-table')).all(by.css('.item-title')).get(5).all(by.tagName('label')).get(0);
        var submit=element(by.partialButtonText('保存并提交'));
        // var successMessage = element(by.css('.alert-success'));
        // var errorMessage = element(by.css('.alert-danger'));

        it('提交"业务审批"',function(){
            util.presenceOfAll(updownStream, 5000);
            updownStream.clear().sendKeys(verify.saleVerify.updownStream);
            operationMode.clear().sendKeys(verify.saleVerify.operationMode);
            logistics.clear().sendKeys(verify.saleVerify.logistics);
            another.clear().sendKeys(verify.saleVerify.another);
            supplyMaterial.clear().sendKeys(verify.saleVerify.supplyMaterial);
            verifyPass.click();
            submit.click();

        });
    //     it('"需求提报"提交成功!', function () {
    //         expect(successMessage.getAttribute('class')).toContain('');
    //         expect(errorMessage.getAttribute('class')).toContain('hidden');
    //     });
    })
})
