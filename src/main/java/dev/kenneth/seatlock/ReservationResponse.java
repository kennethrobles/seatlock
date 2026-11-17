package dev.kenneth.seatlock;

import java.time.Instant;

public record ReservationResponse(Long id, long seatId, Instant createdAt, Instant expiresAt) {
}
