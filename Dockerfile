FROM ubuntu-1404

MAINTAINER hary <94093146@qq.com>

ADD build/libs/aegis-finance-admin-1.0-SNAPSHOT.jar  /app/aegis-finance-admin/lib/

RUN mkdir /app/aegis-finance-admin/logs

RUN mkdir /app/aegis-finance-admin/config

WORKDIR /app/aegis-finance-admin

# 配置可被挂在
VOLUME /app/aegis-finance-admin/config
VOLUME /app/aegsi-finance-admin/logs

EXPOSE 8001/tcp

CMD  java -jar lib/aegis-finance-admin-1.0-SNAPSHOT.jar
