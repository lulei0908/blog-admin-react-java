FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY pom.xml .
COPY src ./src
COPY mvnw .

RUN chmod +x mvnw

RUN ./mvnw dependency:go-offline -B

RUN ./mvnw package -DskipTests -B

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=0 /app/target/*.jar app.jar
COPY uploads/ ./uploads/

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
