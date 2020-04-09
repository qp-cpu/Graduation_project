FROM jdk1.8
MAINTAINER qiupeng
ADD microservicecloud-provider-dept-8887.jar 8887.jar
ENV SPRING-CLOUD-HOSTNAME 172.16.36.171
ENV SPRING-CLOUD-MYSQL-HOST  172.17.0.3
ENV IPADDR 121.41.85.42
RUN echo 'Asia/Shanghai' >/etc/timezone
EXPOSE 8887
ENTRYPOINT ["java","-jar","8887.jar"]


