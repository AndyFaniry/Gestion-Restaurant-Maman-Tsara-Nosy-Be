# Étape 1 : Build Maven (Java 21)
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Optimisation du cache Docker pour les dépendances Maven
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copie des sources et compilation
COPY src ./src
RUN mvn clean package -DskipTests

# Étape 2 : Exécution Java 21 JRE
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copie du jar compilé
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]