# Simple Notebook App

## How to start the notebook application

1. Run `mvn clean install` to build your application
2. Start application with `java -jar target/notebookapp-0.1.0.jar server config.yml`
3. To check that your application is running enter url `http://localhost:8080` 

## How to access the notebook application
REST API  
`http://localhost:8080/api`

Angular-based UI  
`http://localhost:8080`

Swagger UI  
`http://localhost:8080/api/swagger`

Monitoring API (Metrics, Health Checks, ...)  
`http://localhost:8081`

## How to configure the notebook application
All configuration is done by editing the `config.yml` file.

The application supports running in different modes. Currently the following modes are supported:

#### Mode A

all-in-one, no persistence, no external dependencies  

```
mode: A
```

#### Mode B

two components, no persistence, external text processor service  

```
mode: B
textProcessorResource: http://localhost:8082/api
```