/**
 * Created by JinWYP on 9/29/16.
 */

describe('Admin Login 管理员登录', function() {


    function waitForUrlToChangeTo( url) {

        browser.driver.wait(function () {
            return browser.driver.getCurrentUrl().then(function (resultUrl) {
                return resultUrl.includes(url);
            });
        }, 10 * 1000, '等待10秒钟');
    }


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


    it('输入错误的密码', function() {
        usernameInput.sendKeys('jin');
        passwordInput.sendKeys('654321');

        expect(errorSpan.getAttribute('class')).toEqual('hidden');

        submitButton.click();
        expect(errorSpan.getText()).toEqual('账户或者密码错误，请重新输入。');
        expect(errorSpan.getAttribute('class')).toEqual('');
    });


    it('输入正确的密码', function() {
        usernameInput.sendKeys('jin');
        passwordInput.sendKeys('123456');

        submitButton.click();
        waitForUrlToChangeTo('/finance/admin/home/tasks/pending');
        expect(browser.getTitle()).toEqual('供应链金融管理平台 - 管理首页');

    });
});
