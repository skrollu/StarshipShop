### 14/08:

Refactoring des endpoints sur le modèle des manufacturers

### 09/08:

Found why there was a compilation error because of lombok not working: pom.xml -> The plugin part needed to be change to work with both lombok and mapstruct.

## TODO:

-   Revoir l'utilité de fournir l'id + name des entités DTO, pour le moment seul l'ID est pris en compte.
-   Revoir la réponse de chaque endpoints et celle des delete
-   ~~Créer des méthodes check dans chaque service pour raccourcir et éviter la redondance de create et update~~
-   Ajouter une Salt venant d'une variable d'environnement pour le hashids
