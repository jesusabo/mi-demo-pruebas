FROM openjdk:17-alpine
COPY ./target/*.jar products.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "products.jar"]