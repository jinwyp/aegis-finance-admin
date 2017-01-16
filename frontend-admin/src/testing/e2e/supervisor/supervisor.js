/**
 * Created by gonghuihui on 16/12/15.
 */

var user = require("../../userData.js");
var verify=require('../../verifyData.js');
var util = require('../../util.js');
describe("监管员",function(){
    var loginUserName=element(by.id('inputUsername'));
    var loginPassword=element(by.id('inputPassword'));
    var loginButton=element(by.css('.button'));
    var breadNav = element(by.css('.breadcrumb .active'));

    beforeAll(function(){
        browser.get('/finance/admin/login');
        loginUserName.sendKeys(user.supervisor1.username);
        loginPassword.sendKeys(user.supervisor1.password);
        loginButton.click();
        util.waitForUrlToChangeTo('/finance/admin/home/tasks/pending');
    });

    describe('监管员-我的待办',function(){
        it('定位相应融资订单', function () {

            var verfiyBtn = element(by.partialButtonText('审核'));
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

    describe('监管报告',function(){

        var warehouseProperty = element(by.css(".apply-table")).all(by.css('.item-min')).get(0).all(by.tagName('td')).get(3).element(by.tagName('input'));
        var warehouseAddress= element(by.css(".apply-table")).all(by.css('.item-min')).get(1).all(by.tagName('td')).get(1).element(by.tagName('input'));
        var historyCooperation = element(by.css(".apply-table")).all(by.css('.item-min')).get(2).element(by.tagName('textarea'));
        var managerCondition = element(by.css(".apply-table")).all(by.css('.item-min')).get(3).element(by.tagName('textarea'));
        var standardDegree = element(by.css(".apply-table")).all(by.css('.item-min')).get(4).element(by.tagName('textarea'));
        var Coordination = element(by.css(".apply-table")).all(by.css('.item-min')).get(5).element(by.tagName('textarea'));
        var scheme = element(by.css(".apply-table")).all(by.css('.item-min')).get(6).element(by.tagName('textarea'));
        var conclusion = element(by.css(".apply-table")).all(by.css('.item-min')).get(7).element(by.tagName('textarea'));
        var verifyPass=element(by.css(".apply-table")).all(by.css('.item-title')).get(8).all(by.tagName('label')).get(0);
        var submit=element(by.partialButtonText('保存并提交'));

        it('提交监管报告',function(){
            util.presenceOfAll(warehouseProperty, 5000);
            warehouseProperty.clear().sendKeys(verify.supervisorVerify.warehouseProperty);
            historyCooperation.clear().sendKeys(verify.supervisorVerify.historyCooperation);
            managerCondition.clear().sendKeys(verify.supervisorVerify.managerCondition);
            standardDegree.clear().sendKeys(verify.supervisorVerify.standardDegree);
            Coordination.clear().sendKeys(verify.supervisorVerify.Coordination);
            scheme.clear().sendKeys(verify.supervisorVerify.scheme);
            conclusion.clear().sendKeys(verify.supervisorVerify.conclusion);
            verifyPass.click();
            submit.click();

        });

    })
})

