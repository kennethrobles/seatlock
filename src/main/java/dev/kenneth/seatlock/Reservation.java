package dev.kenneth.seatlock;

import java.time.LocalDateTime;

// Reserva temporal de un asiento: caduca a los 10 minutos de crearse.
public class Reservation {

    private static final int MINUTOS_RESERVA = 10;

    private final LocalDateTime createdAt;
    private final LocalDateTime expiresAt;

    public Reservation(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        this.expiresAt = createdAt.plusMinutes(MINUTOS_RESERVA);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    // La hora llega por parámetro (no now()) para poder testearla.
    // Justo en expiresAt aún es válida; caduca después.
    public boolean isExpired(LocalDateTime now) {
        return now.isAfter(expiresAt);
    }
}