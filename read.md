#本项目为qiuppeng同学的毕业设计-基于docker的微服务架构设计-服务器端发现模式
##本项目后端框架springcloud  springboot 
##前端框架bootstrap 前端解析模板thymeleaf
##数据库 mysql
##部署容器 docker
##图片存储服务器  fastdfs
###部署环境 阿里云centeros7 [链接](http://121.41.85.42:9527)

###服务器端发现模式：
eureka 服务注册发现
ZUUL路由转发
springboot论坛项目为微服务的提供者（2份）

浏览器发送请求-》通过ZUUL自带的负载均衡转发-》eureka微服务注册表查找微服务的提供者

7001.dockerfile
FROM jdk1.8
ADD microservicecloud-eureka-7001.jar 7001.jar
ENV SPRING-CLOUD-HOSTNAME ####
EXPOSE  7001
ENTRYPOINT    ["java","-jar","-Duser.timezone=GMT+8","/7001.jar"]

8887 dockerfile
FROM jdk1.8
ADD microservicecloud-provider-dept-8887.jar 8887.jar
ENV SPRING-CLOUD-HOSTNAME ####
ENV SPRING-CLOUD-MYSQL-HOST ####
ENV IPADDR ####
ENV FDFS-TRANCKER-LIST 121.41.85.42:22122
EXPOSE  8887
ENTRYPOINT    ["java","-jar","-Duser.timezone=GMT+8","/8887.jar"]


8888 dockerfile
FROM jdk1.8
ADD microservicecloud-provider-dept-8888.jar 8888.jar
ENV SPRING-CLOUD-HOSTNAME ####
ENV SPRING-CLOUD-MYSQL-HOST ####
ENV IPADDR ####
ENV FDFS-TRANCKER-LIST 121.41.85.42:22122
EXPOSE  8887
ENTRYPOINT    ["java","-jar","-Duser.timezone=GMT+8","/8888.jar"]

9527 dockerfile
FROM jdk1.8
ADD microservicecloud-zuul-gateway-9527.jar 9527.jar
ENV SPRING-CLOUD-HOSTNAME 172.16.36.171
ENV MICROSERVICE-8887 http://#####:8887
ENV MICROSERVICE-8888 http://#####:8888

EXPOSE 9527
ENTRYPOINT    ["java","-jar","-Duser.timezone=GMT+8","/9527.jar"]

