/* */ 
var webpack = require('webpack');
var path = require('path');
var fs = require('fs');
var json = require('./package.json!systemjs-json');
var version = json.version.split('.');
var v = (version.shift() + '.' + version.join('')).replace(/0+$/, "0");
var text = fs.readFileSync('./src/seed/lang.share.js', 'utf8');
text = text.replace(/version\s*\:\s*([^,]+)/, function(a, b) {
  return 'version: ' + JSON.stringify(v);
});
function heredoc(fn) {
  return fn.toString().replace(/^[^\/]+\/\*!?\s?/, '').replace(/\*\/[^\/]+$/, '').trim().replace(/>\s*</g, '><');
}
var feather = heredoc(function() {});
fs.writeFileSync('./src/seed/lang.share.js', text, 'utf8');
var now = new Date;
var snow = now.getFullYear() + '-' + (now.getMonth() + 1) + '-' + now.getDate() + ':' + now.getHours();
module.exports = {
  entry: {
    avalon: './src/avalon',
    'avalon.modern': './src/avalon.modern',
    'avalon.test': './src/avalon.test',
    'avalon.next': './src/avalon.next'
  },
  output: {
    path: path.join(__dirname, 'dist'),
    filename: '[name].js',
    libraryTarget: 'umd',
    library: 'avalon'
  },
  plugins: [new webpack.BannerPlugin('built in ' + snow + ' version ' + v + ' by 司徒正美\n' + feather)],
  module: {},
  eslint: {configFile: './eslintrc.json'},
  resolve: {
    extensions: ['.js', '', '.css'],
    alias: {avalon: './src/avalon'}
  }
};
