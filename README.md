# AffiliateLink

**Plateforme de gestion d'affiliation — API REST avec Spring Boot et Spring Modulith**

## 1. Présentation du projet

AffiliateLink est une application backend permettant de gérer une plateforme d'affiliation qui met en relation les annonceurs (*Advertisers*) et les éditeurs (*Publishers*).

La plateforme permet aux annonceurs de créer et gérer des offres publicitaires ainsi que leurs campagnes d'affiliation. Les éditeurs peuvent consulter les offres disponibles et, dans une évolution future, promouvoir ces offres auprès de leur audience.

Le projet est développé avec **Java et Spring Boot**, en utilisant une architecture modulaire basée sur **Spring Modulith** afin de séparer les responsabilités fonctionnelles et faciliter la maintenance et l'évolution de l'application.

## 2. Objectifs fonctionnels

Les principaux objectifs d'AffiliateLink sont :

- Gérer les profils des annonceurs et des éditeurs.
- Gérer les statuts des profils.
- Permettre aux annonceurs de créer et modifier leurs offres.
- Organiser les offres par niche et par tags.
- Associer plusieurs campagnes à une offre.
- Gérer les dates, budgets et modèles de rémunération des campagnes.
- Gérer les statuts des campagnes.
- Rechercher et filtrer les offres et les campagnes.
- Garantir la séparation des responsabilités entre les modules.

## 3. Architecture du projet

Le projet utilise une architecture **modulaire par domaine fonctionnel (Package by Feature)**.

Chaque module possède ses propres couches : Controller, DTO, Entity, Repository et Service.

```text
affiliatelink/
│
├── src/
│   ├── main/
│   │   ├── java/ma/youcode/affiliatelink/
│   │   │
│   │   ├── common/
│   │   │   ├── exception/
│   │   │   ├── handler/
│   │   │   └── ...
│   │   │
│   │   ├── profile/
│   │   │   ├── api/
│   │   │   ├── controller/
│   │   │   ├── dto/
│   │   │   ├── entity/
│   │   │   ├── enums/
│   │   │   ├── event/
│   │   │   ├── mapper/
│   │   │   ├── repository/
│   │   │   └── service/
│   │   │
│   │   ├── offer/
│   │   │   ├── controller/
│   │   │   ├── dto/
│   │   │   ├── entity/
│   │   │   ├── enums/
│   │   │   ├── mapper/
│   │   │   ├── repository/
│   │   │   ├── service/
│   │   │   └── specification/
│   │   │
│   │   └── AffiliateLinkApplication.java
│   │
│   └── resources/
│       ├── db/
│       │   └── migration/
│       └── application.properties
│
├── src/test/
│   └── java/
│       └── ma/youcode/affiliatelink/
│           └── ModularityTest.java
│
├── docker-compose.yml
├── .env
├── .gitignore
├── pom.xml
└── README.md
```

*Remarque : cette structure représente l'organisation fonctionnelle du projet. Les sous-packages peuvent évoluer selon l'implémentation.*

## 4. Modules fonctionnels

### 4.1. Module Profile

Le module `profile` est responsable de la gestion des profils des utilisateurs de la plateforme.

**Fonctionnalités :**

- Création d'un profil Publisher.
- Création d'un profil Advertiser.
- Consultation des éditeurs.
- Consultation d'un éditeur par identifiant.
- Modification du statut d'un profil par l'administrateur.
- Vérification de l'existence et de l'activité d'un annonceur avant la création d'une offre.

**Entités principales :**

- Profile
- Publisher
- Advertiser

**Exemples de statuts :**

- `PENDING`
- `ACTIVE`
- `SUSPENDED`

### 4.2. Module Offer

Le module `offer` est responsable de la gestion des offres publicitaires et des campagnes d'affiliation.

**Fonctionnalités :**

- Création d'une offre pour un annonceur actif.
- Consultation de toutes les offres.
- Recherche et filtrage des offres.
- Consultation d'une offre par identifiant.
- Modification d'une offre.
- Gestion des tags associés aux offres.
- Création de campagnes associées à une offre.
- Consultation des campagnes selon leur statut.
- Modification du statut d'une campagne.

**Entités principales :**

**Offer**
- id
- advertiserId
- title
- description
- landingUrl
- niche
- createdAt

Relations :
- OneToMany avec Campaign.
- ManyToMany avec Tag.

**Campaign**
- id
- name
- startDate
- endDate
- budget
- payoutModel
- payoutValue
- status

Relation :
- ManyToOne avec Offer.

**Tag**
- id
- name (unique)

Relation :
- ManyToMany avec Offer.

**Modèles de rémunération :**

- `CPC` : Cost Per Click.
- `CPL` : Cost Per Lead.
- `CPA_FIXED` : rémunération fixe par action.
- `CPA_PERCENT` : rémunération en pourcentage.

**Exemples de statuts de campagne :**

- `DRAFT`
- `ACTIVE`

Les autres statuts peuvent être ajoutés selon les règles métier du projet.

**Niches disponibles :**

- `BEAUTY`
- `TECH`
- `GAMING`
- `TRAVEL`
- `LIFESTYLE`
- `FOOD`
- `SPORT`

## 5. Spring Modulith

Le projet utilise **Spring Modulith** pour organiser l'application en modules fonctionnels indépendants.

Les modules principaux sont :

- `profile`
- `offer`

Le package `common` contient les éléments partagés, notamment les exceptions et les composants transversaux.

### Communication entre les modules

Le module `offer` ne doit pas accéder directement aux entités ou aux repositories du module `profile`.

Pour respecter cette séparation, le module `profile` expose une interface publique (`ProfileApi`) permettant au module `offer` de vérifier la disponibilité d'un annonceur.

Le module `profile` utilise également des événements applicatifs, par exemple `ProfileSuspended`, pour communiquer certains changements métier.

### Vérification de la modularité

Une classe de test `ModularityTest` permet de vérifier les dépendances entre les modules :

```java
@Test
void verifiesModularStructure() {
    modules.forEach(System.out::println);
    modules.verify();
}
```

Cette vérification permet notamment de détecter les dépendances non autorisées entre les modules.

## 6. API REST — Endpoints

Base URL :

```text
http://localhost:8080
```

### 6.1. Profile API

| Méthode | Endpoint | Description | Réponse |
|---|---|---|---|
| POST | `/api/publishers` | Créer un éditeur | 201 / 400 / 409 |
| GET | `/api/publishers` | Récupérer les éditeurs | 200 |
| GET | `/api/publishers/{id}` | Récupérer un éditeur par ID | 200 / 404 |
| POST | `/api/advertisers` | Créer un annonceur | 201 / 400 / 409 |
| PATCH | `/api/admin/profiles/{id}/status` | Modifier le statut d'un profil | 204 / 404 / 409 |

### 6.2. Offer API

| Méthode | Endpoint | Description | Réponse |
|---|---|---|---|
| POST | `/api/offres` | Créer une offre | 201 / 400 / 404 / 409 |
| GET | `/api/offres` | Récupérer les offres | 200 |
| GET | `/api/offres/{id}` | Récupérer une offre par ID | 200 / 404 |
| PUT | `/api/offres/{id}` | Modifier une offre | 200 / 400 / 404 |
| POST | `/api/offres/{id}/campaigns` | Créer une campagne pour une offre | 201 / 400 / 404 / 409 |
| GET | `/api/campaigns` | Récupérer les campagnes | 200 |
| PATCH | `/api/campaigns/{id}/status` | Modifier le statut d'une campagne | 204 / 404 / 409 |

### 6.3. Recherche et filtrage des offres

L'endpoint de consultation des offres accepte plusieurs paramètres facultatifs :

```http
GET /api/offres?niche=TECH&advertiserId=1&tag=java&keyword=Spring&page=0&size=10&sort=createdAt,desc
```

| Paramètre | Description |
|---|---|
| niche | Filtrer par niche |
| advertiserId | Filtrer par annonceur |
| tag | Filtrer par tag |
| keyword | Rechercher dans le titre et la description |
| page | Numéro de page |
| size | Nombre d'éléments par page |
| sort | Champ et direction du tri |

Exemple de filtrage des campagnes :

```http
GET /api/campaigns?status=ACTIVE
```

### 6.4. Codes HTTP

| Code | Signification |
|---|---|
| 200 | OK — Opération réussie |
| 201 | Created — Ressource créée |
| 204 | No Content — Modification réussie sans contenu de réponse |
| 400 | Bad Request — Données invalides |
| 404 | Not Found — Ressource introuvable |
| 409 | Conflict — Conflit avec une règle métier |

## 7. Technologies utilisées

| Technologie | Utilisation |
|---|---|
| Java 17 | Langage de programmation |
| Spring Boot | Framework backend |
| Spring Web | Création des API REST |
| Spring Data JPA | Accès aux données |
| Hibernate | ORM |
| Spring Modulith | Architecture modulaire |
| PostgreSQL | Base de données relationnelle |
| Flyway | Gestion des migrations SQL |
| Jakarta Validation | Validation des données |
| MapStruct | Mapping entre DTO et entités |
| Lombok | Réduction du code répétitif |
| Maven | Gestion des dépendances et build |
| Docker | Conteneurisation de PostgreSQL |
| Docker Compose | Gestion des services Docker |
| Postman | Tests des API REST |
| Git / GitHub | Gestion de versions |

## 8. Base de données et migrations

Le projet utilise PostgreSQL comme système de gestion de base de données.

Les migrations sont gérées avec Flyway.

Les scripts SQL sont placés dans :

```text
src/main/resources/db/migration/
```

Exemple de nommage :

```text
V1__create_profiles_tables.sql
V2__create_offers_tables.sql
V3__create_campaigns_tables.sql
```

Les migrations permettent de versionner la structure de la base de données et de suivre son évolution.

La configuration Hibernate recommandée en complément de Flyway est :

```properties
spring.jpa.hibernate.ddl-auto=validate
```

Hibernate vérifie alors la compatibilité entre les entités et le schéma existant, sans créer automatiquement les tables.

## 9. Docker — Utilisation

Docker est utilisé pour exécuter PostgreSQL dans un conteneur, sans avoir besoin d'installer et de gérer directement le serveur PostgreSQL sur la machine de développement.

### Pourquoi utiliser Docker ?

- Avoir un environnement de développement reproductible.
- Simplifier l'installation de PostgreSQL.
- Isoler la base de données du système local.
- Conserver les données grâce à un volume Docker.
- Faciliter le démarrage et l'arrêt de l'environnement.

### Configuration

Le projet utilise Docker Compose avec un service PostgreSQL.

Configuration indicative :

```yaml
services:
  postgres:
    image: postgres:18
    container_name: affiliatelink-postgres
    restart: unless-stopped
    env_file:
      - .env
    ports:
      - "5432:5432"
    volumes:
      - affiliatelink_data:/var/lib/postgresql

volumes:
  affiliatelink_data:
```

Le fichier `.env` contient les paramètres de connexion :

```env
POSTGRES_DB=affiliatelink
POSTGRES_USER=postgres
POSTGRES_PASSWORD=your_password
```

Remplacer `your_password` par un mot de passe local et ne jamais publier le fichier `.env` sur GitHub.

### Démarrer PostgreSQL

```bash
docker compose up -d
```

### Vérifier les conteneurs

```bash
docker compose ps
```

### Consulter les logs

```bash
docker compose logs -f postgres
```

### Arrêter les conteneurs

```bash
docker compose down
```

### Redémarrer les conteneurs

```bash
docker compose restart
```

**Attention :** `docker compose down -v` supprime également les volumes associés et peut donc supprimer les données de la base. À utiliser uniquement si tu souhaites réinitialiser complètement l'environnement.

### Connexion à la base de données

| Paramètre | Valeur |
|---|---|
| Host | localhost |
| Port | 5432 |
| Database | affiliatelink |
| Username | postgres |
| Password | Valeur définie dans `.env` |

## 10. Installation et lancement du projet

### Prérequis

- JDK 17
- Maven
- Docker Desktop
- Git
- Postman (pour tester les endpoints)

### Étape 1 — Cloner le projet

```bash
git clone <repository-url>
```

### Étape 2 — Accéder au projet

```bash
cd affiliatelink
```

### Étape 3 — Configurer les variables d'environnement

Créer ou compléter le fichier `.env` avec les informations de connexion PostgreSQL.

### Étape 4 — Démarrer PostgreSQL avec Docker

```bash
docker compose up -d
```

### Étape 5 — Vérifier PostgreSQL

```bash
docker compose ps
```

### Étape 6 — Lancer les migrations et compiler le projet

```bash
mvn clean install
```

### Étape 7 — Lancer l'application

```bash
mvn spring-boot:run
```

L'API sera accessible à l'adresse :

```text
http://localhost:8080
```

## 11. Tests

Les tests permettent de vérifier le bon fonctionnement des modules et des endpoints.

### Vérifier la modularité

```bash
mvn test
```

La classe `ModularityTest` vérifie les règles de dépendances définies avec Spring Modulith.

### Tester les API REST

Les endpoints peuvent être testés avec Postman en utilisant les méthodes HTTP :

- GET
- POST
- PUT
- PATCH

Les requêtes POST et PUT doivent respecter les validations définies dans les DTO.

## 12. Améliorations possibles

Les évolutions possibles du projet comprennent :

- Authentification et autorisation avec Spring Security et JWT.
- Gestion des rôles et permissions.
- Gestion des candidatures des éditeurs aux offres.
- Suivi des clics et des conversions.
- Calcul automatique des commissions.
- Tableau de bord annonceur et éditeur.
- Pagination et filtrage avancés des campagnes.
- Tests unitaires et tests d'intégration supplémentaires.
- Documentation interactive avec Swagger / OpenAPI.

## 13. Auteur

**Abdelhakim Allouani**

Développeur Full Stack — Java / Spring Boot

Projet réalisé dans le cadre du développement d'une plateforme de gestion d'affiliation.

---

*AffiliateLink — Modular Backend Architecture with Spring Boot & Spring Modulith.*