
package dev.kenneth.seatlock;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import org.junit.jupiter.api.Test;

class SeatTest {

    // All reservations are created at 10:00 UTC, so they expire at 10:10 UTC.
    private static final Instant CREATED = Instant.parse("2026-10-17T10:00:00Z");

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
        assertTrue(s.isOccupied(CREATED.plusSeconds(300)));
    }

    @Test
    void seatIsFreeAfterReservationExpires() {
        Seat s = new Seat("A12");
        s.addReservation(new Reservation(CREATED));
        assertFalse(s.isOccupied(CREATED.plusSeconds(601)));
    }

    @Test
    void seatIsOccupiedIfAnyReservationIsActive() {
        Seat s = new Seat("A12");
        Reservation expired = new Reservation(CREATED);
        Reservation confirmed = new Reservation(CREATED);
        confirmed.confirm(CREATED.plusSeconds(300));
        s.addReservation(expired);
        s.addReservation(confirmed);
        assertTrue(s.isOccupied(CREATED.plusSeconds(3600)));
    }
}
