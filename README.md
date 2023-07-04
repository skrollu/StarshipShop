![workflow](https://github.com/skrollu/StarshipShop/actions/workflows/maven.yml/badge.svg)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=skrollu_StarshipShop&metric=coverage)](https://sonarcloud.io/summary/new_code?id=skrollu_StarshipShop)
[![SonarCloud](https://sonarcloud.io/images/project_badges/sonarcloud-white.svg)](https://sonarcloud.io/summary/new_code?id=skrollu_StarshipShop)

# Starship shop S3

This is a fullstack multimodule project and is composed by api microservices that part of an ecommerce api.
Modules suffixed with "server" are responsable for specific cloud feature (configuration, gateway, discovery...). Others 
are responsable of their own API (see [schema](./starshipShop.drawio))
We want to create a Star Wars starships e-commerce project.
Using react(type script)/SpringBoot and lots of technologies.

# Summary

- Description
- Getting Started
- Used Technologies
- CI
- Run tests on a local Sonarqube.

# Getting Started (dev)

To start the project you'll need to execute the following instructions step by step.

## MySQL

This project is using a MySQL database which need to be installed. I use WAMP.
But below I made a simple config to [run a MySQL server on a docker container](#run-application-with-a-mysql-server-on-a-docker-container).

```docker
docker volume create mysql-volume
docker run -it --rm --name mysql -p3306:3306 -v mysql-volume:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=starship_shop mysql:8
```

> Wait for this kind of log (it can take some minutes... so using a volume recommanded for next connection): _2022-09-11T10:42:07.458801Z 0 [System] [MY-011323] [Server] X Plugin ready for connections. Bind-address: '::' port: 33060, socket: /var/run/mysqld/mysqlx.sock_ Which means MySQL Server is now fully available and you can connect throught the container.

```docker
docker exec -it mysql bash
mysql -u root -p
```

> Enter password: root
> Now to permit our project to connect externally to the docker container or if you want to connect to this database with Database manager you need to change the host value to '%' for the root user or any user you want to create.

```sql
ALTER USER 'root'@'%' IDENTIFIED WITH mysql_native_password BY 'root';
FLUSH PRIVILEGES;
```

> Our container is now available for external connections and the project is now runnable too. The database is created with running container command but if you want to test the project you'll need a starship_shop_test db as bellow:

```sql
CREATE DATABASE starship_shop_test;
```

## PostgreSQL

To use Keycloak, postgreSQL is also needed.

```sh
docker run --name postgresql -e POSTGRES_USER=root -e POSTGRES_PASSWORD=root -e POSTGRES_DB=bitnami_keycloak -p 5432:5432 -d postgres:latest
```

## Keycloak

Run Keycloak for authentication and authorization.

```sh
docker run -d --name keycloak -e KEYCLOAK_ADMIN_USER=admin -e KEYCLOAK_ADMIN_PASSWORD=admin -e KEYCLOAK_DATABASE_USER=root -e KEYCLOAK_DATABASE_PASSWORD=root -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin -e KC_HEALTH_ENABLED=true -p 8181:8080 --link postgresql -v ./realms:/opt/keycloak/data/import quay.io/keycloak/keycloak:latest start-dev --import-realm
```

## Zipkin

Run Zipkin for a better API monitoring.

```sh
docker run  --name zipkin -d -p 9411:9411 openzipkin/zipkin:latest
```

## Server modules

Then run the *server modules* using the following commands:

```sh
cd config-server
mvn spring-boot:run -Pdev
cd ..
cd discovery-server
mvn spring-boot:run -Pdev 
cd ..
cd gateway-server
mvn spring-boot:run -Pdev
cd ..
```

## Service modules

Finally run the *service module* you want to use:

```sh
cd XXX-service
mvn spring-boot:run -Pdev
cd ..
```

# Used technologies:

-  Spring boot
-  Spring Cloud
    -   Hibernate
    -   Lombok
    -   Mapstruct
    -   Hashids
    -   Junit
    -   Jacoco
-   Sonarqube (See how to configure Sonarqube [here](#run-tests-and-report-on-sonarqube))
-   Mysql (See how to configure MySQL [here](#run-application-with-a-mysql-server-on-a-docker-container).)
-   Keycloak
-   Zipkin
-   Docker

# Export Keycloak realm

```sh
export seams not to be allowed in docker keycloak container but may works with jboss image. Works correctly with server version.
kc.bat export --file starshipshop-realm-export.json
```


# Run tests and report on Sonarqube

Use the official docker sonarqube image to launch a server container on the default 9000 port.
Create docker volumes the first time you use Sonarqube but nextime it will already exist on your computer. So don't create them twice because it will override them.

```docker
docker volume create sonarqube_data
docker volume create sonarqube_extensions
docker volume create sonarqube_logs
docker run --rm --name sonarqube -p 9000:9000 -v sonarqube_data:/opt/sonarqube/data -v sonarqube_extensions:/opt/sonarqube/extensions -v sonarqube_logs:/opt/sonarqube/logs sonarqube
```

### Authentication

Go to http://localhost:9000 and login with default id: admin and password: admin.
First connection will ask you to change your password.

If you are using SonarLint plugin go to your **Profile** > **Security** > **Generate Tokens**
chose a name for it:

> Name: global

> Type: Global Analysis Token

> Expiration: No expiration

Copy this token the sonarlint connection form: _see sonar lint documentation_ of your IDE.

By default, SonarQube forces user authentication. You can disable forced user authentication, and allow anonymous users to browse projects and run analyses in your instance. To do this, log in as a system administrator, go to **Administration** > **Configuration** > **General Settings** > **Security**, and disable the Force user authentication property.

The **sonar-maven-plugin** execute the SonarQube analysis via a regular Maven. So here we execute the sonar:sonar goal in the verify maven phase to post the report on sonarqube default url (http://localhost:9000).

```sh
mvn clean verify
```

### Feature 0

Imagine a relationnal model and create minimal JPA entities.

### Feature 1

-   Create a simple CRUD API.

#### Manufacturer api/v1/manufacturers

| Méthodes | Entrée                        | Sortie                 |
| -------- | ----------------------------- | ---------------------- |
| GET all  |                               | Array[ManufacturerDTO] |
| GET      | id                            | ManufacturerDTO        |
| POST     | ManufacturerRequestInput      | ManufacturerDTO        |
| PUT      | id + ManufacturerRequestInput | ManufacturerDTO        |
| DEL      | id                            | DeleteRequestOutput    |

    ManufacturerRequestInput:
    {
        *name: string
    }

    ManufacturerDTO: {
        hash: string, (hashed)
        name: string
    }

    DeleteRequestOutput: {
        deleted: boolean
    }

-   Link
-   Meta
    -   responseTime
    -   lang
    -   apiVersion

#### HyperdriveSystem api/v1/hyperdriveSystems

| Méthodes | Entrée                            | Sortie                     |
| -------- | --------------------------------- | -------------------------- |
| GET all  |                                   | Array[HyperdriveSystemDTO] |
| GET      | id                                | HyperdriveSystemDTO        |
| POST     | HyperdriveSystemRequestInput      | HyperdriveSystemDTO        |
| PUT      | id + HyperdriveSystemRequestInput | HyperdriveSystemDTO        |
| DEL      | id                                | DeleteRequestOutput        |

    HyperdriveSystemRequestInput:
    {
        *name: string,
        manufacturer: ManufacturerDTO
    }

    HyperdriveSystemDTO: {
        id: string, (hashed)
        name: string,
        manufacturer: ManufacturerDTO
    }

#### Weapon api/v1/weapons

| Méthodes | Entrée                  | Sortie              |
| -------- | ----------------------- | ------------------- |
| GET all  |                         | Array[WeaponDTO]    |
| GET      | id                      | WeaponDTO           |
| POST     | WeaponRequestInput      | WeaponDTO           |
| PUT      | id + WeaponRequestInput | WeaponDTO           |
| DEL      | id                      | DeleteRequestOutput |

    WeaponRequestInput:
    {
        *name: string,
        manufacturer: ManufacturerDTO
    }

    WeaponDTO: {
        id: string, (hashed)
        name: string,
        manufacturer: ManufacturerDTO
    }

#### Starship api/v1/starships

| Méthodes | Entrée                    | Sortie              |
| -------- | ------------------------- | ------------------- |
| GET all  |                           | Array[StarshipDTO]  |
| GET      | id                        | StarshipDTO         |
| POST     | StarshipRequestInput      | StarshipDTO         |
| PUT      | id + StarshipRequestInput | StarshipDTO         |
| DEL      | id                        | DeleteRequestOutput |

    StarshipRequestInput:
    {
        *name: string,
        engines:  string,
        height: number,
        width: number,
        lenght: number,
        weight: number,
        description; string,
        manufacturer: ManufacturerDTO,
        hyperdriveSystem: HyperdriveSystemDTO,
        weapon: [WeaponDTO]
    }

    StarshipDTO: {
        id: string, (hashed)
        name: string,
        engines:  string,
        height: number,
        width: number,
        lenght: number,
        weight: number,
        description; string,
        manufacturer: ManufacturerDTO,
        hyperdriveSystem: HyperdriveSystemDTO,
        weapon: [WeaponDTO]
    }

-   Test endpoints.

### Feature 2

-   Include Open API and Hyper media driven fetatures
-   Test

### Feature 3

-   Secure the API
-   Test

### Feature 4

-   Add pictures for Starships
-   Improve the model (engines, name, short name, etc...)

### Feature 5

-   Create a Front end
