# aegis-finance-admin
供应链金融管理员平台

1,在终端里安装flyway
brew install flyway

2,第一次运行本项目, 需要先创建数据库
  在aegis-finance-admin 目录下执行
sh initdatabase.sh

3,更新数据库,
  在aegis-finance-admin 目录下执行
  flywayMigrate
