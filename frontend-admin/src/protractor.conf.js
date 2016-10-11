/**
 * Created by JinWYP on 9/29/16.
 */

exports.config = {
    framework: 'jasmine',
    seleniumAddress: 'http://localhost:4444/wd/hub',
    specs: [
        // 'testing/e2e/**/*.js',
        'testing/e2e/create-users.js',
    ],

    baseUrl: 'http://finance-local.yimei180.com:8002',

    multiCapabilities: [
        // { browserName: 'safari'},
        { browserName: 'chrome', chromeOptions: {'args': ['show-fps-counter=true']}}
    ],

    allScriptsTimeout: 120 * 1000,

    jasmineNodeOpts: {
        showTiming: true,
        showColors: true,
        includeStackTrace: false,
        defaultTimeoutInterval: 400000
    },

    directConnect: true,

    onPrepare: function () {
        var SpecReporter = require('jasmine-spec-reporter');
        var Jasmine2HtmlReporter = require('protractor-jasmine2-html-reporter');
        // add jasmine spec reporter
        jasmine.getEnv().addReporter(new SpecReporter({
            displayStacktrace       : 'all',
            displaySuccessesSummary : true,
            displaySpecDuration     : true,
            displaySuiteNumber      : true
        }));

        jasmine.getEnv().addReporter(new Jasmine2HtmlReporter({
            savePath: 'testing/report/'
        }));

        // browser.ignoreSynchronization = true;
    },

    /**
     * Angular 2 configuration
     *
     * useAllAngular2AppRoots: tells Protractor to wait for any angular2 apps on the page instead of just the one matching
     * `rootEl`
     *
     */
    useAllAngular2AppRoots: true
};