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

    beforeAll(function() {
        browser.get('/finance/admin/login');
        loginUsernameInput.sendKeys(user.xianguan1.username);
        loginPasswordInput.sendKeys(user.xianguan1.password);

        loginButton.click();
        util.waitForUrlToChangeTo('/finance/admin/home/tasks/pending');
    });

    describe('测试数据点击', function () {
        var name = 'JR201612020002';
        // util.waitForElementToBePresent(table);
        it('测试', function () {
            var trs = element(by.css('.info-table tbody')).all(by.css('.item'));
            var btn = element(by.partialButtonText('分配'));
            browser.wait(util.presenceOfAll(trs),10000);
            // browser.wait(browser.isElementPresent(btn), 5000);

            return trs.filter(function(row) {
                // Get the second column's text.
                return row.$$('td').get(0).getText().then(function(rowName) {
                    // Filter rows matching the name you are looking for.
                    rowName = rowName.split('\n');
                    // expect(rowName[0]).toMatch(name);
                    return rowName[0] === name;
                });
            }).then(function(rows) {
                rows[0].$('.btn').click();
            });

            // expect(true).toBe(true);
        })
    });

});
