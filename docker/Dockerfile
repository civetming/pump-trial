# 指定基础镜像
From registry.cn-shenzhen.aliyuncs.com/sibionics_dev/openjdk:17.0.1-oracle

ARG JAR_VERSION

WORKDIR /usr/local/cgmdc-sys

# 复制 Spring Boot 包
COPY target/pump-trial-service-${JAR_VERSION}.jar ./app.jar

# 设置时区
# RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone

# 执行启动命令
# ENTRYPOINT exec java -Xmx2g -Xms256m -Xmn128m -XX:+UseG1GC -XX:+HeapDumpOnOutOfMemoryError --illegal-access=permit -Duser.timezone=Asia/Shanghai -jar /app.jar
