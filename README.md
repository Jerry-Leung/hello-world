# hello-world

A simple Spring Boot application exposing a `Hello World` endpoint.

## Requirements

- Java 21+ (Java 22 works too)
- No local Gradle needed — the Gradle Wrapper (`./gradlew`) is included

## Run

```bash
./gradlew bootRun
```

Then open http://localhost:8080/ — it returns `Hello World`.

## Build

```bash
./gradlew clean build
java -jar build/libs/hello-world-0.0.1-SNAPSHOT.jar
```

## Test

```bash
./gradlew test
```

## Endpoint

| Method | Path | Response      |
|--------|------|---------------|
| GET    | `/`  | `Hello World` |
