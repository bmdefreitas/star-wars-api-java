FROM openjdk:11

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java","-Dspring.data.mongodb.uri=mongodb://mongodb:27017/starwars", "-jar","/app.jar"]
