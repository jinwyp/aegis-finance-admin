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


module.exports = {
    waitForUrlToChangeTo : waitForUrlToChangeTo
};