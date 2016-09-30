/**
 * Created by JinWYP on 9/29/16.
 */

exports.config = {
    framework: 'jasmine',
    seleniumAddress: 'http://localhost:4444/wd/hub',
    specs: [
        'testing/e2e/**/*.js'
    ],

    baseUrl: 'http://finance-local.yimei180.com:8002',

    /**
     * Angular 2 configuration
     *
     * useAllAngular2AppRoots: tells Protractor to wait for any angular2 apps on the page instead of just the one matching
     * `rootEl`
     *
     */
    useAllAngular2AppRoots: true
}