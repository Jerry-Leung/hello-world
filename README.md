# hello-world

A simple Spring Boot application exposing a `Hello World` endpoint.

## Requirements

- Java 21+ (Java 22 works too)
- No local Maven needed — the Maven Wrapper (`./mvnw`) is included

## Run

```bash
./mvnw spring-boot:run
```

Then open http://localhost:8080/ — it returns `Hello World`.

## Build

```bash
./mvnw clean package
java -jar target/hello-world-0.0.1-SNAPSHOT.jar
```

## Test

```bash
./mvnw test
```

## Endpoint

| Method | Path | Response      |
|--------|------|---------------|
| GET    | `/`  | `Hello World` |
