/**
 * Created by JinWYP on 10/10/2016.
 */



var util = require('../util.js');
var user = require('../userData.js');



describe('Admin Home Page - 管理后台首页', function() {

    var loginUsernameInput = element(by.id('inputUsername'));
    var loginPasswordInput = element(by.id('inputPassword'));

    var loginButton = element(by.css('.button'));
    var successMessage = element(by.css('.alert-success'));
    var errorMessage = element(by.css('.alert-danger'));

    var menuUserManageButton = element(by.partialLinkText('用户管理'));
    var userSearchButton = element(by.partialButtonText('查询'));
    var userAddButton = element(by.partialButtonText('添加用户'));
    var userSaveButton = element(by.partialButtonText('保存'));
    var userBackButton = element(by.partialButtonText('返回'));
    var breadNav = element(by.css('.breadcrumb .active'));

    var newUsernameInput = element(by.id('usernameInput'));
    var newNameInput = element(by.id('nameInput'));
    var newGroupInput = element(by.tagName('custom-checkbox')).all(by.tagName('label'));
    var newEmailInput = element(by.id('emailInput'));
    var newPhoneInput = element(by.id('phoneInput'));



    beforeAll(function() {
        browser.get('/finance/admin/login');
        loginUsernameInput.sendKeys(user.admin.username);
        loginPasswordInput.sendKeys(user.admin.password);

        loginButton.click();
        util.waitForUrlToChangeTo('/finance/admin/home/tasks/pending');
    });


    it('页面应该有Title', function() {
        expect(browser.getTitle()).toEqual('供应链金融管理平台 - 管理首页');
    });


    describe('创建用户 -', function() {

        it('点击用户管理菜单', function() {
            menuUserManageButton.click();
            browser.wait(browser.isElementPresent(userSearchButton), 10000);
            expect(breadNav.getText()).toEqual('用户管理');
        });

        
        it('点击添加用户按钮', function() {
            userAddButton.click();
            browser.wait(browser.isElementPresent(userSaveButton), 10000);
            expect(breadNav.getText()).toEqual('添加用户');
        });


        it('创建新用户-线上交易员管理组 成功', function() {
            newUsernameInput.sendKeys(user.adminTrader.username);
            newNameInput.sendKeys(user.adminTrader.username);
            newGroupInput.get(1).element(by.tagName('input')).click();
            newEmailInput.sendKeys(user.adminTrader.email);

            expect(newGroupInput.get(1).getText()).toEqual('线上交易员管理组');

            userSaveButton.click();

            expect(successMessage.getAttribute('class')).toContain('');
            expect(errorMessage.getAttribute('class')).toContain('hidden');

        });


        it('创建新用户-线上交易员管理组 失败', function() {
            newUsernameInput.sendKeys(user.adminTrader.username);
            newNameInput.sendKeys(user.adminTrader.username);
            newGroupInput.get(1).element(by.tagName('input')).click();
            newEmailInput.sendKeys(user.adminTrader.email);

            expect(newGroupInput.get(1).getText()).toEqual('线上交易员管理组');

            userSaveButton.click();

            expect(successMessage.getAttribute('class')).toContain('hidden');
            expect(errorMessage.getText()).toEqual('此登录名已经存在!');

        });


        it('点击返回按钮', function() {
            userBackButton.click();
            browser.wait(browser.isElementPresent(userSearchButton), 10000);
            expect(breadNav.getText()).toEqual('用户管理');
        });
    });


});
