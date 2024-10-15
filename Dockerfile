FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY target/user-experience-service-1.0.0-RELEASE.jar /app/app.jar
EXPOSE 8090
ENTRYPOINT ["java","-jar","/app/app.jar"]