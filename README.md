# SeatLock

SeatLock is a Java REST API for reserving and confirming seats at events. It is a portfolio project in progress built with Java 25, Spring Boot, Spring Data JPA, PostgreSQL, Flyway and JUnit.

## What works today

| Request | Result |
| --- | --- |
| `GET /seats/{id}` | Returns the seat code or `404`. |
| `POST /seats/{seatId}/reservations` | Creates a pending reservation and returns its generated ID. |
| `GET /reservations/{id}` | Returns the reservation ID, seat ID and timestamps, or `404`. |
| `POST /reservations/{id}/confirm` | Confirms a pending reservation within its hold period and returns `204`, or `404` if the reservation does not exist. |

A reservation starts as `PENDING` for ten minutes. It can be confirmed up to and including its expiry time. The domain rejects late and repeated confirmations. The service loads and updates the reservation in one transaction.

PostgreSQL limits each seat to one confirmed reservation. The next milestone is to reproduce overlapping pending holds and test the concurrency fix. Automatic expiry cleanup, consistent HTTP conflict responses and CI are planned.

## Run locally

Install Java 25, Maven and Docker with Compose.

For a fresh database, export `SEATLOCK_DB_PASSWORD` with a password you choose for local PostgreSQL, then run:

```sh
docker compose up -d db
docker compose exec -T db pg_isready -U seatlock -d seatlock
SEATLOCK_FLYWAY_ENABLED=true mvn spring-boot:run
```

Start Spring after PostgreSQL reports that it is accepting connections. Flyway applies V1–V3 to create the schema, insert sample events and seats, and create the reservation ID sequence. Use the same flag on later launches of that Flyway-managed database. For a database that was initialized manually, use its existing password and run `mvn spring-boot:run` with Flyway disabled (the default).

The API listens on port `8080`:

```sh
curl -i http://localhost:8080/seats/1
curl -sS -X POST http://localhost:8080/seats/1/reservations
```

Use the returned ID with `GET /reservations/{id}` or `POST /reservations/{id}/confirm`.

## Run the tests

```sh
mvn test
```

The unit tests cover reservation states, expiry boundaries, entity conversion and seat occupancy. They run without PostgreSQL.
