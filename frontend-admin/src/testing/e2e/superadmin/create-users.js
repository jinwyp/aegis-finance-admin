/**
 * Created by JinWYP on 10/10/2016.
 */



var util = require('../../util.js');
var user = require('../../userData.js');



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
    var riskLineSelect = element(by.id('riskLine'));
    var riskLineInput = element(by.id('riskLine')).all(by.tagName('div ul li'));
    var newEmailInput = element(by.id('emailInput'));
    var newPhoneInput = element(by.id('phoneInput'));


    function inputUserData(isSaveSuccess, user, groupIndex, groupText,riskIndex, riskText){
        newUsernameInput.clear().sendKeys(user.username);
        newNameInput.clear().sendKeys(user.username);
        newGroupInput.get(groupIndex).element(by.tagName('input')).click();
        riskLineSelect.click();
        riskLineInput.get(riskIndex).click();
        newEmailInput.clear().sendKeys(user.email);
        expect(newGroupInput.get(groupIndex).getText()).toEqual(groupText);

        userSaveButton.click();

        if (isSaveSuccess){
            expect(successMessage.getAttribute('class')).toContain('');
            expect(errorMessage.getAttribute('class')).toContain('hidden');
        }else{
            expect(successMessage.getAttribute('class')).toContain('hidden');
            expect(errorMessage.getText()).toEqual('此登录名已经存在!');
        }
    }

    beforeAll(function() {
        browser.get('/finance/admin/login');
        loginUsernameInput.sendKeys(user.admin.username);
        loginPasswordInput.sendKeys(user.admin.password);

        loginButton.click();
        util.waitForUrlToChangeTo('/finance/admin/home/tasks/pending');
    });


    describe('进入创建用户的页面 -', function() {

        it('页面应该有Title', function() {
            expect(browser.getTitle()).toEqual('供应链金融管理平台 - 管理首页- 审批');
        });

        it('点击用户管理菜单', function() {
            menuUserManageButton.click();
            browser.wait(browser.isElementPresent(userAddButton), 5000);
            expect(breadNav.getText()).toEqual('用户管理');
        });

        it('点击添加用户按钮', function() {
            userAddButton.click();
            browser.wait(browser.isElementPresent(userSaveButton), 5000);
            expect(breadNav.getText()).toEqual('添加用户');
        });
    });


    describe('创建用户 -', function() {

        // beforeEach(function() {
        //     browser.get('finance/admin/home/users');
        //     util.waitForUrlToChangeTo('finance/admin/home/users');
        // });

        afterEach(function() {
            newUsernameInput.clear();
            newNameInput.clear();
            newEmailInput.clear();

            var inputArray = Object.keys(Array.apply(null,{length:11})).map(Number);

            inputArray.forEach(function(value, index){
                newGroupInput.get(value).element(by.tagName('input')).isSelected().then(function(selected) {
                    if (selected) {newGroupInput.get(value).element(by.tagName('input')).click() }
                });
            });
        });

        it('创建新用户-易煤风控线管理员 成功', function () {
            inputUserData(true, user.fkxg1, 0, '系统管理员组',3 , '易煤风控线');
        });

        it('创建新用户-风控线二管理员 成功', function () {
            inputUserData(true, user.fkxg2, 0, '系统管理员组',2 , '测试风控线二');
        });

        it('创建新用户-风控线三管理员 成功', function () {
            inputUserData(true, user.fkxg3, 0, '系统管理员组',1 , '测试风控线三');
        });

        it('创建新用户-线上交易员管理组 成功', function () {
            inputUserData(true, user.adminTrader, 1, '线上交易员管理组', 0 , '请选择');
        });

        it('创建新用户-线上交易员组 成功', function () {
            inputUserData(true, user.trader, 2, '线上交易员组', 0 , '请选择');
        });
    });


    describe('返回上级页面 -', function() {

        it('点击返回按钮', function() {
            userBackButton.click();
            browser.wait(browser.isElementPresent(userSearchButton), 5000);
            expect(breadNav.getText()).toEqual('用户管理');
        });
    });

});
