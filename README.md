![workflow](https://github.com/skrollu/StarshipShop/actions/workflows/maven.yml/badge.svg)

# Starship shop S3

# Run application

    DEV mode:

    mvn clean package -DskipTests -Pdev
    mvn spring-boot:run -Pdev

    DEFAULT mode:

    mvn clean package -DskipTests
    mvn spring-boot:run

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
