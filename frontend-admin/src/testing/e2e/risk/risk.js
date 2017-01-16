/**
 * Created by gonghuihui on 16/12/16.
 */

var user = require("../../userData.js");
var verify = require('../../verifyData.js');
var util = require('../../util.js');
describe("风控员", function () {
    var loginUserName = element(by.id('inputUsername'));
    var loginPassword = element(by.id('inputPassword'));
    var loginButton = element(by.css('.button'));
    var breadNav = element(by.css('.breadcrumb .active'));

    beforeAll(function () {
        browser.get('/finance/admin/login');
        loginUserName.sendKeys(user.riskmanager1.username);
        loginPassword.sendKeys(user.riskmanager1.password);
        loginButton.click();
        util.waitForUrlToChangeTo('/finance/admin/home/tasks/pending');
    });

    describe('风控员-我的待办', function () {
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
                console.log('-----rows-----' + rows.length);
                rows[0].all(by.tagName('td')).get(0).getText().then(function (code) {
                    code = code.split('\n');
                    expect(code[0]).toBe(verify.financecode);
                });
                rows[0].$('.btn').click();

            });
        });

        it('页面应该有"我的待办"', function () {
            // expect(browser.getTitle()).toEqual('供应链金融管理平台 - 管理首页- 审批');
            expect(breadNav.getText()).toEqual('我的待办');
        });

    });

    describe('风控报告', function () {

        var distributionCapability = element(by.css(".apply-table")).all(by.css('.item-min')).get(0).element(by.tagName('textarea'));
        var operationalRisk = element(by.css(".apply-table")).all(by.css('.item-min')).get(1).element(by.tagName('textarea'));
        var controlScheme = element(by.css(".apply-table")).all(by.css('.item-min')).get(2).element(by.tagName('textarea'));
        var controlConclusion = element(by.css(".apply-table")).all(by.css('.item-min')).get(3).element(by.tagName('textarea'));
        var upstremContract = element(by.css('.apply-table')).all(by.css('.item-title')).get(7).all(by.tagName('a')).get(1);
        var downstremContract = element(by.css('.apply-table')).all(by.css('.item-title')).get(7).all(by.tagName('a')).get(4);
        var verifyPass = element(by.css(".apply-table")).all(by.css('.item-title')).get(8).all(by.tagName('input')).get(0);
        var submit = element(by.partialButtonText('保存并提交'));
        // var keep=element(by.css('.apply-table')).element(by.css('.btn-save'));
        // var keep = aa.element(by.css('.btn-save'));
        // var successMessage = element(by.css('.alert-success'));
        // var errorMessage = element(by.css('.alert-danger'));

        it('风控报告页面点击"编辑上游合同"', function () {
            // expect(element(by.css('.apply-table')).all(by.css('.item-title')).count()).toBe(9);
            util.presenceOfAll(distributionCapability, 5000);
            distributionCapability.clear().sendKeys(verify.riskVerify.distributionCapability);
            operationalRisk.clear().sendKeys(verify.riskVerify.operationalRisk);
            controlScheme.clear().sendKeys(verify.riskVerify.controlScheme);
            controlConclusion.clear().sendKeys(verify.riskVerify.controlConclusion);
            upstremContract.click();
        });

        describe('上游合同', function () {
            it('编辑"上游合同"并提交', function () {
                var signDate = element(by.css('.icon-calendar'));
                util.presenceOfAll(signDate, 5000);
                signDate.click();
                var date = element(by.css('.currday'));
                util.presenceOfAll(date, 5000);
                date.click();
                var signAddress = element(by.css('.contract-table')).all(by.tagName('tr')).get(2).all(by.tagName('td')).get(1).element(by.tagName('input'));
                var shipName = element(by.css('.contract-table')).all(by.tagName('tr')).get(3).all(by.tagName('td')).get(1).element(by.tagName('input'));
                var shipTime = element(by.css('.contract-table')).all(by.tagName('tr')).get(3).all(by.tagName('td')).get(3).element(by.tagName('input'));
                var unloadPlace = element(by.css('.contract-table')).all(by.tagName('tr')).get(4).all(by.tagName('td')).get(1).element(by.tagName('input'));
                var addreviation = element(by.css('.contract-table')).all(by.tagName('tr')).get(4).all(by.tagName('td')).get(3).element(by.tagName('input'));
                var tonnage = element(by.css('.contract-table')).all(by.tagName('tr')).get(5).all(by.tagName('td')).get(1).element(by.tagName('input'));
                var deliveryPlace = element(by.css('.contract-table')).all(by.tagName('tr')).get(5).all(by.tagName('td')).get(3).element(by.tagName('input'));
                var variety = element(by.css('.contract-table')).all(by.tagName('tr')).get(6).all(by.tagName('td')).get(1).element(by.tagName('input'));
                var quantity = element(by.css('.contract-table')).all(by.tagName('tr')).get(6).all(by.tagName('td')).get(3).element(by.tagName('input'));
                var quantityRemarks = element(by.css('.contract-table')).all(by.tagName('tr')).get(7).all(by.tagName('td')).get(1).element(by.tagName('textarea'));
                var hot = element(by.css('.contract-table')).all(by.tagName('tr')).get(8).all(by.tagName('td')).get(1).element(by.tagName('input'));
                var liu = element(by.css('.contract-table')).all(by.tagName('tr')).get(8).all(by.tagName('td')).get(3).element(by.tagName('input'));
                var anotherQuality = element(by.css('.contract-table')).all(by.tagName('tr')).get(9).all(by.tagName('td')).get(1).element(by.tagName('textarea'));
                var qualityInspect = element(by.css('.contract-table')).all(by.tagName('tr')).get(10).all(by.tagName('td')).get(1).element(by.tagName('textarea'));
                var quanityInspect = element(by.css('.contract-table')).all(by.tagName('tr')).get(11).all(by.tagName('td')).get(1).element(by.tagName('textarea'));
                var settlementPrice = element(by.css('.contract-table')).all(by.tagName('tr')).get(12).all(by.tagName('td')).get(1).element(by.tagName('input'));
                var billPrice = element(by.css('.contract-table')).all(by.tagName('tr')).get(13).all(by.tagName('td')).get(1).all(by.tagName('input')).get(0);
                var billQuanity = element(by.css('.contract-table')).all(by.tagName('tr')).get(13).all(by.tagName('td')).get(1).all(by.tagName('input')).get(1);
                var buyerSum = element(by.css('.contract-table')).all(by.tagName('tr')).get(14).all(by.tagName('td')).get(1).element(by.tagName('input'));
                var specialAgreement = element(by.css('.contract-table')).all(by.tagName('tr')).get(15).all(by.tagName('td')).get(1).element(by.tagName('textarea'));
                var contractAppendcy = element(by.css('.contract-table')).all(by.tagName('tr')).get(16).all(by.tagName('td')).get(1).element(by.tagName('input'));
                var contractName = element(by.css('.contract-table')).all(by.tagName('tr')).get(17).all(by.tagName('td')).get(1).element(by.tagName('textarea'));

                var buyerCompany = element(by.css('.contract-table')).all(by.tagName('tr')).get(18).all(by.tagName('td')).get(1).element(by.tagName('input'));
                var buyer = element(by.css('.contract-table')).all(by.tagName('tr')).get(18).all(by.tagName('td')).get(3).element(by.tagName('input'));
                var buyerPhone = element(by.css('.contract-table')).all(by.tagName('tr')).get(19).all(by.tagName('td')).get(1).element(by.tagName('input'));
                var buyerEmail = element(by.css('.contract-table')).all(by.tagName('tr')).get(19).all(by.tagName('td')).get(3).element(by.tagName('input'));
                var buyerComAddress = element(by.css('.contract-table')).all(by.tagName('tr')).get(20).all(by.tagName('td')).get(1).element(by.tagName('input'));
                var buyerLegalRepresent = element(by.css('.contract-table')).all(by.tagName('tr')).get(20).all(by.tagName('td')).get(3).element(by.tagName('input'));
                var buyerBank = element(by.css('.contract-table')).all(by.tagName('tr')).get(21).all(by.tagName('td')).get(1).element(by.tagName('input'));
                var buyerBankAccount = element(by.css('.contract-table')).all(by.tagName('tr')).get(21).all(by.tagName('td')).get(3).element(by.tagName('input'));
                var sellerCom = element(by.css('.contract-table')).all(by.tagName('tr')).get(22).all(by.tagName('td')).get(1).element(by.tagName('input'));
                var seller = element(by.css('.contract-table')).all(by.tagName('tr')).get(22).all(by.tagName('td')).get(3).element(by.tagName('input'));
                var sellerPhone = element(by.css('.contract-table')).all(by.tagName('tr')).get(23).all(by.tagName('td')).get(1).element(by.tagName('input'));
                var sellerEmail = element(by.css('.contract-table')).all(by.tagName('tr')).get(23).all(by.tagName('td')).get(3).element(by.tagName('input'));
                var sellerComAddress = element(by.css('.contract-table')).all(by.tagName('tr')).get(24).all(by.tagName('td')).get(1).element(by.tagName('input'));
                var sellerLegalRepresent = element(by.css('.contract-table')).all(by.tagName('tr')).get(24).all(by.tagName('td')).get(3).element(by.tagName('input'));
                var sellerBank = element(by.css('.contract-table')).all(by.tagName('tr')).get(25).all(by.tagName('td')).get(1).element(by.tagName('input'));
                var sellerBankAccount = element(by.css('.contract-table')).all(by.tagName('tr')).get(25).all(by.tagName('td')).get(3).element(by.tagName('input'));

                signAddress.clear().sendKeys(verify.riskVerify.signAddress);
                shipName.clear().sendKeys(verify.riskVerify.shipName);
                shipTime.clear().sendKeys(verify.riskVerify.shipTime);
                unloadPlace.clear().sendKeys(verify.riskVerify.unloadPlace);
                addreviation.clear().sendKeys(verify.riskVerify.addreviation);
                tonnage.clear().sendKeys(verify.riskVerify.tonnage);
                deliveryPlace.clear().sendKeys(verify.riskVerify.deliveryPlace);
                variety.clear().sendKeys(verify.riskVerify.variety);
                quantity.clear().sendKeys(verify.riskVerify.quantity);
                quantityRemarks.clear().sendKeys(verify.riskVerify.quantityRemarks);
                hot.clear().sendKeys(verify.riskVerify.hot);
                liu.clear().sendKeys(verify.riskVerify.liu);
                anotherQuality.clear().sendKeys(verify.riskVerify.anotherQuality);
                qualityInspect.clear().sendKeys(verify.riskVerify.qualityInspect);
                quanityInspect.clear().sendKeys(verify.riskVerify.quanityInspect);
                settlementPrice.clear().sendKeys(verify.riskVerify.settlementPrice);
                billPrice.clear().sendKeys(verify.riskVerify.billPrice);
                billQuanity.clear().sendKeys(verify.riskVerify.billQuanity);
                buyerSum.clear().sendKeys(verify.riskVerify.buyerSum);
                specialAgreement.clear().sendKeys(verify.riskVerify.specialAgreement);
                contractAppendcy.clear().sendKeys(verify.riskVerify.contractAppendcy);
                contractName.clear().sendKeys(verify.riskVerify.contractName);
                buyerCompany.clear().sendKeys(verify.riskVerify.buyerCompany);
                buyer.clear().sendKeys(verify.riskVerify.buyer);
                buyerPhone.clear().sendKeys(verify.riskVerify.buyerPhone);
                buyerEmail.clear().sendKeys(verify.riskVerify.buyerEmail);
                buyerComAddress.clear().sendKeys(verify.riskVerify.buyerComAddress);
                buyerLegalRepresent.clear().sendKeys(verify.riskVerify.buyerLegalRepresent);
                buyerBank.clear().sendKeys(verify.riskVerify.buyerBank);
                buyerBankAccount.clear().sendKeys(verify.riskVerify.buyerBankAccount);
                sellerCom.clear().sendKeys(verify.riskVerify.sellerCom);
                seller.clear().sendKeys(verify.riskVerify.seller);
                sellerPhone.clear().sendKeys(verify.riskVerify.sellerPhone);
                sellerEmail.clear().sendKeys(verify.riskVerify.sellerEmail);
                sellerComAddress.clear().sendKeys(verify.riskVerify.sellerComAddress);
                sellerLegalRepresent.clear().sendKeys(verify.riskVerify.sellerLegalRepresent);
                sellerBank.clear().sendKeys(verify.riskVerify.sellerBank);
                sellerBankAccount.clear().sendKeys(verify.riskVerify.sellerBankAccount);
                // var contractKeep = element(by.css('.contract-table')).element(by.css('.btn-option')).all(by.css('.btn')).get(0);
                // contractKeep.click();
                var contractSubmit = element(by.partialButtonText('保存并提交'));
                contractSubmit.click();
            });
            it('风控报告页面点击"编辑下游合同"', function () {
                util.presenceOfAll(downstremContract, 5000);
                expect(downstremContract.getTagName()).toBe('a');
                // expect(element(by.css('.apply-table')).all(by.css('.item-title')).get(7).all(by.tagName('a')).count()).toBe('6')
                downstremContract.click();

            });
        });


        describe('下游合同', function () {
            it('编辑下游合同并提交', function () {
                var signDate2 = element(by.css('.icon-calendar'));
                expect(signDate2.getTagName()).toBe('span');
                util.presenceOfAll(signDate2, 5000);
                signDate2.click();
                var date2 = element(by.css('.currday'));
                util.presenceOfAll(date2, 5000);
                date2.click();
                var signAddress2 = element(by.css('.contract-table')).all(by.tagName('tr')).get(2).all(by.tagName('td')).get(1).element(by.tagName('input'));
                var shipName2 = element(by.css('.contract-table')).all(by.tagName('tr')).get(3).all(by.tagName('td')).get(1).element(by.tagName('input'));
                var shipTime2 = element(by.css('.contract-table')).all(by.tagName('tr')).get(3).all(by.tagName('td')).get(3).element(by.tagName('input'));
                var buyAdress2 = element(by.css('.contract-table')).all(by.tagName('tr')).get(4).all(by.tagName('td')).get(1).element(by.tagName('input'));
                var tonnage2 = element(by.css('.contract-table')).all(by.tagName('tr')).get(4).all(by.tagName('td')).get(3).element(by.tagName('input'));
                var variety2 = element(by.css('.contract-table')).all(by.tagName('tr')).get(5).all(by.tagName('td')).get(1).element(by.tagName('input'));
                var quantity2 = element(by.css('.contract-table')).all(by.tagName('tr')).get(5).all(by.tagName('td')).get(3).element(by.tagName('input'));
                var quantityRemarks2 = element(by.css('.contract-table')).all(by.tagName('tr')).get(6).all(by.tagName('td')).get(1).element(by.tagName('textarea'));
                var hot2 = element(by.css('.contract-table')).all(by.tagName('tr')).get(7).all(by.tagName('td')).get(1).element(by.tagName('input'));
                var liu2 = element(by.css('.contract-table')).all(by.tagName('tr')).get(7).all(by.tagName('td')).get(3).element(by.tagName('input'));
                var anotherQuality2 = element(by.css('.contract-table')).all(by.tagName('tr')).get(8).all(by.tagName('td')).get(1).element(by.tagName('textarea'));
                var deliveryPlace2 = element(by.css('.contract-table')).all(by.tagName('tr')).get(9).all(by.tagName('td')).get(1).element(by.tagName('input'));
                var paymentPeriods2 = element(by.css('.contract-table')).all(by.tagName('tr')).get(9).all(by.tagName('td')).get(3).element(by.tagName('input'));
                var quantityInspectTonnage2 = element(by.css('.contract-table')).all(by.tagName('tr')).get(10).all(by.tagName('td')).get(1).element(by.tagName('input'));
                var finalSum2 = element(by.css('.contract-table')).all(by.tagName('tr')).get(11).all(by.tagName('td')).get(1).element(by.tagName('input'));
                var bond2 = element(by.css('.contract-table')).all(by.tagName('tr')).get(12).all(by.tagName('td')).get(1).element(by.tagName('input'));
                var supplyName2 = element(by.css('.contract-table')).all(by.tagName('tr')).get(13).all(by.tagName('td')).get(1).element(by.tagName('input'));
                var contractNum2 = element(by.css('.contract-table')).all(by.tagName('tr')).get(13).all(by.tagName('td')).get(3).element(by.tagName('input'));
                var specialAgreement2 = element(by.css('.contract-table')).all(by.tagName('tr')).get(14).all(by.tagName('td')).get(1).element(by.tagName('textarea'));
                var contractAppendcy2 = element(by.css('.contract-table')).all(by.tagName('tr')).get(15).all(by.tagName('td')).get(1).element(by.tagName('input'));
                var contractName2 = element(by.css('.contract-table')).all(by.tagName('tr')).get(16).all(by.tagName('td')).get(1).element(by.tagName('textarea'));

                var buyerCompany2 = element(by.css('.contract-table')).all(by.tagName('tr')).get(17).all(by.tagName('td')).get(1).element(by.tagName('input'));
                var buyer2 = element(by.css('.contract-table')).all(by.tagName('tr')).get(17).all(by.tagName('td')).get(3).element(by.tagName('input'));
                var buyerPhone2 = element(by.css('.contract-table')).all(by.tagName('tr')).get(18).all(by.tagName('td')).get(1).element(by.tagName('input'));
                var buyerEmail2 = element(by.css('.contract-table')).all(by.tagName('tr')).get(18).all(by.tagName('td')).get(3).element(by.tagName('input'));
                var buyerComAddress2 = element(by.css('.contract-table')).all(by.tagName('tr')).get(19).all(by.tagName('td')).get(1).element(by.tagName('input'));
                var buyerLegalRepresent2 = element(by.css('.contract-table')).all(by.tagName('tr')).get(19).all(by.tagName('td')).get(3).element(by.tagName('input'));
                var buyerBank2 = element(by.css('.contract-table')).all(by.tagName('tr')).get(20).all(by.tagName('td')).get(1).element(by.tagName('input'));
                var buyerBankAccount2 = element(by.css('.contract-table')).all(by.tagName('tr')).get(20).all(by.tagName('td')).get(3).element(by.tagName('input'));
                var sellerCom2 = element(by.css('.contract-table')).all(by.tagName('tr')).get(21).all(by.tagName('td')).get(1).element(by.tagName('input'));
                var seller2 = element(by.css('.contract-table')).all(by.tagName('tr')).get(21).all(by.tagName('td')).get(3).element(by.tagName('input'));
                var sellerPhone2 = element(by.css('.contract-table')).all(by.tagName('tr')).get(22).all(by.tagName('td')).get(1).element(by.tagName('input'));
                var sellerEmail2 = element(by.css('.contract-table')).all(by.tagName('tr')).get(22).all(by.tagName('td')).get(3).element(by.tagName('input'));
                var sellerComAddress2 = element(by.css('.contract-table')).all(by.tagName('tr')).get(23).all(by.tagName('td')).get(1).element(by.tagName('input'));
                var sellerLegalRepresent2 = element(by.css('.contract-table')).all(by.tagName('tr')).get(23).all(by.tagName('td')).get(3).element(by.tagName('input'));
                var sellerBank2 = element(by.css('.contract-table')).all(by.tagName('tr')).get(24).all(by.tagName('td')).get(1).element(by.tagName('input'));
                var sellerBankAccount2 = element(by.css('.contract-table')).all(by.tagName('tr')).get(24).all(by.tagName('td')).get(3).element(by.tagName('input'));

                signAddress2.clear().sendKeys(verify.riskVerify.signAddress);
                shipName2.clear().sendKeys(verify.riskVerify.shipName);
                shipTime2.clear().sendKeys(verify.riskVerify.shipTime);
                buyAdress2.clear().sendKeys(verify.riskVerify.buyAdress2);
                tonnage2.clear().sendKeys(verify.riskVerify.tonnage);
                variety2.clear().sendKeys(verify.riskVerify.variety);
                quantity2.clear().sendKeys(verify.riskVerify.quantity);
                quantityRemarks2.clear().sendKeys(verify.riskVerify.quantityRemarks);
                hot2.clear().sendKeys(verify.riskVerify.hot);
                liu2.clear().sendKeys(verify.riskVerify.liu);
                anotherQuality2.clear().sendKeys(verify.riskVerify.anotherQuality);
                deliveryPlace2.clear().sendKeys(verify.riskVerify.deliveryPlace);
                paymentPeriods2.clear().sendKeys(verify.riskVerify.paymentPeriods2);
                quantityInspectTonnage2.clear().sendKeys(verify.riskVerify.quantityInspectTonnage2);
                finalSum2.clear().sendKeys(verify.riskVerify.finalSum2);
                bond2.clear().sendKeys(verify.riskVerify.bond2);
                supplyName2.clear().sendKeys(verify.riskVerify.supplyName2);
                contractNum2.clear().sendKeys(verify.riskVerify.contractNum2);
                specialAgreement2.clear().sendKeys(verify.riskVerify.specialAgreement);
                contractAppendcy2.clear().sendKeys(verify.riskVerify.contractAppendcy);
                contractName2.clear().sendKeys(verify.riskVerify.contractName);
                buyerCompany2.clear().sendKeys(verify.riskVerify.buyerCompany);
                buyer2.clear().sendKeys(verify.riskVerify.buyer);
                buyerPhone2.clear().sendKeys(verify.riskVerify.buyerPhone);
                buyerEmail2.clear().sendKeys(verify.riskVerify.buyerEmail);
                buyerComAddress2.clear().sendKeys(verify.riskVerify.buyerComAddress);
                buyerLegalRepresent2.clear().sendKeys(verify.riskVerify.buyerLegalRepresent);
                buyerBank2.clear().sendKeys(verify.riskVerify.buyerBank);
                buyerBankAccount2.clear().sendKeys(verify.riskVerify.buyerBankAccount);
                sellerCom2.clear().sendKeys(verify.riskVerify.sellerCom);
                seller2.clear().sendKeys(verify.riskVerify.seller);
                sellerPhone2.clear().sendKeys(verify.riskVerify.sellerPhone);
                sellerEmail2.clear().sendKeys(verify.riskVerify.sellerEmail);
                sellerComAddress2.clear().sendKeys(verify.riskVerify.sellerComAddress);
                sellerLegalRepresent2.clear().sendKeys(verify.riskVerify.sellerLegalRepresent);
                sellerBank2.clear().sendKeys(verify.riskVerify.sellerBank);
                sellerBankAccount2.clear().sendKeys(verify.riskVerify.sellerBankAccount);
                // var contractKeep = element(by.css('.btn-option')).all(by.css('.btn')).get(0);
                // contractKeep.click();
                var contractSubmit = element(by.partialButtonText('保存并提交'));
                expect(contractSubmit.getTagName()).toBe('button');
                contractSubmit.click();
            });
            it('风控报告审核通过并提交', function () {
                util.presenceOfAll(verifyPass, 5000);
                verifyPass.click();
                submit.click();
            });

        });



        // it('风控报告提交成功!', function () {
        //     // expect(successMessage.getAttribute('class')).toContain('');
        //     expect(errorMessage.getText()).toBe('dsfsdf');
        // });
    });
})
