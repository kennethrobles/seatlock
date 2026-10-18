package dev.kenneth.seatlock;

import java.time.LocalDateTime;

// Temporary seat reservation: expires 10 minutes after creation.
public class Reservation {
    private static final int RESERVATION_MINUTES = 10;

    private final LocalDateTime createdAt;
    private final LocalDateTime expiresAt;
    private ReservationStatus status; // not final: changes when confirmed

    public Reservation(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        this.expiresAt = createdAt.plusMinutes(RESERVATION_MINUTES);
        this.status = ReservationStatus.PENDING; // every reservation starts as PENDING
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    // Time is passed in (not now()) so it can be tested.
    // Still valid exactly at expiresAt; expired after that.
    public boolean isExpired(LocalDateTime now) {
        return now.isAfter(expiresAt);
    }

    public ReservationStatus getStatus() {
        return status;
    }

    // Confirms the reservation only if it is PENDING and not expired.
    public void confirm(LocalDateTime now) {
        if (status != ReservationStatus.PENDING) {
            throw new IllegalStateException("Only PENDING reservations can be confirmed");
        }
        if (isExpired(now)) {
            throw new IllegalStateException("Cannot confirm an expired reservation");
        }
        status = ReservationStatus.CONFIRMED;
    }
}