# Simple Notebook App

## How to start the notebook application

1. Run `mvn clean install` to build your application
2. Start application with `java -jar target/notebookapp-0.1.0.jar server config.yml`
3. To check that your application is running enter url `http://localhost:8080`

When using AWS (e.g. for queuing, see below for details on configuration) you have to provide your API credentials in a file named `aws.properties`. You may use `aws.properties.example` as a template.  
__Note: Do not rename__ `aws.properties.example` __to__ `aws.properties` __but rather create a new file named__ `aws.properties` __instead.__  
Otherwise, Git will no longer ignore the file, possibly resulting in your AWS credentials being committed and pushed and available to everyone.  

When using a database for storing the notes (see below for details on configuration) you have to provide your database credentials (username and password) in a file named `db.properties`. You may use `db.properties.example` as a template.  
__Note: Do not rename__ `db.properties.example` __to__ `db.properties` __but rather create a new file named__ `db.properties` __instead.__  
Otherwise, Git will no longer ignore the file, possibly resulting in your database credentials being committed and pushed and available to everyone. In addition, the database has to be initialized (create tables) before running the application the first time using the following command:   
```
java -jar target/notebookapp-0.1.0.jar db migrate config.yml  
```

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

The application supports different setups for its components. Currently, the following modes are supported:

### Text Processor

#### local

local text processor, no external dependencies  

```
textProcessor:
  mode: local
```

#### remoteSingle

single remote text processor, requires the endpoint of the text processor resource

```
textProcessor:
  mode: remoteSingle
  textProcessorResource: http://localhost:8082/api
```

#### queue

arbitrary amount of remote text processors load-balanced using queues, requires names of request and response queue

When using ActiveMQ:

```
textProcessor:
  mom: ActiveMQ
  activeMQurl: tcp://localhost:61616
  requestQueueName: TextProcessorRequests
  responseQueueName: TextProcessorResponses
```

When using AWS SQS:

```
textProcessor:
  mom: SQS
  requestQueueName: TextProcessorRequests
  responseQueueName: TextProcessorResponses
```

For AWS SQS, in addition to the `config.yml` file a properties file named `aws.properties` has to be present containing credentials for AWS as follows:
  
```
accessKey = ...
secretKey = ...
```

The `aws.properties` file has to be placed in the same location as the `config.yml` file.

### Notes Database

#### tmp

no persistence, in-memory data storage  

```
notesDB:
  mode: tmp
```

#### jdbc

persistence using jdbc for access to a relational SQL database

```
notesDB:
  mode: jdbc
```

In addition to the `config.yml` file a properties file named `db.properties` has to be present containing username and password for the database as follows:  
```
user = ...
password = ...
```

The `db.properties` file has to be placed in the same location as the `config.yml` file.

Using jdbc mode requires a JDBC database configuration as described in e.g. [http://www.dropwizard.io/1.1.0/docs/manual/jdbi.html](http://www.dropwizard.io/1.1.0/docs/manual/jdbi.html). The `config.yml` file already contains such a configuration for using a [file-based H2 database](http://www.h2database.com/html/features.html#embedded_databases) or a MySQL database (tested with AWS RDS).

In jdbc mode the application supports several commands for creating and managing the database (see also [http://www.dropwizard.io/1.1.0/docs/manual/migrations.html](http://www.dropwizard.io/1.1.0/docs/manual/migrations.html)):

Create or update the database in dry-run mode, i.e. the database is not changed but the SQL commands to be executed are shown on the command line:  
```
java -jar notebookapp-0.1.0.jar db migrate --dry-run config.yml  
```

Create or update the database:  
```
java -jar notebookapp-0.1.0.jar db migrate config.yml  
```

Check database status:  
```
java -jar notebookapp-0.1.0.jar db status config.yml  
```