
Voici un guide pas-à-pas pour configurer, exécuter et tester votre application **Spring Boot (Java 21 + PostgreSQL + Maven 3.8)** dans un conteneur au sein de **GitHub Codespaces** directement depuis votre iPad (via navigateur ou VS Code Web).

---

## 🛠️ Étape 1 : Fichiers de Configuration dans le Projet

Pour faire tourner l'application dans Codespaces avec PostgreSQL, ajoutez ces 3 fichiers à la racine de votre dépôt Git.

### 1. `docker-compose.yml`

Créez un fichier `docker-compose.yml` à la racine pour orchestrer votre base de données PostgreSQL et votre application Java :

```yaml
version: '3.8'

services:
  db:
    image: postgres:16.4
    container_name: postgres_db
    environment:
      POSTGRES_DB: restaurant
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgrespassword
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data

  app:
    build: .
    container_name: spring_app
    ports:
      - "8080:8080"
    depends_on:
      - db
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://db:5432/restaurant
      SPRING_DATASOURCE_USERNAME: postgres
      SPRING_DATASOURCE_PASSWORD: postgrespassword
      SPRING_JPA_HIBERNATE_DDL_AUTO: update

volumes:
  postgres_data:

```

---

### 2. `Dockerfile`

Créez un fichier `Dockerfile` à la racine pour builder et exécuter votre projet avec **Java 21** et **Maven** :

```dockerfile
# Étape 1 : Build Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Étape 2 : Exécution Java 21
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

```

---

### 3. Fichier de configuration `application.properties`

Assurez-vous que votre fichier `src/main/resources/application.properties` contient les éléments nécessaires pour PostgreSQL et JPA (Hibernate) :

```properties
spring.application.name=restaurant
spring.datasource.url=${SPRING_DATASOURCE_URL:jdbc:postgresql://localhost:5432/restaurant}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME:postgres}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD:postgrespassword}
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=${SPRING_JPA_HIBERNATE_DDL_AUTO:update}
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.show-sql=true

```

---

## 🚀 Étape 2 : Lancement dans GitHub Codespaces depuis l'iPad

1. **Ouvrez le terminal dans VS Code / Codespaces** sur votre iPad.
2. **Démarrez les conteneurs Docker** :

```bash
docker-compose up --build -d

```

3. **Vérifier le statut des conteneurs** :

```bash
docker-compose ps

```

---

## 🔍 Étape 3 : Suivi des logs et Accès à l'Application

* **Voir les logs Spring Boot en temps réel :**

```bash
docker-compose logs -f app

```

* **Accéder au shell PostgreSQL (`psql 16.4`) dans le conteneur :**

```bash
docker exec -it postgres_db psql -U postgres -d restaurant

```

---

## 🌐 Remarque sur l'accès depuis l'iPad

Une fois le conteneur démarré sur le port `8080`, VS Code Web dans Codespaces affichera une notification en bas à droite indiquant que le port `8080` a été transféré (*Forwarded Port*).

Vous n'avez qu'à cliquer sur **Open in Browser** (ou ouvrir l'onglet **Ports** dans le panneau inférieur de VS Code) pour accéder à l'interface de votre application de gestion de restaurant (`/dashboard`, `/ingredients`, `/commandes`, etc.).
