![workflow](https://github.com/skrollu/StarshipShop/actions/workflows/maven.yml/badge.svg)

# Starship shop S3

This project describe a starships management api. Originaly created to improve my skills different features will appear by time. See the to learn more about it: [description](#description).

### Used tehcnologies:

-   Springboot
    -   Hibernate
    -   Lombok
    -   Mapstruct
    -   Hashids
    -   Junit
    -   Jacoco
-   Sonarqube (See how to configure Sonarqube [here](#run-tests-and-report-on-sonarqube))
-   Mysql (See how to configure MySQL [here](#run-application-with-a-mysql-server-on-a-docker-container).)

# Run application

This project is using a MySQL database which need to be install. Personnaly I use WAMP.
But below I made a simple config to [run a MySQL server on a docker container](#run-application-with-a-mysql-server-on-a-docker-container).

> DEV mode:

```sh
mvn clean package -DskipTests -Pdev
mvn spring-boot:run -Pdev
```

> DEFAULT mode:

```sh
mvn clean package -DskipTests
mvn spring-boot:run
```

## Run applicationwith a MySQL server on a docker container

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

By default, SonarQube forces user authentication. You can disable forced user authentication, and allow anonymous users to browse projects and run analyses in your instance. To do this, log in as a system administrator, go to **Administration** > **Configuration** > **General Settings** > **Security**, and disable the Force user authentication property.

The **sonar-maven-plugin** execute the SonarQube analysis via a regular Maven. So here we execute the sonar:sonar goal in the verify maven phase to post the report on sonarqube default url (http://localhost:9000).

```sh
mvn clean verify
```

# Description

We want to create a Star Wars starships e-commerce project.
Using react(type script)/SpringBoot technologies

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
        *manufacturer: ManufacturerDTO
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
        *manufacturer: ManufacturerDTO
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
        *manufacturer: ManufacturerDTO,
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
