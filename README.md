# aegis-finance-admin
# 供应链金融管理员平台

## 后端环境

1. 第一次运行本项目, 需要先创建数据库, 在aegis-finance-admin 目录下执行 ``` sh initdatabase.sh ```
2. 运行项目 在aegis-finance-admin 目录下执行 ``` mvn spring-boot:run ``` 本地地址: http://127.0.0.1:8002/
3. 目前本项目只能在本地运行, docker还没配置好


## 前端环境

- 安装 Typescript 编译器。 进入frontend/src 下运行 ``` npm install -g typescript ```
- 安装依赖, 进入frontend/src 下运行 ``` npm install --registry=https://registry.npm.taobao.org --phantomjs_cdnurl=https://npm.taobao.org/mirrors/phantomjs ```
- 运行 gulp dev 进行ts编译,并监视 ts 文件修改自动编译成 js
- 调试 angular2的chrome 工具 [Augury](https://augury.angular.io/)  



### API 规范 请看DOCS 目录