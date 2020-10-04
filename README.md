# Java Test

### To Start Application

```
./gradlew bootRun -i
```

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
