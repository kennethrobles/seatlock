package dev.kenneth.seatlock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// A seat in a venue, identified by its code (e.g. "A12").
public class Seat {
    private final String code;
    private final List<Reservation> reservations = new ArrayList<>();

    public Seat(String code) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Seat code must not be blank");
        }
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    // Occupied if any of its reservations is active at that time.
    public boolean isOccupied(LocalDateTime now) {
        for (Reservation reservation : reservations) {
            if (reservation.isActive(now)) {
                return true;
            }
        }
        return false;
    }
}