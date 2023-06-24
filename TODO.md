# TODO:
### Cloud

- [ ] rewrite Starship service and others to use oauth2
- [ ] find a way to launch config server in background during ci or had a test application.yml file to every servicesc
- [ ] config server cannot handle circuit breaker config of product-service. It needs a specials move -> see documentation:
https://docs.spring.io/spring-cloud-config/docs/current/reference/html/#config-client-retry
### Feature

-   [ ] update user endpoint
    -   [x] Check number of addresses/emails/telephones per user
    -   [ ] check redondance of the data via set but if id is given its not working
    -   [x] check redondante pseudo
    -   [ ] Transactional update
    -   [x] What if we have the same id but with different value given many times in entry  => error

### General

-   [ ] Refactor domain objects: 
    -  EntityInput si entité d'entrée pour la création = entité d'update
    -  CreateEntityRequest si entité de création si différent d'update
    -  UpdateEntityRequest si entité de création si différent de création

-   [ ] Add a default exception handler
-   [ ] Improve encodage of HATEOAS: ie create user return getUser + email link with encodage error for "@".
-   [ ] Only ask for id for DTO, entity name is useless and can result in bugs... only the id is used today
-   [ ] Change the response of each endpoints in a more complete one {meta, links, response} and the delete response.
-   [ ] Improve validation of data in controller.
-   [ ] Improve Advice response.
-   [ ] Improve HATEOAS response.
-   [x] Change package name on github
-   [x] Add jacoco analyze in sonarcloud like before
-   [x] AccountService register must return SecurityUserDetail and need to be fully tested.
-   [x] Replace Sysout by slf4j
-   [x] see sonar fix recommandation and go up to 50% of code coverage to be able to push code
-   [x] Install SonarLint
-   [x] Had permission system over Authorities.
-   [x] Secure existing endpoints
