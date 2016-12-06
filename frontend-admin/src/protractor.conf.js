/**
 * Created by JinWYP on 9/29/16.
 */

//获取终端命令行参数测试
// process.argv.forEach(function (val, index) {
//
//     var match = process.argv[index].match(/^--params\.([^=]+)=(.*)$/);
//
//     console.log("------", index, val);
//     console.log("------", match);
// });

var options = process.argv[2].match(/^--params\.([^=]+)=(.*)$/);



var specsStep1 = [
    // 'testing/e2e/**/*.js'
    // 'testing/e2e/superadmin/**/*.js',

    'testing/e2e/trader-manager/verify.js',

    // 'testing/e2e/superadmin/login.js',
    // 'testing/e2e/superadmin/create-risk-line.js',
    // 'testing/e2e/superadmin/create-users.js'
];
var specsStep2 = [
    'testing/e2e/admin/create-users-g1.js',
    'testing/e2e/admin/create-users-g2.js',
    'testing/e2e/admin/create-users-g3.js'
];


function getSpecs (currentStep){
    console.log('-----------------------');
    console.log(currentStep);
    if (currentStep === 'superadmin') return specsStep1;
    if (currentStep === 'admin') return specsStep2;
}


exports.config =  {
    framework: 'jasmine',
    seleniumAddress: 'http://localhost:4444/wd/hub',
    specs: getSpecs(options[2]),

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
    useAllAngular2AppRoots: true,

    params : {
        step : 'superadmin'
    }
};