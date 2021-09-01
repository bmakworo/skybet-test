# Sky Bet Test ReadMe

# Tools and frameworks used
Maven

I chose maven over gradle because I have more experience using the maven build tool than I do gradle

Spring boot

I chose to use spring boot as it provides an easy way to write and implement RestFul APIs. It also provides additional tools such as
dependency injection(that is widely used in the project).

Docker

# How to run the app
1. Clone the spring boot application to a folder of choice
2. Navigate to the skybet-test folder
3. Run the command ```mvn clean package``` to build the jar file and run all the tests
4. Build the docker images by running the following command: 
```docker build -t springio/gs-spring-boot-docker .```
5. Run the application by running the following command: 
```docker run -p 8080:8080 springio/gs-spring-boot-docker```
   
# Curl Requests
##  POST - Create user

curl --location --request POST 'http://localhost:8080/user' \
--header 'Content-Type: application/json' \
--data-raw '{
"id": "34",
"firstName": "betty",
"lastName": "makworo",
"emailAddress": "betty@email.com",
"dateOfBirth": "2012-04-23"
}'

GET - Get all Users

curl --location --request GET 'http://localhost:8080/users'

GET - Get user by Id

curl --location --request GET 'http://localhost:8080/user/18'

PUT - Update user by Id

curl --location --request PUT 'http://localhost:8080/user/18' \
--header 'Content-Type: application/json' \
--data-raw '{
"id": "25",
"firstName": "katherine",
"lastName": "kerubo",
"emailAddress": "katetherine@email.com",
"dateOfBirth": "1981-04-10"
}'

DELETE - Delete user by Id

curl --location --request DELETE 'http://localhost:8080/user/100'