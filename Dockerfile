FROM eclipse-temurin:26-jdk AS build

WORKDIR /app

COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .

RUN chmod +x mvnw

RUN ./mvnw dependency:go-offline

COPY src ./src

RUN ./mvnw clean package -DskipTests


FROM eclipse-temurin:26-jre

WORKDIR /app

COPY --from=build /app/target/*.jar posts.jar

EXPOSE 8080
EXPOSE 5005

ENTRYPOINT ["java", "-jar", "posts.jar"]
