FROM eclipse-temurin:21-jre-alpine

EXPOSE 8080

ARG JAR_FILE=target/smartcity-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]