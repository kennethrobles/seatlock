package dev.kenneth.seatlock;

import java.time.Instant;

// Temporary seat reservation: expires 10 minutes after creation.
public class Reservation {
    private static final int RESERVATION_MINUTES = 10;

    private final Instant createdAt;
    private final Instant expiresAt;
    private ReservationStatus status; // not final: changes when confirmed

    public Reservation(Instant createdAt) {
        this.createdAt = createdAt;
        this.expiresAt = createdAt.plusSeconds(RESERVATION_MINUTES * 60);
        this.status = ReservationStatus.PENDING; // every reservation starts as PENDING
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    // Only PENDING reservations expire: still valid exactly at expiresAt, expired
    // after that.
    public boolean isExpired(Instant now) {
        return now.isAfter(expiresAt) && this.status == ReservationStatus.PENDING;
    }

    // Effective status: a PENDING reservation past expiresAt is shown as EXPIRED.
    public ReservationStatus getStatus(Instant now) {
        if (isExpired(now)) {
            return ReservationStatus.EXPIRED;
        }
        return status;
    }

    // Confirms the reservation only if it is PENDING and not expired.
    public void confirm(Instant now) {
        if (status != ReservationStatus.PENDING) {
            throw new IllegalStateException("Only PENDING reservations can be confirmed");
        }
        if (isExpired(now)) {
            throw new IllegalStateException("Cannot confirm an expired reservation");
        }
        status = ReservationStatus.CONFIRMED;
    }

    public boolean isActive(Instant now) {
        return !isExpired(now);
    }
}