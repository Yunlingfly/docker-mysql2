# 这个是构建Java环境的dockerfile

FROM registry.saas.hand-china.com/hap-cloud/base:latest

WORKDIR /

# 将系统编码设置为c.utf-8，默认的POSIX不支持中文
ENV LANG C.UTF-8
ENV LANGUAGE C.UTF-8
ENV LC_ALL C.UTF-8

# 将子项目打包的jar包拷贝到项目根目录
COPY target/Java1-1.0-SNAPSHOT.jar /test1.jar

# 设置容器启动时执行的命令,-Dfile.encoding=utf-8
CMD ["java", "-jar", "test1.jar"]