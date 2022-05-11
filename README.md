# Starship shop S3

On souhaite créer un projet e-commerce de vaisseau Star wars en ligne.
En utilisant les technologies react(type script)/SpringBoot.

### Feature 0

Imaginer un modèle relationnel et créer les entités JPA corespondantes.

### Feature 1

-   Créer une api qui permet les actions suivantes:

    GET api/v1/starships
    POST api/v1/starships/{:id}
    PUT api/v1/starships/{:id}
    DELETE api/v1/starships/{:id}

-   Adapter l'API avec l'utilisation de référence plutôt que des noms
    -   Manufacturer
        -   Utilisation du nom avec _underscore_ pour remplacer les espaces
    -   HyperdriveSystem
        -   Utilisation des références plutôt que le nom pour la recherche/modification/
        -   La création nécessite donc uniquement un nom qui
    -   Wepon
        -   Idem HdS
    -   Starship
        -   idem HdS
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
