FROM jdk1.8
MAINTAINER qiupeng
ADD microservicecloud-zuul-gateway-9527.jar 9527.jar
ENV SPRING-CLOUD-HOSTNAME 172.16.36.171
ENV MICROSERVICE-8887 http://121.41.85.42:8887
ENV MICROSERVICE-8888 http://121.41.85.42:8888
RUN echo 'Asia/Shanghai' >/etc/timezone
EXPOSE 9527
ENTRYPOINT ["java","-jar","9527.jar"]


