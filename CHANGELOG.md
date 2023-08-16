### 15-08-2023

- Change the authorization flow of keycloak with a user id user password user
- Add a client  with client_id client
- totally review with successful test of the security of gateway
- product -> inventory + starship work fine from navigator 
- Gateway can't find service using lb:service-url in config But it works fine using the full url localhost:XXXX
- Keycloak and Gateway configured following this guide: https://www.youtube.com/watch?v=YHWfJHKGYGI&list=PLab_if3UBk99jYV1jfe_7fKQczVX9H4zZ&index=11

### 03-07-2023

- Finally, get a CI that able to run integration tests using keycloak.

### 09-04-2023

- Add basic AudienceValidator and JWTDecoder to see JWT content
- Add Scope based authority for starship endpoints (odc: https://docs.spring.io/spring-security/reference/reactive/oauth2/resource-server/jwt.html) Maps each scope to an authority with the prefix SCOPE_.
- I think I need spring-boot-starter-oauth2-client to have a role based authorisation route.

### 19-03-2023

- Implement zipkin for all services
- improve product and starship service in order to have a complete navigation features in the future web site

### 05-03-2023

- Cloudify starship service (connect to config server and discovery server).
- Make a request with openfeign from product service to inventory and starship service.
- Configure the gateway server filters to access eureka and product starship through it.

### 10-11-2022

-   Manage errors response simply
-   acount username will be changed too username

### 09-11-2022

-   Implement a fully tested register endpoint using red-green-refactor method.

### 07-11-2022

-   Setup a sonarcloud analyze in the github workflow because docker desktop and so sonarqube, doesn't work anymore...

### 04-11-2022

-   Pass every Integration tests in Mysql database to be more accurate with the "prod" environnment and because of some table names error in H2.
-   Create Mappers for user information in AccountMapper.
-   Test them
-   Create a myAccount endpoint and test it

### 10-23-2022

-   Refactored test packages.
-   Implement simple security for test endpoints manged with a JPA entity.

### 10-18-2022

-   Remove unused files and move some packages.

### 10-08-2022

-   Reach good percentage of coverage code on service class.
-   Install and configure Sonarlint

### 10-02-2022:

-   Config jacoco reports for sonarqube
-   Improve code coverage using sonarqube
-   Fix some code smells and bugs (sonarqube)

### 09-11-2022:

-   Improve Jacoco reports and complete the MySQL docker guide in README.md

### 09-10-2022:

-   Document test with report with jacoco and Sonarqube

### 08-29-2022:

-   Add a salt variable to a brand new serializer that uses the id/hash converters. Ids/Hashs now works both with hateoas links and JsonSerialization.

### 08-14-2022:

-   Refactoring des endpoints sur le modèle des manufacturers

### 08-09-2022:

-   Found why there was a compilation error because of lombok not working: pom.xml -> The plugin part needed to be change to work with both lombok and mapstruct.
