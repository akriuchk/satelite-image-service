# Satelite image service

Simple backend application that is capable of working with image metadata

## About The Project

This service is created with API-First approach, you can find `OpenAPI` specification 
in [resource](src/main/resources/oas/satelite-image-service.yaml) folder.

### Built With

* springboot
* java11
* h2/postgres
* openapi codegen
* git
* github actions
* docker

## Getting Started

### Prerequisites

To build application several tools are required:

* java11
* maven (tested with 3.6.3)

* (opt) docker

### Set up application

#### 1. The Simplest variation: app with h2 db, without data

1. Clone the repo
   ```sh
   git clone git@github.com:akriuchk/satelite-image-service.git
   ```
2. Run application: this will generate required code and start application
   ```sh
   mvn spring-boot:run
   ```

4. Application is available `http://localhost:8080` by default.

#### 2. Docker compose with postgres

1. Clone the repo
   ```sh
   git clone git@github.com:akriuchk/satelite-image-service.git
   ```
2. Run application: this will generate required code, execute tests and create `app.jar` 
   artifact in `./docker` folder
   ```sh
   mvn clean package
   ```
3. Go to `./docker` folder and run docker compose
   ```sh
   docker-compose up
   ```

4. Application is available `http://localhost:8080` by default.

### Configuration and test

There is no special API to upload json files with features, but it is possible to use
`demo` profile(enabled by default in docker-compose).
This profile enables search and processing of `source-data.json` in folder near `app.jar` file.

#### Test

Most simple way is replace `source-data.json` in project root with your test file and run it with simplest method,
described in `Set up application` chapter.

* After application is up and running, get all features by `GET /features` endpoint

* Then you can select random feature `id` and execute next request, like `GET /features/{id}` to get info 
about one particular feature.

* Last possibility is `GET /features/{id}/quicklook` to get a picture.
