## TODO:

-   [ ] Only ask for id for DTO entity name is useless and can result in bugs... only the id is used today
-   [ ] Change the response of each endpoints in a more complete one {meta, links, response} and the delete response.
-   [x] Replace Sysout by slf4j
-   [ ] Remove print in tests
-   [ ] see sonar fix recommandation

### 09-11-2020:

-   Improve Jacoco reports and complete the MySQL docker guide in README.md

### 09-10-2020:

-   Document test with report with jacoco and Sonarqube

### 08-29-2022:

-   Add a salt variable to a brand new serializer that uses the id/hash converters. Ids/Hashs now works both with hateoas links and JsonSerialization.

### 08-14-2022:

-   Refactoring des endpoints sur le modèle des manufacturers

### 08-09-2022:

-   Found why there was a compilation error because of lombok not working: pom.xml -> The plugin part needed to be change to work with both lombok and mapstruct.
