# Simple Notebook Application

A simple application that demonstrates various technologies that are relevant in the context of cloud computing.

The application provides a simple web UI that allows to create, retrieve, update, and delete simple text notes. Whenever a note is retrieved from the application, its text is first processed by a so-called text processor component. In the current implementation, the text processor provides no meaningful behavior, it simply appends some text to the original note text.

Technically, the web UI is based on [Angular](https://angular.io/) interacting with a REST API. The notebook application itself (which provides the REST API) is based on [Dropwizard](http://www.dropwizard.io/). The notebook application comprises various implementations for the notes storage as well as for the text processor component. Details about these variants and how the notebook application can be configured to use them can be found below. 

## How to build and run the notebook application
1. You need to have Java 8, Git, and Maven installed on your system
2. Checkout the source code from GitHub 
3. Run `mvn clean install` to build your application
4. Start application with `java -jar target/notebookapp-0.2.0.jar server config-min.yml`
(on some platforms, `sudo` is required, i.e. you have to run `sudo java -jar ...` instead)
5. To check that your application is running enter url `http://localhost`

The default configuration has no external dependencies. It uses the internal text processor as well as the non-persistent in-memory storage. 

## How to access the notebook application
Angular-based UI  
`http://localhost`

REST API  
`http://localhost/api`

Swagger UI  
`http://localhost/api/swagger`

Monitoring API (Metrics, Health Checks, ...)  
`http://localhost:8081`

## General configuration
All configuration is done by editing the `config.yml` file. The `config-min.yml` file provides a minimal configuration whereas the `config.yml` file contains all possible configuration options. When the notebook application is started, the configuration file to be used is passed as a parameter. 

Sometimes, configuration parameters are only known at runtime, meaning that it is not feasible to write them to the configuration file. For such cases, it is possible to access environment variables from the configuration file. The expression `${PORT!8080}` for example refers to the value of the environment variable named PORT. If this variable is not set, the value 8080 will be used instead.

## Text processor configuration

The notebook application supports three different implementations / integrations for the text processor component.

1. `local` - local text processor, no external dependencies
2. `remoteSingle` - single remote text processor with a REST API
3. `queue` - arbitrary amount of remote text processors, load-balanced using message queues

The internal interface for a text processor is defined by `de.ustutt.iaas.cc.core.ITextProcessor`. During startup, the configuration file is read and a corresponding implementation of this interface is instantiated.   

#### "local"

local text processor, no external dependencies  

```
textProcessor:
  mode: local
```

Implemented by `de.ustutt.iaas.cc.core.LocalTextProcessor`.

#### "remoteSingle"

single remote text processor, requires the endpoint of the text processor resource

```
textProcessor:
  mode: remoteSingle
  textProcessorResource: http://localhost:8082/api
```

An example implementation of such a remote text processor can be found [here](https://github.com/F7502/textprocessor-service). It is also possible to implement such a service for example as a Google Cloud Function. Technically, the text processor service has to provide a HTTP endpoint that accepts POST requests with `text/plain` payload and that also returns some `text/plain` payload. 

Implemented by `de.ustutt.iaas.cc.core.RemoteTextProcessor`.

#### "queue"

arbitrary amount of remote text processors, load-balanced using queues, requires names of request and response queue

This text processor implementation / integration is based on JMS and currently supports two different messaging systems that both require a different configuration. An example implementation of a text processor worker that processes text messages from the request queue and then puts the result to the response queue can be found [here](https://github.com/F7502/textprocessor-worker). 

The first supported messaging system is [Apache ActiveMQ](http://activemq.apache.org/). A short documentation of how to install and run ActiveMQ can be found [here](https://github.com/F7502/lcm-jms-helloworld) (this project also comprises some example code). ActiveMQ requires the following configuration: 

```
textProcessor:
  mom: ActiveMQ
  activeMQurl: tcp://localhost:61616
  requestQueueName: TextProcessorRequests
  responseQueueName: TextProcessorResponses
```

The second supported messaging system is [AWS SQS](https://aws.amazon.com/sqs) ("Amazon Web Services Simple Queue Service"). AWS SQS requires the following configuration:

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

The `aws.properties` file has to be placed in the same location as the `config.yml` file. You may use the `aws.properties.example` file as a template.  
__Note: Do not rename__ `aws.properties.example` __to__ `aws.properties` __but rather create a new file named__ `aws.properties` __instead.__  
Otherwise, Git will no longer ignore the file, possibly resulting in your AWS credentials being committed and pushed and available to everyone.  

Implemented by `de.ustutt.iaas.cc.core.QueueTextProcessor`.

## Notes storage configuration

The notebook application supports three different implementations for storing notes:

1. `tmp` - non-persistent, in-memory data storage
2. `gcds` - persistent data storage using the Google Cloud Datastore
3. `jdbc` - persistent data storage using JDBC for accessing a relational SQL database

The internal interface for the notes storage is defined by `de.ustutt.iaas.cc.core.INotebookDAO`. During startup, the configuration file is read and a corresponding implementation of this interface is instantiated.   

#### "tmp"

non-persistent, in-memory data storage  

```
notesDB:
  mode: tmp
```

Implemented by `de.ustutt.iaas.cc.core.SimpleNotebookDAO`.

#### "gcds"

persistent data storage using the Google Cloud Datastore  

```
notesDB:
  mode: gcds
  gcProjectId: hauptfn-167617
  gcKeyFile: hauptfn-063896e58594_CloudDatastoreUser.json
```

The `gcProjectId` parameter contains the ID of the Google Cloud project that shall be used to access the Google Cloud Storage. The `gcKeyFile` parameter refers to a local file that contains the key that is required to access the Google Cloud Datastore. 

Implemented by `de.ustutt.iaas.cc.core.GoogleDatastoreNotebookDAO`.

Note: the parameters "gcProjectId" and "gcKeyFile" are currently __not yet__ configurable using the configuration file.

#### "jdbc"

persistence using JDBC for access to a relational SQL database

```
notesDB:
  mode: jdbc
```

In addition to the `config.yml` file a properties file named `db.properties` has to be present containing username and password for the database as follows:  
```
user = ...
password = ...
```

You may use `db.properties.example` as a template.  
__Note: Do not rename__ `db.properties.example` __to__ `db.properties` __but rather create a new file named__ `db.properties` __instead.__  
Otherwise, Git will no longer ignore the file, possibly resulting in your database credentials being committed and pushed and available to everyone. The `db.properties` file has to be placed in the same location as the `config.yml` file. The `db.properties` is referenced from the the `config.yml` file using expressions like `${dbprops.user!'sa'}`.  

Using jdbc mode requires a JDBC database configuration as described in e.g. [http://www.dropwizard.io/1.1.0/docs/manual/jdbi.html](http://www.dropwizard.io/1.1.0/docs/manual/jdbi.html). The `config.yml` file already contains such a configuration for using a [file-based H2 database](http://www.h2database.com/html/features.html#embedded_databases) or a MySQL database (tested with AWS RDS).

When the notebook application is run in JDBC mode, the database first has to be initialized (i.e. the required tables have to be created first). For that, the notebook application supports several commands for creating and managing the database (see also [http://www.dropwizard.io/1.1.0/docs/manual/migrations.html](http://www.dropwizard.io/1.1.0/docs/manual/migrations.html)):

Create or update the database in dry-run mode, i.e. the database is not changed but the SQL commands to be executed are shown on the command line:  
```
java -jar notebookapp-0.2.0.jar db migrate --dry-run config.yml  
```

Create or update the database (has to be executed once before running the notebook application for the first time):  
```
java -jar notebookapp-0.2.0.jar db migrate config.yml  
```

Check database status:  
```
java -jar notebookapp-0.2.0.jar db status config.yml  
```
Implemented by `de.ustutt.iaas.cc.core.DatabaseNotebookDAO`.

## Installing and running release versions of the notebook application

The following commands have been tested with Ubuntu Server 16.04 LTS on AWS EC2. During the installation, the service will be configured to automatically start during system boot.

```
sudo apt-get update  
sudo apt-get install --yes openjdk-8-jre-headless  
wget https://github.com/F7502/notebookapp/releases/download/v0.2.0/notebookapp-0.2.0.jar  
wget https://github.com/F7502/notebookapp/releases/download/v0.2.0/config.yml  
wget https://github.com/F7502/notebookapp/releases/download/v0.2.0/notebookapp  
sudo mv notebookapp /etc/init.d/  
sudo chmod +x /etc/init.d/notebookapp  
sudo update-rc.d notebookapp defaults  
```

At this point, you will have to adapt the `config.yml` file to your specific setup (or you already download an adapted configuration file before, instead of using the default one). When using SQS, you have to add the `aws.properties` file containing your AWS credentials. When using a database, you also have to add the `db.properties` file containing your database access data. When using a fresh/empty database, you first have to initialize it by running the following command: 

```
java -jar notebookapp-0.2.0.jar db migrate config.yml  
```

After adapting the configuration (and initializing the database if necessary), the application can be started:  

```
sudo /etc/init.d/notebookapp start  
```