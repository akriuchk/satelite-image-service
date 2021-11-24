FROM adoptopenjdk:11-jre-hotspot
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} app.jar
COPY source-data.json source-data.json
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]