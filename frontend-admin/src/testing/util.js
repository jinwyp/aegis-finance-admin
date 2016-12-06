/**
 * Created by JinWYP on 10/10/2016.
 */



function waitForUrlToChangeTo(url) {
    browser.driver.wait(function () {
        return browser.driver.getCurrentUrl().then(function (resultUrl) {
            return resultUrl.includes(url);
        });
    }, 5 * 1000, '等待5秒钟');
}


function waitForElementToBePresent(element){
    return browser.wait(function () {
        return browser.isElementPresent(by.css(element));
    }, 10000);
}




function presenceOfAll(elementArrayFinder) {
    return function () {
        return elementArrayFinder.count(function (count) {
            return count > 0;
        });
    };
}

module.exports = {
    waitForUrlToChangeTo : waitForUrlToChangeTo,
    waitForElementToBePresent : waitForElementToBePresent,
    presenceOfAll : presenceOfAll
};