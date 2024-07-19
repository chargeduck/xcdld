#openjdk:8-jre 为基础镜像，来构建此镜像，可以理解为运行的需要基础环境
FROM openjdk:8-jre
#WORKDIR指令用于指定容器的一个目录， 容器启动时执行的命令会在该目录下执行。
ENV MYPATH /home
WORKDIR $MYPATH
VOLUME $MYPATH
#将当前metabase.jar 复制到容器根目录下
ADD ./target/xcdld-1.0-SNAPSHOT.jar xcdld.jar
#暴露容器端口为9528 Docker镜像告知Docker宿主机应用监听了9528端口
EXPOSE 9528
#容器启动时执行的命令
#CMD java -jar xcdld.jar --spring.profiles.active=dev > logs/xcdld.log
ENTRYPOINT ["java", "-jar", "xcdld.jar", "--spring.profiles.active=dev","> logs/xcdld.log"]
ENV TZ=Asia/Shanghai