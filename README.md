# CryptoMapAPI

CryptoMapAPI is an API built with Scala, http4s, Circe, Doobie, and Cats-Effect. It provides various services related to cryptocurrency locations, listings, merchants, reviews, transactions, and user management.

## Prerequisites

- Java 8 or newer
- SBT (Scala Build Tool)

## Getting Started

1. Clone the repository:

git clone https://github.com/TwentyOne37/cryptomap.git

css

2. Change directory to the project folder:

cd CryptoMapAPI

3. Run the application:

sbt run

4. Run tests:

sbt test

## Postman Collection

The project includes a Postman collection that can be found in the following path:

src/main/resources/postman/CryptoM.app API v1.postman_collection.json

Import this file into Postman to test the API endpoints.

## Test Coverage

To generate test coverage reports, add the following sbt plugin to the `plugins.sbt` file:

addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.9.2")

Run the following command to generate the test coverage report:

sbt coverage test coverageReport

The report will be generated in the `target/scala-2.13/scoverage-report` directory.

## API Documentation

This README provides instructions on how to run the application, run tests, use the provided Postman collection, generate test coverage reports, and suggests using an API documentation tool to display the API routes.
