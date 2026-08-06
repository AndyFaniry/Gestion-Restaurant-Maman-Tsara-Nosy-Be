# Gestion Restaurant — Maman Tsara (Nosy Be)

Application web de gestion pour le restaurant **Maman Tsara** (Nosy Be) : stocks, recettes, commandes, caisse, personnels, matériels et livraisons.

---

## Sommaire

- [Stack technique](#stack-technique)
- [Prérequis](#prérequis)
- [Démarrage rapide (Docker)](#démarrage-rapide-docker)
- [Installation locale](#installation-locale)
- [Configuration](#configuration)
- [Accès à l’application](#accès-à-lapplication)
- [Structure du projet](#structure-du-projet)
- [Modules fonctionnels](#modules-fonctionnels)
- [Base de données](#base-de-données)
- [Scripts utiles](#scripts-utiles)
- [Dépannage](#dépannage)

---

## Stack technique

| Composant | Version / techno |
|-----------|------------------|
| Langage | Java **21** |
| Framework | Spring Boot **3.5.4** |
| UI | Thymeleaf + HTML/CSS (Font Awesome) |
| Persistance | Spring Data JPA / Hibernate |
| Base de données | PostgreSQL **16** |
| Build | Maven (wrapper `mvnw` / `mvnw.cmd`) |
| Conteneurisation | Docker + Docker Compose |

Architecture : **MVC** — Controllers → Services (logique métier) → Repositories → Entités JPA.

---

## Prérequis

### Option A — Docker (recommandé)

- [Docker Desktop](https://www.docker.com/products/docker-desktop/) (Windows/Mac) ou Docker Engine + Compose (Linux)
- Ports libres : **8080** (app) et **5432** (PostgreSQL)

### Option B — Installation locale

- JDK **21**
- Maven **3.9+** (ou utiliser le wrapper `./mvnw` / `mvnw.cmd`)
- PostgreSQL **16** (ou compatible)
- Ports libres : **8080** et **5432**

---

## Démarrage rapide (Docker)

À la racine du projet :

```bash
docker compose up --build -d
```

Vérifier que les conteneurs tournent :

```bash
docker compose ps
```

Suivre les logs de l’application :

```bash
docker compose logs -f app
```

Ouvrir l’application : [http://localhost:8080](http://localhost:8080)

Arrêter :

```bash
docker compose down
```

Supprimer aussi les données PostgreSQL (volume) :

```bash
docker compose down -v
```

### Conteneurs créés

| Service | Conteneur | Port |
|---------|-----------|------|
| Application Spring Boot | `mon_app_java` | `8080` |
| PostgreSQL 16 | `mon_postgres_db` | `5432` |

Identifiants DB (Docker) :

- Base : `restaurant`
- Utilisateur : `dev1`
- Mot de passe : `dev`

---

## Installation locale

### 1. Cloner le dépôt

```bash
git clone <url-du-repo>
cd Gestion-Restaurant-Maman-Tsara-Nosy-Be
```

### 2. Préparer PostgreSQL

Créer l’utilisateur et la base (exemple sous Linux / WSL) :

```bash
sudo systemctl start postgresql
sudo -u postgres psql
```

Dans `psql` :

```sql
CREATE USER dev1 WITH PASSWORD 'dev';
CREATE DATABASE restaurant OWNER dev1;
GRANT ALL PRIVILEGES ON DATABASE restaurant TO dev1;
\c restaurant
GRANT ALL ON SCHEMA public TO dev1;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO dev1;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON SEQUENCES TO dev1;
```

Sous Windows (pgAdmin ou `psql`) : créer une base `restaurant` et un utilisateur `dev1` / mot de passe `dev`.

> Les scripts d’aide sont aussi dans `database/init.sql` (à adapter si besoin).

### 3. Lancer l’application

**Windows (PowerShell / CMD) :**

```bat
mvnw.cmd spring-boot:run
```

**Linux / macOS :**

```bash
./mvnw spring-boot:run
```

Ou via le script fourni :

```bash
./compile.sh
```

(`compile.sh` exécute `mvn clean compile` puis `mvn spring-boot:run`.)

### 4. Build du JAR (optionnel)

```bash
./mvnw clean package -DskipTests
java -jar target/restaurant-0.0.1-SNAPSHOT.jar
```

---

## Configuration

Fichier principal : `src/main/resources/application.properties`

| Propriété | Défaut | Description |
|-----------|--------|-------------|
| `SPRING_DATASOURCE_URL` | `jdbc:postgresql://localhost:5432/restaurant` | URL JDBC |
| `SPRING_DATASOURCE_USERNAME` | `dev1` | Utilisateur DB |
| `SPRING_DATASOURCE_PASSWORD` | `dev` | Mot de passe DB |
| `SPRING_JPA_HIBERNATE_DDL_AUTO` | `update` | Mode schéma Hibernate |

Exemple d’override en local :

```bash
# Windows PowerShell
$env:SPRING_DATASOURCE_URL="jdbc:postgresql://localhost:5432/restaurant"
$env:SPRING_DATASOURCE_USERNAME="dev1"
$env:SPRING_DATASOURCE_PASSWORD="dev"
.\mvnw.cmd spring-boot:run
```

```bash
# Linux / macOS
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/restaurant
export SPRING_DATASOURCE_USERNAME=dev1
export SPRING_DATASOURCE_PASSWORD=dev
./mvnw spring-boot:run
```

Avec Docker Compose, ces variables sont déjà injectées vers le service `db`.

> `ddl-auto=update` : Hibernate crée / met à jour le schéma au démarrage. Pour la production, préférez `validate` + migrations contrôlées.

---

## Accès à l’application

URL de base : **http://localhost:8080**

| Module | Route |
|--------|-------|
| Tableau de bord | `/` ou `/dashboard` |
| Ingrédients | `/ingredients` |
| Stock ingrédients | `/ingredients/stock` ou `/stocks` |
| Plats | `/plats` |
| Recettes | `/recettes/plat/{idPlat}` |
| Fournisseurs | `/fournisseurs` |
| Matériels | `/materielles` |
| Personnels | `/personnels` |
| Clients | `/clients` |
| Zones de livraison | `/zones-livraison` |
| Commandes | `/commandes` |
| Caisse | `/caisse` |

---

## Structure du projet

```
.
├── Dockerfile                 # Build multi-stage Java 21 + JRE
├── docker-compose.yml         # App + PostgreSQL
├── pom.xml                    # Dépendances Maven / Spring Boot
├── mvnw / mvnw.cmd            # Wrapper Maven
├── compile.sh                 # Compile + run
├── database/                  # Scripts SQL utilitaires
│   ├── init.sql               # Création user / DB
│   ├── data.sql               # Schéma de référence
│   ├── views.sql
│   ├── vider.sql / delete.sql
│   └── ...
└── src/main/
    ├── java/com/gestion/restaurant/
    │   ├── RestaurantApplication.java
    │   ├── controller/        # Endpoints MVC (Thymeleaf)
    │   ├── service/           # Logique métier
    │   ├── repository/        # Accès données JPA
    │   ├── entity/            # Entités JPA
    │   ├── dto/               # Objets de transfert
    │   ├── specification/     # Filtres JPA Criteria
    │   └── exception/         # Gestion d’erreurs globale
    └── resources/
        ├── application.properties
        ├── templates/         # Vues Thymeleaf
        └── static/            # CSS, JS, Font Awesome
```

---

## Modules fonctionnels

| Module | Fonctionnalités principales |
|--------|-----------------------------|
| **Dashboard** | Vue d’ensemble |
| **Ingrédients** | CRUD, catégories, statuts, achats / sorties, péremption, historique prix |
| **Stock** | État de stock et inventaire des ingrédients |
| **Plats** | CRUD, filtres par catégorie |
| **Recettes** | Composition d’un plat (ingrédients + quantités) |
| **Fournisseurs** | CRUD fournisseurs / types |
| **Matériels** | CRUD, historique, maintenance, hors service |
| **Personnels** | CRUD, rôles, fiches de paie, absences |
| **Clients** | CRUD clients |
| **Zones de livraison** | CRUD des zones |
| **Commandes** | Commandes multi-lignes, détail, facturation |
| **Caisse** | Mouvements de caisse (entrées / sorties) |
| **Unités** | Conversion d’unités (service dédié) |

---

## Base de données

- SGBD : **PostgreSQL**
- Nom : `restaurant`
- Schéma géré principalement par **Hibernate** (`ddl-auto=update`)

Dossier `database/` :

| Fichier | Usage |
|---------|--------|
| `init.sql` | Création utilisateur / base / droits |
| `data.sql` | Schéma SQL de référence |
| `views.sql` | Vues SQL |
| `vider.sql` / `delete.sql` | Nettoyage des données |
| `reset-sequences.sql` | Réinitialisation des séquences |
| `add-column.sql` / `test.sql` | Scripts utilitaires |

Connexion manuelle au conteneur Postgres :

```bash
docker exec -it mon_postgres_db psql -U dev1 -d restaurant
```

---

## Scripts utiles

| Script | Rôle |
|--------|------|
| `compile.sh` | `mvn clean compile` puis `spring-boot:run` |
| `environnement.sh` | Export du code / config vers `source.txt` |
| `source.sh` | Script d’export complémentaire |
| `conteneur.md` | Notes Codespaces / Docker (historique) |

---

## Dépannage

### Port 8080 déjà utilisé

Changer le mapping dans `docker-compose.yml` :

```yaml
ports:
  - "8081:8080"
```

Ou en local :

```properties
server.port=8081
```

### Erreur de connexion PostgreSQL

1. Vérifier que Postgres tourne (`docker compose ps` ou service local).
2. Contrôler URL / user / mot de passe (`application.properties` ou variables d’environnement).
3. En Docker, l’URL doit pointer vers l’hôte `db` (pas `localhost`) depuis le conteneur app.

### Tables absentes / schéma incohérent

- Au premier démarrage, laisser Hibernate créer le schéma (`ddl-auto=update`).
- En cas de doute : `docker compose down -v` puis `docker compose up --build -d` (efface les données).

### Build Maven échoue

Vérifier Java 21 :

```bash
java -version
./mvnw -v
```

---

## Développement

Conventions du projet :

1. **Controller** : routes HTTP + binding formulaires, délègue au service.
2. **Service** : toute la logique métier.
3. **Repository** : accès données JPA (+ Specifications pour les filtres).
4. **DTO / Mapper** : échanges formulaires ↔ entités.
5. **Templates Thymeleaf** : UI serveur dans `resources/templates`.

Tests :

```bash
./mvnw test
```

---

## Licence / contexte

Projet de gestion interne pour le restaurant **Maman Tsara — Nosy Be**.  
Usage et diffusion selon les règles définies par l’équipe du projet.
