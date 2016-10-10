/**
 * Created by JinWYP on 9/29/16.
 */

var util = require('../util.js');
var user = require('../userData.js');



describe('Admin Login Page - 管理员登录页面', function() {

    var usernameInput = element(by.id('inputUsername'));
    var passwordInput = element(by.id('inputPassword'));

    var submitButton = element(by.css('.button'));

    var errorSpan = element(by.css('.error')).all(by.tagName('span')).last();

    beforeEach(function() {
        browser.get('/finance/admin/login');
    });


    it('页面应该有Title', function() {
        expect(browser.getTitle()).toEqual('供应链金融管理平台 - 登录');
    });


    it('登录输入错误的密码', function() {
        usernameInput.sendKeys('jin');
        passwordInput.sendKeys('654321');

        expect(errorSpan.getAttribute('class')).toEqual('hidden');

        submitButton.click();
        expect(errorSpan.getText()).toEqual('账户或者密码错误，请重新输入。');
        expect(errorSpan.getAttribute('class')).toEqual('');
    });


    it('登录输入正确的密码', function() {
        usernameInput.sendKeys(user.admin.username);
        passwordInput.sendKeys(user.admin.password);

        submitButton.click();
        util.waitForUrlToChangeTo('/finance/admin/home/tasks/pending');
        expect(browser.getTitle()).toEqual('供应链金融管理平台 - 管理首页');

    });
});
