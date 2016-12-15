/**
 * Created by gonghuihui on 16/12/6.
 */

var user = require("../../userData.js");
var verify=require('../../verifyData.js');
var util = require('../../util.js');
describe("线上交易员",function(){
    var loginUserName=element(by.id('inputUsername'));
    var loginPassword=element(by.id('inputPassword'));
    var loginButton=element(by.css('.button'));
    var breadNav = element(by.css('.breadcrumb .active'));

    beforeAll(function(){
        browser.get('/finance/admin/login');
        loginUserName.sendKeys(user.trader.username);
        loginPassword.sendKeys(user.trader.password);
        loginButton.click();
        util.waitForUrlToChangeTo('/finance/admin/home/tasks/pending');
    });

    describe('线上交易员-我的待办',function(){
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

    describe('需求提报页面',function(){

        var financeMoney = element(by.css(".apply-table")).all(by.tagName('tr')).get(1).all(by.tagName('td')).get(1).element(by.tagName('input'));
        var time = element(by.css(".apply-table")).all(by.tagName('tr')).get(1).all(by.tagName('td')).get(3).element(by.tagName('input'));
        var amount = element(by.css(".apply-table")).all(by.tagName('tr')).get(2).all(by.tagName('td')).get(1).element(by.tagName('input'));
        var place = element(by.css(".apply-table")).all(by.tagName('tr')).get(3).all(by.tagName('td')).get(1).element(by.tagName('input'));
        var origin = element(by.css(".apply-table")).all(by.tagName('tr')).get(3).all(by.tagName('td')).get(3).element(by.tagName('input'));
        var price = element(by.css(".apply-table")).all(by.tagName('tr')).get(4).all(by.tagName('td')).get(1).element(by.tagName('input'));
        var index = element(by.css(".apply-table")).all(by.tagName('tr')).get(4).all(by.tagName('td')).get(3).element(by.tagName('input'));
        var fkxButton=element(by.css('.custom-select'));
        var verifyPass=element(by.css('.item-title')).all(by.tagName('td')).get(1).all(by.tagName('label')).get(0);
        var submit=element(by.partialButtonText('保存并提交'));
        var successMessage = element(by.css('.alert-success'));
        var errorMessage = element(by.css('.alert-danger'));

        it('提交需求提报',function(){
            util.presenceOfAll(element.all(by.css('.custom-select li')), 5000);
            financeMoney.clear().sendKeys(verify.traderVerify.financeMoney);
            time.clear().sendKeys(verify.traderVerify.time);
            amount.clear().sendKeys(verify.traderVerify.amount);
            place.clear().sendKeys(verify.traderVerify.place);
            origin.clear().sendKeys(verify.traderVerify.origin);
            price.clear().sendKeys(verify.traderVerify.price);
            index.clear().sendKeys(verify.traderVerify.index);

                util.presenceOfAll(element.all(by.css('.custom-select li')), 5000);
                fkxButton.click();
                return element.all(by.css('.custom-select ul li')).filter(function (elem) {
                    return elem.getText().then(function (text) {
                        return text === '易煤风控线';
                    });
                }).then(function (rows) {
                    console.log('-----li-----' + rows.length);
                    rows[0].getText().then(function (text) {
                        expect(text).toBe('易煤风控线');
                    });
                    rows[0].click();
                    expect(element(by.css('.custom-select label')).getText()).toContain('易煤风控线');
                    verifyPass.click();
                    submit.click();
                });
            });
        it('"需求提报"提交成功!', function () {
            expect(successMessage.getAttribute('class')).toContain('');
            expect(errorMessage.getAttribute('class')).toContain('hidden');
        });
        })
    })
