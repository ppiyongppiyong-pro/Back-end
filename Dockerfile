FROM openjdk:17-jdk-alpine

ARG JAR_FILE=build/libs/Back-end-0.0.1-SNAPSHOT.jar

WORKDIR /app

COPY ${JAR_FILE} ppiyong.jar

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "-Duser.timezone=Asia/Seoul", "ppiyong.jar"]