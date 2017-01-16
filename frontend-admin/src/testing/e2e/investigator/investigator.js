/**
 * Created by gonghuihui on 16/12/15.
 */

var user = require("../../userData.js");
var verify=require('../../verifyData.js');
var util = require('../../util.js');
describe("尽调员",function(){
    var loginUserName=element(by.id('inputUsername'));
    var loginPassword=element(by.id('inputPassword'));
    var loginButton=element(by.css('.button'));
    var breadNav = element(by.css('.breadcrumb .active'));

    beforeAll(function(){
        browser.get('/finance/admin/login');
        loginUserName.sendKeys(user.investigator1.username);
        loginPassword.sendKeys(user.investigator1.password);
        loginButton.click();
        util.waitForUrlToChangeTo('/finance/admin/home/tasks/pending');
    });

    describe('尽调员-我的待办',function(){
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

    describe('尽调报告',function(){

        var weCompany = element(by.css(".apply-table")).all(by.css('.item-min')).get(0).all(by.tagName('td')).get(3).element(by.tagName('input'));
        var financePeriod = element(by.css(".apply-table")).all(by.css('.item-min')).get(1).all(by.tagName('td')).get(3).element(by.tagName('input'));
        var interestRate = element(by.css(".apply-table")).all(by.css('.item-min')).get(2).all(by.tagName('td')).get(1).element(by.tagName('input'));
        var Businessdate = element(by.css(".apply-table")).all(by.css('.item-min')).get(2).all(by.tagName('td')).get(3).element(by.tagName('my-date-picker'));
        var upstream = element(by.css(".apply-table")).all(by.css('.item-min')).get(3).all(by.tagName('td')).get(1).element(by.tagName('input'));
        var downstream = element(by.css(".apply-table")).all(by.css('.item-min')).get(3).all(by.tagName('td')).get(3).element(by.tagName('input'));
        var transport = element(by.css(".apply-table")).all(by.css('.item-min')).get(4).all(by.tagName('td')).get(1).element(by.tagName('input'));
        var transitPort = element(by.css(".apply-table")).all(by.css('.item-min')).get(4).all(by.tagName('td')).get(3).element(by.tagName('input'));
        var qualityInspect = element(by.css(".apply-table")).all(by.css('.item-min')).get(5).all(by.tagName('td')).get(1).element(by.tagName('input'));
        var quantityInspect = element(by.css(".apply-table")).all(by.css('.item-min')).get(5).all(by.tagName('td')).get(3).element(by.tagName('input'));
        var historyCoperation = element(by.css(".apply-table")).all(by.css('.item-min')).get(6).all(by.tagName('td')).get(1).element(by.tagName('input'));
        var mainInformation = element(by.css(".apply-table")).all(by.css('.item-min')).get(7).all(by.tagName('td')).get(1).element(by.tagName('input'));
        var goodsCirculation = element(by.css(".apply-table")).all(by.css('.item-min')).get(8).all(by.tagName('td')).get(1).element(by.tagName('input'));
        var riskPoint = element(by.css(".apply-table")).all(by.css('.item-min')).get(9).element(by.tagName('textarea'));
        var credit = element(by.css(".apply-table")).all(by.css('.item-min')).get(10).element(by.tagName('textarea'));
        var opinions = element(by.css(".apply-table")).all(by.css('.item-min')).get(11).element(by.tagName('textarea'));
        var verifyPass=element(by.css(".apply-table")).all(by.css('.item-title')).get(7).all(by.tagName('label')).get(0);
        var submit=element(by.partialButtonText('保存并提交'));
        // var successMessage = element(by.css('.alert-success'));
        // var errorMessage = element(by.css('.alert-danger'));

        it('提交尽调报告',function(){
            util.presenceOfAll(weCompany, 5000);
            weCompany.clear().sendKeys(verify.investigatorVerify.weCompany);
            financePeriod.clear().sendKeys(verify.investigatorVerify.financePeriod);
            interestRate.clear().sendKeys(verify.investigatorVerify.interestRate);
            upstream.clear().sendKeys(verify.investigatorVerify.upstream);
            downstream.clear().sendKeys(verify.investigatorVerify.downstream);
            transport.clear().sendKeys(verify.investigatorVerify.transport);
            transitPort.clear().sendKeys(verify.investigatorVerify.transitPort);
            qualityInspect.clear().sendKeys(verify.investigatorVerify.qualityInspect);
            quantityInspect.clear().sendKeys(verify.investigatorVerify.quantityInspect);
            historyCoperation.clear().sendKeys(verify.investigatorVerify.historyCoperation);
            mainInformation.clear().sendKeys(verify.investigatorVerify.mainInformation);
            goodsCirculation.clear().sendKeys(verify.investigatorVerify.goodsCirculation);
            riskPoint.clear().sendKeys(verify.investigatorVerify.riskPoint);
            credit.clear().sendKeys(verify.investigatorVerify.credit);
            opinions.clear().sendKeys(verify.investigatorVerify.opinions);
            verifyPass.click();
            submit.click();

        });
        // it('"需求提报"提交成功!', function () {
        //     expect(successMessage.getAttribute('class')).toContain('');
        //     expect(errorMessage.getAttribute('class')).toContain('hidden');
        // });
    })
})

