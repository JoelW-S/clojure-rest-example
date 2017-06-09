FROM openjdk:8-jre-alpine

COPY target/clojure-rest-example-0.1.0-SNAPSHOT-standalone.jar /app.jar

RUN chmod +x /app.jar

EXPOSE 3000

ENTRYPOINT ["java", "-jar", "/app.jar"]
