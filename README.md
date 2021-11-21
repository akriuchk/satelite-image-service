# Satelite image service

Simple backend application that is capable of working with image metadata

## About The Project

This service is created with API-First approach, you can find `OpenAPI` specification 
in [resource](src/main/resources/oas/satelite-image-service.yaml) folder.

### Built With

* springboot
* java11
* h2
* openapi codegen

## Getting Started

### Prerequisites

To build application several tools are required:

* java11
* maven (tested with 3.6.3)

### Set up application

1. Clone the repo
   ```sh
   git clone git@github.com:akriuchk/satelite-image-service.git
   ```
2. Run application: this will generate required code and start spring context
   ```sh
   mvn spring-boot:run
   ```
   
3. Application is available `http://localhost:8080` by default.
