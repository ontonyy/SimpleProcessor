FROM gradle:8.5 AS build
COPY . /temp
WORKDIR /temp
RUN gradle clean build --no-daemon

FROM eclipse-temurin:21

# If check bootJar then you will see that in /docker/jar/*.jar creating file, because was changed destination dir
COPY --from=build /temp/configs/jar/app.jar /app/app.jar
COPY /configs/jar/opentelemetry-javaagent.jar /app/opentelemetry-javaagent.jar
WORKDIR /app
EXPOSE 8080
ENTRYPOINT ["java", "-javaagent:opentelemetry-javaagent.jar", "-jar", "app.jar"]
