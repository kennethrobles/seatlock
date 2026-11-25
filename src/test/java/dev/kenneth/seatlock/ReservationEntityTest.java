package dev.kenneth.seatlock;

import java.time.Instant;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReservationEntityTest {

    @Test
    void convertsEntityToDomain() {
        Instant createdAt = Instant.parse("2026-11-22T10:00:00Z");
        ReservationEntity entity = new ReservationEntity(1L, createdAt);

        Reservation domain = entity.toDomain();

        assertEquals(createdAt, domain.getCreatedAt());
        assertEquals(entity.getExpiresAt(), domain.getExpiresAt());
        assertEquals(ReservationStatus.PENDING, domain.getStatus(createdAt));
    }

    @Test
    void confirmsEntityAtExpiration() {
        Instant createdAt = Instant.parse("2026-11-22T10:00:00Z");
        ReservationEntity entity = new ReservationEntity(1L, createdAt);
        Instant confirmationTime = entity.getExpiresAt();

        entity.confirm(confirmationTime);

        assertEquals(ReservationStatus.CONFIRMED, entity.toDomain().getStatus(confirmationTime));
    }
}
