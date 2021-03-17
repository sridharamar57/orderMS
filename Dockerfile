FROM openjdk:11.0-jdk-slim
VOLUME /tmp
COPY /target/orderMS1-0.0.1-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
EXPOSE 8600
ENV JAVA_OPTS=""
RUN sh -c "touch orderMS1-0.0.1-SNAPSHOT.jar"
ENTRYPOINT [ "java", "-jar", "orderMS1-0.0.1-SNAPSHOT.jar" ]
