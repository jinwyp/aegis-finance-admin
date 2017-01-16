/**
 * Created by gonghuihui on 16/12/29.
 */

var casper = require('casper').create();

casper.start('http://www.baidu.com/', function() {
    this.echo(this.getTitle());
});

casper.thenOpen('http://www.taobao.com', function() {
    this.echo(this.getTitle());
});


casper.run();