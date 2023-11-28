# Contact Book project
## Technologies
* JDK 17
* Spring Boot 3.2
* Spring Security & JWT
* Maven


## Getting Started
To get started with this project, you will need to have the following installed on your local machine:
* JDK 17
* Postgres
* Postman


in application properties change the following properties to your database credentials
* spring.datasource.url
* spring.datasource.username
* spring.datasource.password


## Running the application
* register and generate jwt token http://localhost:8080/api/v1/auth/register
* with token you can access the api endpoint: http://localhost:8080/api/v1/contact-info
* now you can get,add, update, delete and authenticated users contact book