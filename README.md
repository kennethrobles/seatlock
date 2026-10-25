# SeatLock

A REST API to book seats for events, like concerts or theater. The main goal: **never sell the same seat twice**, even when two people try to book it at the same moment.

> 🚧 Work in progress. I'm building it step by step while learning Java backend.

## The problem

If two requests check "is this seat free?" at the same time, both can get "yes" and both book it. The seat is sold twice. This is called a race condition. SeatLock will reproduce this bug with a test and then fix it.

## How a reservation works

1. A client reserves a seat. The reservation is `PENDING` for 10 minutes.
2. The client can confirm it until `expiresAt` (included). It becomes `CONFIRMED`.
3. If nobody confirms it in time, it becomes `EXPIRED` and the seat is free again.

Only `PENDING` reservations can expire. A `CONFIRMED` reservation never expires.

## Progress

- [x] Phase 0: Java review
- [ ] Phase 1: Domain in plain Java (in progress)
  - [x] `Reservation`: states, confirm, expiration and effective status
  - [ ] `Seat`
- [ ] Phase 2: PostgreSQL and data
- [ ] Phase 3: REST API with Spring Boot
- [ ] Phase 4: Concurrency: reproduce and fix double booking
- [ ] Phase 5: Expiration job and clear HTTP errors
- [ ] Phase 6: Run with one command, CI and API docs

## Tech stack

**Now:** Java 25, Maven, JUnit.

**Planned:** Spring Boot, PostgreSQL, JPA/Hibernate, Flyway, Testcontainers, Docker Compose, GitHub Actions.

## Run the tests

You need Java 25 and Maven.

```
mvn test
```