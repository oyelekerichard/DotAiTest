# Dockerfile
FROM openjdk:11-jre-slim
VOLUME /tmp
COPY target/bankapp.jar bankapp.jar
ENTRYPOINT ["java","-jar","/bankapp.jar"]