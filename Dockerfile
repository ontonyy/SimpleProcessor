FROM gradle:8.2.1 AS build
COPY . /temp
WORKDIR /temp
RUN gradle clean build --no-daemon

FROM eclipse-temurin:17

# If check bootJar then you will see that in /docker/jar/*.jar creating file, because was changed destination dir
COPY --from=build /temp/docker/jar/*.jar /app/app.jar
WORKDIR /app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
