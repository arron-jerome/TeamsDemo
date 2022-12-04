#FROM registry.cn-shanghai.aliyuncs.com/arron_jerome/java-skywalking-base:v1
FROM openjdk:8-jdk-alpine
#VOLUME /tmp
#RUN addgroup -S spring && adduser -S spring -G spring
#USER spring:spring
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
