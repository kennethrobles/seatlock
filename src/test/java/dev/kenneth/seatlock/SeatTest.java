package dev.kenneth.seatlock;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class SeatTest {

    // All reservations are created at 10:00, so they expire at 10:10.
    private static final LocalDateTime CREATED = LocalDateTime.of(2026, 10, 17, 10, 0);

    @Test
    void createsSeatWithCode() {
        Seat s = new Seat("A12");
        assertEquals("A12", s.getCode());
    }

    @Test
    void blankCodeThrows() {
        assertThrows(IllegalArgumentException.class, () -> new Seat(" "));
    }

    @Test
    void seatWithoutReservationsIsFree() {
        Seat s = new Seat("A12");
        assertFalse(s.isOccupied(CREATED));
    }

    @Test
    void seatIsOccupiedWhileReservationIsActive() {
        Seat s = new Seat("A12");
        s.addReservation(new Reservation(CREATED));
        assertTrue(s.isOccupied(CREATED.plusMinutes(5)));
    }

    @Test
    void seatIsFreeAfterReservationExpires() {
        Seat s = new Seat("A12");
        s.addReservation(new Reservation(CREATED));
        assertFalse(s.isOccupied(CREATED.plusMinutes(10).plusSeconds(1)));
    }

    @Test
    void seatIsOccupiedIfAnyReservationIsActive() {
        Seat s = new Seat("A12");
        Reservation expired = new Reservation(CREATED);
        Reservation confirmed = new Reservation(CREATED);
        confirmed.confirm(CREATED.plusMinutes(5));
        s.addReservation(expired);
        s.addReservation(confirmed);
        assertTrue(s.isOccupied(CREATED.plusHours(1)));
    }
}