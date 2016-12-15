/**
 * Created by gonghuihui on 16/12/15.
 */

var user=require('../../userData.js');
var verify=require('../../verifyData.js');
var util=require('../../util.js');

describe('尽调员管理员',function(){
    var loginUserName=element(by.id('inputUsername'));
    var loginPassword=element(by.id('inputPassword'));
    var loginButton=element(by.css('.button'));
    var breadNav = element(by.css('.breadcrumb .active'));

    beforeAll(function(){
        browser.get('/finance/admin/login');
        loginUserName.sendKeys(user.adminInvestigator1.username);
        loginPassword.sendKeys(user.adminInvestigator1.password);
        loginButton.click();
        util.waitForUrlToChangeTo('/finance/admin/home/tasks/pending');
    });

    describe('尽调员管理员-我的待办',function(){
        it('定位相应融资订单', function () {

            var btn = element(by.partialButtonText('分配'));
            util.presenceOfAll(btn, 5000);

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
                // var traderBack = element(by.partialButtonText('返回'));
                // browser.wait(util.presenceOfAll(traderBack),5000);
                // expect(traderBack.isPresent()).toBe(true);
            });


        });

        it('页面应该有"待办详情"', function() {
            // expect(browser.getTitle()).toEqual('供应链金融管理平台 - 管理首页- 审批');
            expect(breadNav.getText()).toEqual('待办详情');
        });

    });

})

describe('尽调员管理员-待办详情',function(){

    var xlkButton=element(by.css('.custom-select'));
    var fpButton=element(by.partialButtonText('分配'));
    var successMessage = element(by.css('.alert-success'));
    var errorMessage = element(by.css('.alert-danger'));
    it('分配尽调员', function () {
        util.presenceOfAll(element.all(by.css('.custom-select li')), 5000);
        xlkButton.click();
        return element.all(by.css('.custom-select ul li')).filter(function (elem) {
            return elem.getText().then(function (text) {
                return text === '尽调1';
            });
        }).then(function (rows) {
            console.log('-----li-----' + rows.length);
            rows[0].getText().then(function (text) {
                expect(text).toBe('尽调1');
            });
            rows[0].click();
            expect(element(by.css('.custom-select label')).getText()).toContain('尽调1');
            fpButton.click();
        });
    });

    it('分配成功提示!', function () {
        expect(successMessage.getAttribute('class')).toContain('');
        expect(errorMessage.getAttribute('class')).toContain('hidden');
    });
})