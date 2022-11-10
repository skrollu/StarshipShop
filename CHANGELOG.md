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
