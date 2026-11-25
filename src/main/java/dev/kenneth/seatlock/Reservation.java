package dev.kenneth.seatlock;

import java.time.Instant;

// A new reservation holds a seat for ten minutes or until it is confirmed.
public class Reservation {
    private static final int RESERVATION_MINUTES = 10;

    private final Instant createdAt;
    private final Instant expiresAt;
    private ReservationStatus status; // not final: changes when confirmed

    public Reservation(Instant createdAt) {
        this.createdAt = createdAt;
        this.expiresAt = createdAt.plusSeconds(RESERVATION_MINUTES * 60);
        this.status = ReservationStatus.PENDING;
    }

    // Reuse the stored expiry and status when rebuilding a reservation.
    public Reservation(Instant createdAt, Instant expiresAt, ReservationStatus status) {
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.status = status;
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
        if (getStatus(now) == ReservationStatus.EXPIRED) {
            return false;
        }
        return true;
    }
}
