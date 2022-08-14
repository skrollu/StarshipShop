# Starship shop S3

On souhaite créer un projet e-commerce de vaisseau Star wars en ligne.
En utilisant les technologies react(type script)/SpringBoot.

### Feature 0

Imaginer un modèle relationnel et créer les entités JPA corespondantes.

### Feature 1

-   Créer une api qui permet les actions suivantes:

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
        name: string
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
        name: string,
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
        name: string,
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

-   Tester les endpoints.

### Feature 2

-   Inclure un système d'open API et d'hyper media driven
-   Tester

### Feature 3

-   Sécurisé l'api avec JWT.
-   Tester

### Feature 4

-   Trouver une solution pour intégrer des images pour les starships
-   améliorer les modèle (engines, name, short name, etc...)

### Feature 5

-   Créer une interface simple afin de d'utiliser les différents endpoints

### Feature 6

-   Protéger les endpoints en intégrant Spring Security
-   Utiliser la nouvelle api protégée sur l'interface
