# Gestion Restaurant — Maman Tsara (Nosy Be)

Application web de gestion pour le restaurant **Maman Tsara** (Nosy Be) : stocks, recettes, commandes, caisse, personnels, matériels et livraisons.

---

## Sommaire

- [Stack technique](#stack-technique)
- [Prérequis](#prérequis)
- [Démarrage rapide (Docker)](#démarrage-rapide-docker)
- [MCP Cursor (base de données)](#mcp-cursor-base-de-données)
- [Authentification](#authentification)
- [Installation locale](#installation-locale)
- [Configuration](#configuration)
- [Schéma base (ORM Hibernate)](#schéma-base-orm-hibernate)
- [Accès à l’application](#accès-à-lapplication)
- [Structure du projet](#structure-du-projet)
- [Modules fonctionnels](#modules-fonctionnels)
- [Base de données](#base-de-données)
- [Déploiement Render + Neon](#déploiement-render--neon)
- [Tests](#tests)
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
| Auth | Spring Security (1 compte admin en mémoire via env) |
| Schéma DB | Hibernate ORM (`ddl-auto=update`) — entités = source de vérité |

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
cp .env.example .env
# Éditer .env : APP_ADMIN_PASSWORD, POSTGRES_PASSWORD, etc.

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

Admin BDD (Adminer, équivalent phpMyAdmin pour PostgreSQL) : [http://localhost:8081](http://localhost:8081)

- Système : **PostgreSQL**
- Serveur : `db`
- Utilisateur / mot de passe : ceux de `.env` (`POSTGRES_USER` / `POSTGRES_PASSWORD`)
- Base : `restaurant`

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
| Application Spring Boot | `restaurant-app` | `8080` |
| Adminer (UI BDD) | `restaurant-adminer` | `8081` |
| PostgreSQL 16 | `restaurant-db` | `5432` |

Identifiants DB (Docker, valeurs par défaut) :

- Base : `restaurant`
- Utilisateur : `dev1` (ou `POSTGRES_USER`)
- Mot de passe : `dev` (ou `POSTGRES_PASSWORD`)

---

## MCP Cursor (base de données)

Le projet utilise **[DBHub](https://dbhub.ai)** (`@bytebase/dbhub`) pour connecter l’agent Cursor à PostgreSQL (schéma + SQL).

**Prérequis :** Node.js ≥ 22.5 et Postgres local (`docker compose up -d db` → port `5432`).

1. Copier la config si besoin :

```bash
cp .cursor/mcp.json.example .cursor/mcp.json
```

2. Adapter le DSN dans [`.cursor/mcp.json`](.cursor/mcp.json) avec les mêmes identifiants que `.env` :

```text
postgres://USER:PASSWORD@127.0.0.1:5432/restaurant?sslmode=disable
```

3. Redémarrer Cursor, ou : **Settings → Tools & MCP** → activer / reconnecter `restaurant-postgres`

Le fichier `.cursor/mcp.json` est ignoré par Git (secrets). Le modèle est [`.cursor/mcp.json.example`](.cursor/mcp.json.example).

---

## Authentification

Toutes les pages métier sont protégées. Sans session → redirection vers `/login`.

| Variable | Défaut (dev) | Description |
|----------|--------------|-------------|
| `APP_ADMIN_USERNAME` | `admin` | Identifiant unique |
| `APP_ADMIN_PASSWORD` | `changeme` | Mot de passe (en mémoire, **pas** en base) |

- Un seul compte `ROLE_ADMIN` (pas de table utilisateurs).
- En production (Render) : définir un mot de passe fort via le dashboard (voir [.env.prod.example](.env.prod.example)).
- Déconnexion : bouton dans la sidebar (`POST /logout`).

Public uniquement : `/login`, assets statiques (`/css`, `/js`, `/bootstrap`, `/font-awesome`, …), `/error/**`.

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

Configs séparées **local** / **prod** :

| Environnement | Profil Spring | Base | Fichiers env |
|---------------|---------------|------|--------------|
| Local | `dev` | Postgres Docker (Compose) | [`.env.example`](.env.example) → `.env` |
| Prod | `prod` | **Neon** | [`.env.prod.example`](.env.prod.example) → secrets Render |

| Variable | Local (exemple) | Prod |
|----------|-----------------|------|
| `SPRING_PROFILES_ACTIVE` | `dev` | `prod` |
| `DATABASE_URL` | `jdbc:postgresql://...?user=&password=` | URI Neon `postgresql://...?sslmode=require` |
| `PORT` | `8080` | Injecté par Render |
| `APP_ADMIN_USERNAME` / `PASSWORD` | `.env` | Dashboard Render |
| `HIKARI_MAX_POOL_SIZE` | défaut | `5` (Neon Free) |

`DatabaseUrlConfig` convertit automatiquement les URL Neon (`postgres://` / `postgresql://`) en JDBC + SSL. Les URL `jdbc:` (Compose) restent inchangées.

Exemple d’override en local :

```bash
# Windows PowerShell
$env:DATABASE_URL="jdbc:postgresql://localhost:5432/restaurant?user=dev1&password=dev"
$env:APP_ADMIN_USERNAME="admin"
$env:APP_ADMIN_PASSWORD="secret"
.\mvnw.cmd spring-boot:run
```

---

## Schéma base (ORM Hibernate)

Les **entités JPA** (`src/main/java/.../entity/`) sont la source de vérité du schéma.

| Environnement | Mode |
|---------------|------|
| Dev (`application-dev.properties`) | `ddl-auto=update` |
| Prod (`application-prod.properties`) | `ddl-auto=update` (surchargeable via `SPRING_JPA_HIBERNATE_DDL_AUTO`) |

**Procédure serveur (base vide)**

1. Configurer Postgres + variables DB + `APP_ADMIN_*`
2. Démarrer l’app → Hibernate crée les tables depuis les `@Entity`
3. Se connecter sur `/login`

**Évolution du modèle** : modifier / ajouter une entité, redéployer ; Hibernate ajoute tables / colonnes manquantes.

**Limites** : `update` n’effectue en général pas les renommages / suppressions de colonnes. Le dossier `database/` reste une référence SQL documentaire, pas le moteur de migration.

---

## Accès à l’application

URL de base : **http://localhost:8080** (connexion requise)

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
- Schéma créé / mis à jour par **Hibernate** à partir des entités (`entity/`)

Modules couverts par les `@Entity` : caisse, clients, commandes, fournisseurs, ingrédients/stock, livraisons, matériels, personnels (paie/absences), plats/recettes.

> Les scripts `database/*.sql` (ex. `HistoriqueSalaire`, `BonusGlobaleSalairePersonnels`) peuvent contenir des tables historiques non utilisées par le code actuel — l’ORM ne les crée que s’il existe une entité correspondante.

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

## Déploiement Render + Neon

Architecture prod :

| Couche | Service |
|--------|---------|
| Application | **Render** Web Service (Docker, [`Dockerfile`](Dockerfile), [`render.yaml`](render.yaml)) |
| Base de données | **Neon** Postgres (externe, SSL) |

Le Compose local (`db` + Adminer) **n’est pas** utilisé en production.

### 1. Créer la base Neon

1. Compte sur [console.neon.tech](https://console.neon.tech) → **New Project** (région EU si possible)
2. Copier la **connection string** (avec `sslmode=require`)  
   Ex. : `postgresql://user:pass@ep-xxxx.eu-central-1.aws.neon.tech/neondb?sslmode=require`
3. Ne pas committer cette URL (voir [`.env.prod.example`](.env.prod.example))

### 2. Pousser le code sur GitHub

Branche déployée : `main` (ou celle configurée sur Render).

### 3. Déployer sur Render

**Via Blueprint (recommandé)**

1. [dashboard.render.com](https://dashboard.render.com) → connecter le repo GitHub  
2. **New → Blueprint** → sélectionner le dépôt (lit [`render.yaml`](render.yaml))  
3. Saisir quand demandé :
   - `DATABASE_URL` = URI Neon  
   - `APP_ADMIN_PASSWORD` = mot de passe fort  
4. Apply → build Docker (~5–10 min)

**Manuel**

1. **New → Web Service** → runtime **Docker** → repo  
2. Health check path : `/login`  
3. Variables d’environnement (même liste que [`.env.prod.example`](.env.prod.example)) :
   - `SPRING_PROFILES_ACTIVE=prod`
   - `DATABASE_URL=<uri Neon>`
   - `APP_ADMIN_USERNAME=admin`
   - `APP_ADMIN_PASSWORD=<fort>`
   - `JAVA_OPTS=-Xms256m -Xmx512m`
   - `HIKARI_MAX_POOL_SIZE=5`
   - `SPRING_JPA_HIBERNATE_DDL_AUTO=update`

### 4. Vérifier

1. Logs Render : démarrage Spring, connexion Neon OK, pas d’erreur SSL  
2. Ouvrir `https://<service>.onrender.com/login`  
3. Se connecter avec `APP_ADMIN_*`  
4. Hibernate `ddl-auto=update` crée les tables au premier boot  
5. Plan Free Render : cold start après inactivité (~30–60 s)

### 5. Local vs prod — rappel

```bash
# Local uniquement
cp .env.example .env
docker compose up -d --build
# http://localhost:8080 — Adminer http://localhost:8081
```

Ne pas mettre l’URI Neon dans `.env` local au quotidien.

---

## Tests

Pyramide en place sous `src/test/java` :

| Couche | Emplacement | Contenu |
|--------|-------------|---------|
| Unitaires | `.../service/**` | Mockito, tous les services métier |
| Intégration | `.../integration/**` (`*IT`) | Postgres réel (Testcontainers ou fallback) |
| Fonctionnel | `.../functional/**` | MockMvc + Security + CSRF, parcours HTTP |

**Prérequis**

- Java **21**, Maven (ou image Docker Maven)
- Docker recommandé (Testcontainers Postgres 16)
- Sinon : base `restaurant_test` sur Postgres local + variable `TEST_DATABASE_URL`

Créer la base de fallback (Compose) :

```bash
docker exec restaurant-db psql -U dev1 -d postgres -c "CREATE DATABASE restaurant_test OWNER dev1;"
```

**Lancer les tests**

```bash
# Machine avec Java 21 + Docker (Testcontainers)
./mvnw test

# Via Docker Maven + Postgres Compose (fallback)
docker run --rm --add-host=host.docker.internal:host-gateway \
  -v "${PWD}:/app" -w /app \
  -e "TEST_DATABASE_URL=jdbc:postgresql://host.docker.internal:5432/restaurant_test?user=dev1&password=dev" \
  maven:3.9.9-eclipse-temurin-21 mvn test
```

Compte admin de test : `admin` / `test` (voir `application-test.properties`).

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

---

## Licence / contexte

Projet de gestion interne pour le restaurant **Maman Tsara — Nosy Be**.  
Usage et diffusion selon les règles définies par l’équipe du projet.
