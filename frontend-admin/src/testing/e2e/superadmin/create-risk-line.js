/**
 * Created by liushengbin on 2016/11/24.
 */

var util = require('../../util.js');
var user = require('../../userData.js');
var riskLines = require('../../riskLineData.js');

describe('审批管理添加风控线测试', function(){

    //登陆
    beforeAll(function() {
        browser.get('/finance/admin/login');
        var loginUsernameInput = element(by.id('inputUsername'));
        var loginPasswordInput = element(by.id('inputPassword'));
        var loginButton = element(by.css('.button'));

        loginUsernameInput.sendKeys(user.admin.username);
        loginPasswordInput.sendKeys(user.admin.password);
        loginButton.click();

        util.waitForUrlToChangeTo('/finance/admin/home/tasks/pending');
    });

    var breadNav = element(by.css('.breadcrumb .active'));
    var menuRiskLineManageButton, riskLineAddButton;

    // describe('进入风控线管理页面', function() {
    //     it('管理首页Title检查', function() {
    //         expect(browser.getTitle()).toEqual('供应链金融管理平台 - 管理首页- 审批');
    //     });
    //
    //     it('点击风控线菜单管理', function(){
    //         menuRiskLineManageButton = element(by.partialLinkText('风控线管理'));
    //         menuRiskLineManageButton.click();
    //         riskLineAddButton = element(by.partialButtonText('添加风控线'));
    //         browser.wait(browser.isElementPresent(riskLineAddButton), 5000);
    //         expect(breadNav.getText()).toEqual('风控线管理');
    //     });
    //
    // });

    describe('添加风控线', function(){

        var nameInput = element(by.id('nameInput'));
        var remarkInput = element(by.css('.risk-remark'));

        var successMessage = element(by.css('.alert-success'));
        var errorMessage = element(by.css('.alert-danger'));

        var riskLineSaveButton = element(by.partialButtonText('保存'));

        function inputUserData(isSaveSuccess, riskLine){

            nameInput.clear().sendKeys(riskLine.name);
            remarkInput.clear().sendKeys(riskLine.remark);
            riskLineSaveButton.click();

            if (isSaveSuccess){
                // util.waitForUrlToChangeTo('/finance/admin/home/task/pending');
                // expect(breadNav.getText()).toEqual('风控线管理');

                expect(successMessage.getAttribute('class')).toContain('');
                expect(successMessage.getText()).toEqual('保存成功!');
            }else{
                expect(errorMessage.getAttribute('class')).toContain('');
                expect(errorMessage.getText()).toEqual('此名称已存在!');
            }
        }

        beforeEach(function(){
            browser.get('/finance/admin/home/riskline/add');
            util.waitForUrlToChangeTo('/finance/admin/home/riskline/add');
        });

        // it('添加风控线页面Title检查', function() {
        //     expect(breadNav.getText()).toEqual('添加风控线');
        // });

        it('创建风控线1 成功', function() {
            inputUserData(true, riskLines.riskLine1);
        });

        it('创建风控线2 成功', function() {
            inputUserData(true, riskLines.riskLine2);
        });

        it('创建风控线3 成功', function() {
            inputUserData(true, riskLines.riskLine3);
        });

    });


});