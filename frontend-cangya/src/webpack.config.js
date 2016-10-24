/**
 * Created by JinWYP on 24/10/2016.
 */

var path = require("path");
var webpack = require('webpack');

module.exports = {
    entry: {
        adminLogin : './js/page/adminLogin.js',
        adminHome : './js/page/adminHome.js'
    },
    output: {
        path: path.join(__dirname, "../dist/js"),
        filename: "[name].bundle.js",
        chunkFilename: "[id].chunk.js",
        publicPath : '/'
    },


    module: {
        rules: [

        ]
    },

    plugins: [
        new webpack.ProvidePlugin({
            $: "jquery",
            jQuery: "jquery",
            jquery: 'jquery'

        }),

        new webpack.optimize.CommonsChunkPlugin({
            filename: "common.js",
            name: "common"
        })

    ],


    devServer: {
        contentBase: path.join(__dirname),
        publicPath: "/js/",
        compress: true,
        port: 9000,
        proxy: {
            "/api": "http://localhost:3000"
        }
    }
};
