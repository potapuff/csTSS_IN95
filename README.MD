# Simple database explorer project

The project is a simple SqLite3 database explorer built on top of Javaline. Just for study purposes.

## Prerequisites

Project is written on Java 16 (17 work to). Project requires maven (bundled with Intelj Idea or standalone)
13+, GIT

## Installation

### First step

1. Clone this (or you command) GIT repository to your computer :).
2. Copy files from `samples` directory to project root.
3. Build with Maven and run it.



### Config file

Create config file (any name) with values for keys placed blow. All keys are mandatory. All keys can be specified as OS
environment variables.

| Key   | Value |    
|-------|-------|
|DB.NAME | Name of database for Elephant app |
|APP.URL| Application url (including domain, protocol and port, mostly used for mails) |
|APP.PORT| Internal port for application. Port can be different from APP.URL port, if there is any proxy (like nginx) before Elephant |  
|ENV| PRODUCTION or other . In PRODUCTION mode error page does not show stack traces |

Example:

```
APP.URL = 127.0.0.1
APP.PORT = 7000
ENV = development
DB.NAME = chinook.db
```
We use Chinook sample database obtained from https://www.sqlitetutorial.net/sqlite-sample-database/ .

## How to run?

1. Build project
   `mvn clean install -DskipTest=true`
2. run
   `java -jar target/elephant.jar %your-config-file%`

# Contributors

Kuzikov Borys <b.kuzikov@dl.sumdu.edu.ua>