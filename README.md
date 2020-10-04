# MatchesFashion Java Test

Here at MatchesFashion we love API development. 
We would like for you to demonstrate your API & Java strength by completing this exercise.

* try to not spend more than 4 hours.

### What you have been provided
An incomplete java web application built with 
* Gradle
* Spring Boot (mvc & data jpa)
* Java 8
* H2 DB Embedded

Classes
* Product - JPA Entity mapping to 'products' table
* ProductRepository - Spring JPA Repository class

Sample Data
* src/main/resources/data.sql
* loaded in automatically every time the application starts

### The Challenge!

* Develop an URL endpoint which returns all products

 Hint! you can use the ProductRepository which has inherited useful methods ;)  inject it into your class like so
```$xslt
	@Autowired
	private ProductRepository productRepository;

```


* Develop an endpoint which returns all products which cost more than 100


### System Requirements

* JDK 8


### To Start Application

```
./gradlew bootRun -i
```
* this will start a server on http://localhost:8080
* you can change the port under application.properties

### Sending us your solution

* Link to your PRIVATE git repo 
* Or a link to your zipped up code

### How to call the APIs

The response type of the products API is a paginated list.  Default page size is 25.  Default sort is by title.

Return all the products:
curl 'localhost:8080/products' 

All query params are optional
Example of all the query params in use:
curl 'localhost:8080/products?pageSize=2&page=0&priceGreaterThan=5&sort=title' 

If you request sorting by a field that does not exist then you will get a 400 response
e.g. 
curl -v 'localhost:8080/products?sort=foo'


### Docker
To build the docker image locally, run

`./build-docker.sh`

To run the docker image: 

`docker container run --publish 8080:8080 papi:latest`

### Google Cloud Build/Cloud run pipeline
There is a pipeline set up to auto build/deploy this code
 
`curl https://matches-product-api-a6tmrbnexa-ew.a.run.app/products`
