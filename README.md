# messenger-backend
## Onboarding training: Basic spring boot testings
###### It contains the implementation of Restful services following Richardson Maturity Level description
###### The persistent layer was built initially using MySQL and evolved to MongoDB
###### In Maturity level 3 there is a service layer interfacing with dao impl
###### There are some unit tests using Spring Tests and mockito
###### There are also some integration tests using BDD (Jbehave)
 

1. Install docker and docker-compose modules:
   * https://docs.docker.com/install/
   * https://docs.docker.com/compose/install/
   
2. Clone messenger-docker-compose repository from github:
   ```
   $> git clone https://github.com/tprimini/messenger-docker-compose.git
   $> docker-compose up -d
   ```
   
3. Clone messenger-backend module
    ```
   $> git clone https://github.com/tprimini/messenger-backend.git
   ```
   
4. Compile messenger-backend with Maven
    ```
   $> mvn clean install
   ```
   
5. Run messenger-backend module
    ```
   $> java -jar target/messenger-backend-1.0-SNAPSHOT.jar
   ```
   