# Minesweeper-API

Basic API Rest for minesweeper game.

#### Technology stack

- Java 8 + Springboot + Maven
- MongoDB

#### Requirements

- Maven
- JDK 1.8
- MongoDB

## API Documentation

https://nicovalerga-minesweeper-api.herokuapp.com/minesweeper-api/documentation.html

## Play a Demo

https://nicovalerga-minesweeper-api.herokuapp.com/minesweeper-api/demo/

## Notes

I enjoyed developing minesweeper api, it was a big challenge and was very fun too!
This are some of technical decisions that I have taken:

- Save the data in two collections: Users and Boards. The Tiles information saves into each board. This decision allowed my to use two entities (users, boards) insteaf of three (users, boards, tiles), also for getting or updating the board I used one query because the tiles come in the response. For that reason I choosed mongo DB instead a relational database. 

- Boards can be created with/without an user, the user is optional. This functionality will allow future anonym users to play without sign-in. Also allows to develop new functionalities for registered users to save their boards for longer time than the anonyms users.

- At first I developed a basic html with a table to visualize the tiles position, it helps me a lot. When I finished the api, it occurred me that this basic html could transform it in a demo game. For that reason I read about thymeleaft in SpringBoot applications and I put the basic frontend in my minesweeper application. It calls the api using jquery ajax. Its very rustic but it works!


## Run the application local

First: start a mongo database local
Second: set environment variable ${MONGODB_URI} or replace it in application.yml for: "mongodb://localhost:27017/minesweeper"

```
$ cd minesweeper-api
$ mvn spring-boot:run
```
SpringBoot application up in port 5000, check health endpoint:

    GET http://localhost:5000/minesweeper-api/actuator/health

## Run the tests

```
$ cd minesweeper-api
$ mvn test
```

## ToDo

List of possible improvements:

- [ ] Ability to 'flag' a cell with a question mark or red flag
- [ ] Return DTOs instead of Entities
- [ ] User authentication login/logout
- [ ] Write unit tests for all classes
- [ ] Building a deployment process


## Documentation

- [Basic minesweeper algorithms](https://dzone.com/articles/minesweeper-algorithms-explained)
- [Error Handling](https://www.baeldung.com/spring-rest-template-error-handling)
- [Thymeleaf](https://www.baeldung.com/spring-boot-crud-thymeleaf)
- [Open API documentation](https://www.baeldung.com/spring-rest-openapi-documentation)
- [MongoDB for testing](https://www.baeldung.com/spring-boot-embedded-mongodb)