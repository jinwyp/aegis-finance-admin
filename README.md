# aegis-finance-admin
# 供应链金融管理员平台

## 后端环境

1. 第一次运行本项目, 需要先创建数据库, 在aegis-finance-admin 目录下执行 ``` sh initdatabase.sh ```
2. 运行项目 在aegis-finance-admin 目录下执行 ``` mvn clean spring-boot:run ``` 管理后台地址: http://127.0.0.1:8002/finance/admin, 网站地址 http://127.0.0.1:8002/finance
3. 目前本项目只能在本地运行, docker还没配置好
4. 全部的URL和API, 请看docs目录下的 readme

还要启动 aegis-member 项目,切换到 finance-admin 分支 运行make local
还要启动 aegis-service 项目 运行 gradle clean bootRun     

把域名 finance-local.yimei180.com 加入到hosts里面 127.0.0.1      finance-local.yimei180.com




## finance 管理后台 frontend-admin 前端环境

- 安装 Typescript 编译器。 进入 frontend-admin/src 下运行 ``` npm install -g typescript ```
- 安装依赖, 进入 frontend-admin/src 下运行 ``` SASS_BINARY_SITE=https://npm.taobao.org/mirrors/node-sass/ npm install --registry=https://registry.npm.taobao.org --phantomjs_cdnurl=https://npm.taobao.org/mirrors/phantomjs ```
- 运行 gulp dev 进行ts编译,并监视 ts 文件修改自动编译成 js
- 调试 angular2的chrome 工具 [Augury](https://augury.angular.io/)  



## finance 网站 frontend-site 前端环境

- 使用Gulp 作为前端编译工具  ``` npm install gulp -g ```  
- 使用jspm 替代bower 作为前端包管理工具  ``` npm install jspm -g ``` [查找库](http://jspm.io/docs/installing-packages.html) [参考文章](https://www.sitepoint.com/modular-javascript-systemjs-jspm/)
- 进入 frontend-site/src 下运行 ``` npm install ```  安装前端代码编译工具依赖库 ``` SASS_BINARY_SITE=https://npm.taobao.org/mirrors/node-sass/ npm install --registry=https://registry.npm.taobao.org --phantomjs_cdnurl=https://npm.taobao.org/mirrors/phantomjs ```
- 运行 ``` jspm install ```  安装前端代码依赖库
- [ES6 模块系统解析](https://segmentfault.com/a/1190000003410285)  [ES6 入门](http://es6.ruanyifeng.com/#docs/module)
- 开发环境下 运行 gulp dev 进入监视 sass 文件改动自动编译css, 使用 gulp build 进行生产环境打包


## 仓押 管理后台 frontend-cangya 前端环境

- 使用Gulp 作为前端编译工具  ``` npm install gulp -g ```  
- 使用webpack 前端打包管理工具  ``` npm install webpack webpack-dev-server -g ``` 
- 进入 frontend-cangya/src 下运行 ``` npm install ```  安装前端代码编译工具依赖库 ``` SASS_BINARY_SITE=https://npm.taobao.org/mirrors/node-sass/ npm install --registry=https://registry.npm.taobao.org --phantomjs_cdnurl=https://npm.taobao.org/mirrors/phantomjs ```
- 运行 ``` npm install ```  安装前端代码依赖库
- 开发环境下 运行 npm run dev 进入监视 sass 文件改动自动编译css, 使用 gulp build 进行生产环境打包


### 
- npm 安装 node-sass 网速慢的 可以 运行 ```npm config set registry https://registry.npm.taobao.org```  
- 然后 编辑 ~/.npmrc 加入下面内容
```
registry=https://registry.npm.taobao.org
sass_binary_site=https://npm.taobao.org/mirrors/node-sass/
phantomjs_cdnurl=http://npm.taobao.org/mirrors/phantomjs
ELECTRON_MIRROR=http://npm.taobao.org/mirrors/electron/
```

## 单元测试 

- 安装 Karma 命令行 ``` npm install -g karma-cli --registry=https://registry.npm.taobao.org --phantomjs_cdnurl=https://npm.taobao.org/mirrors/phantomjs
- 进入 frontend-site/src 下运行``` karma start karma.conf.js ``` 或 ``` npm test ```
- 进入 frontend-admin/src 下运行``` karma start karma.conf.js ``` 或 ``` npm test ```


## E2E 测试 端到端的场景测试 

- 安装 [Protractor](http://www.protractortest.org/) 命令行 ``` npm install -g protractor ```
- 运行 ``` webdriver-manager update ```  有可能下载失败，网速太慢，需要翻墙
- （可以不用运行该命令）运行 ``` webdriver-manager start ```  启动 Selenium Server at http://localhost:4444/wd/hub.
- 进入 frontend-admin/src 运行 ``` protractor ``` 或 ``` npm run e2e ``` 开始进行测试, 完成后打开 frontend-admin/src/testing/report/htmlReport.html 查看测试结果                                  



## docker环境准备
1. 从以下仓库获取最新代码: aegis-docker, docker-nginx, git@github.com:yimei180/docker-redis-finance.git
2. 进入docker-mysql目录, 运行 ```make start enter``` , 然后进入数据库, 创建 financedb 数据库






### API 规范 请看DOCS 目录 readme


### Swagger文档查看
http://localhost:8002/swagger/swagger-ui.html


