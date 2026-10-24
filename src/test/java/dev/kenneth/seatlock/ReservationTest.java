package dev.kenneth.seatlock;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class ReservationTest {

// All reservations are created at 10:00, so they expire at 10:10.
    private static final LocalDateTime CREATED = LocalDateTime.of(2026, 10, 17, 10, 0);

    @Test
    void expiresTenMinutesAfterCreation() {
        Reservation r = new Reservation(CREATED);
        assertEquals(CREATED.plusMinutes(10), r.getExpiresAt());
    }

    @Test
    void isNotExpiredAtFiveMinutes() {
        Reservation r = new Reservation(CREATED);
        assertFalse(r.isExpired(CREATED.plusMinutes(5)));
    }

    @Test
    void isNotExpiredExactlyAtExpiresAt() {
        Reservation r = new Reservation(CREATED);
        assertFalse(r.isExpired(CREATED.plusMinutes(10)));
    }

    @Test
    void isExpiredAtElevenMinutes() {
        Reservation r = new Reservation(CREATED);
        assertTrue(r.isExpired(CREATED.plusMinutes(11)));
    }

    @Test
    void newReservationIsPending() {
        Reservation r = new Reservation(CREATED);
        assertEquals(ReservationStatus.PENDING, r.getStatus());
    }

    @Test
    void confirmBeforeExpirationSetsConfirmed() {
        Reservation r = new Reservation(CREATED);
        r.confirm(CREATED.plusMinutes(5));
        assertEquals(ReservationStatus.CONFIRMED, r.getStatus());
    }

    @Test
    void confirmAfterExpirationThrows() {
        Reservation r = new Reservation(CREATED);
        assertThrows(IllegalStateException.class, () -> r.confirm(CREATED.plusMinutes(11)));
    }

    @Test
    void confirmTwiceThrows() {
        Reservation r = new Reservation(CREATED);
        r.confirm(CREATED.plusMinutes(5)); // first confirm: valid
        assertThrows(IllegalStateException.class, () -> r.confirm(CREATED.plusMinutes(6)));
    }

    @Test 
    void confirmedReservationNeverExpires() {
        Reservation r = new Reservation(CREATED);
        r.confirm(CREATED.plusMinutes(5));
        assertFalse(r.isExpired(CREATED.plusMinutes(11)));
    }
}