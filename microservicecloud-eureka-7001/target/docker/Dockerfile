FROM jdk1.8
MAINTAINER qiupeng
ADD microservicecloud-eureka-7001.jar 7001.jar
ENV SPRING-CLOUD-EUREKA 172.16.36.171
RUN echo 'Asia/Shanghai' >/etc/timezone
EXPOSE 7001
ENTRYPOINT ["java","-jar","7001.jar"]


