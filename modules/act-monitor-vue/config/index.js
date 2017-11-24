// see http://vuejs-templates.github.io/webpack for documentation.
var path = require('path')

module.exports = {
    build: {
        env: require('./prod.env'),
        /*
         TODO 独立部署   path.resolve路径 ../../act-monitor-web/src/main/webapp/index.html
         TODO 子系统部署 path.resolve路径 ../../act-monitor-web/src/main/webapp/WEB-INF/views/common/index.jsp 加入<%@ page pageEncoding="utf-8"%>
         */
        index: path.resolve(__dirname, '../../act-monitor-web/src/main/webapp/index.html'),
        assetsRoot: path.resolve(__dirname, '../../act-monitor-web/src/main/webapp'),
        assetsSubDirectory: 'static',
        assetsPublicPath: '/act-monitor-web/',
        productionSourceMap: true,
        // Gzip off by default as many popular static hosts such as
        // Surge or Netlify already gzip all static assets for you.
        // Before setting to `true`, make sure to:
        // npm install --save-dev compression-webpack-plugin
        productionGzip: false,
        productionGzipExtensions: ['js', 'css']
    },
    dev: {
        env: require('./dev.env'),
        port: 8083,
        autoOpenBrowser: true,
        assetsSubDirectory: 'static',
        assetsPublicPath: '/',
        /*使用代理*/
        proxyTable: {
            '/act-monitor-web/': {
                target: 'http://127.0.0.1:8080',
                changeOrigin: true,
                pathRewrite: {
                    '^/act-monitor-web': '/act-monitor-web'
                }
            }
        },
        // CSS Sourcemaps off by default because relative paths are "buggy"
        // with this option, according to the CSS-Loader README
        // (https://github.com/webpack/css-loader#sourcemaps)
        // In our experience, they generally work as expected,
        // just be aware of this issue when enabling this option.
        cssSourceMap: false
    }
}
