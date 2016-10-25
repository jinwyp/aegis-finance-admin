/**
 * Created by JinWYP on 24/10/2016.
 */

var path = require("path");
var webpack = require('webpack');

module.exports = {
    entry: {
        // common: ['jquery','avalon2'],
        adminLogin : './js/page/adminLogin.js',
        adminHome : './js/page/adminHome.js'
    },
    output: {
        path: path.join(__dirname, "js/page-temp-bundle"),
        filename: "[name].bundle.js",
        chunkFilename: "[id].chunk.js",
        publicPath : '/'
    },


    module: {
        loaders: [

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
