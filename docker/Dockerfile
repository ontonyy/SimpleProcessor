FROM eclipse-temurin:17
ARG JAR_FILE=jar/app.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]