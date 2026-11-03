
package dev.kenneth.seatlock;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import org.junit.jupiter.api.Test;

class ReservationTest {

    // All reservations are created at 10:00 UTC, so they expire at 10:10 UTC.
    private static final Instant CREATED = Instant.parse("2026-10-17T10:00:00Z");

    @Test
    void expiresTenMinutesAfterCreation() {
        Reservation r = new Reservation(CREATED);
        assertEquals(CREATED.plusSeconds(600), r.getExpiresAt());
    }

    @Test
    void isNotExpiredAtFiveMinutes() {
        Reservation r = new Reservation(CREATED);
        assertFalse(r.isExpired(CREATED.plusSeconds(300)));
    }

    @Test
    void isNotExpiredExactlyAtExpiresAt() {
        Reservation r = new Reservation(CREATED);
        assertFalse(r.isExpired(CREATED.plusSeconds(600)));
    }

    @Test
    void isExpiredOneSecondAfterExpiresAt() {
        Reservation r = new Reservation(CREATED);
        assertTrue(r.isExpired(CREATED.plusSeconds(601)));
    }

    @Test
    void confirmExactlyAtExpiresAtSetsConfirmed() {
        Reservation r = new Reservation(CREATED);
        r.confirm(CREATED.plusSeconds(600));
        assertEquals(ReservationStatus.CONFIRMED, r.getStatus(CREATED.plusSeconds(600)));
    }

    @Test
    void newReservationIsPending() {
        Reservation r = new Reservation(CREATED);
        assertEquals(ReservationStatus.PENDING, r.getStatus(CREATED));
    }

    @Test
    void confirmBeforeExpirationSetsConfirmed() {
        Reservation r = new Reservation(CREATED);
        r.confirm(CREATED.plusSeconds(300));
        assertEquals(ReservationStatus.CONFIRMED, r.getStatus(CREATED.plusSeconds(300)));
    }

    @Test
    void confirmAfterExpirationThrows() {
        Reservation r = new Reservation(CREATED);
        assertThrows(IllegalStateException.class, () -> r.confirm(CREATED.plusSeconds(601)));
    }

    @Test
    void confirmTwiceThrows() {
        Reservation r = new Reservation(CREATED);
        r.confirm(CREATED.plusSeconds(300));
        assertThrows(IllegalStateException.class, () -> r.confirm(CREATED.plusSeconds(360)));
        assertEquals(ReservationStatus.CONFIRMED, r.getStatus(CREATED.plusSeconds(360)));
    }

    @Test
    void confirmedReservationNeverExpires() {
        Reservation r = new Reservation(CREATED);
        r.confirm(CREATED.plusSeconds(300));
        assertFalse(r.isExpired(CREATED.plusSeconds(660)));
    }

    @Test
    void pendingReservationShowsExpiredAfterExpiresAt() {
        Reservation r = new Reservation(CREATED);
        assertEquals(ReservationStatus.EXPIRED, r.getStatus(CREATED.plusSeconds(601)));
    }

    @Test
    void isActiveWhilePending() {
        Reservation r = new Reservation(CREATED);
        assertTrue(r.isActive(CREATED.plusSeconds(300)));
    }

    @Test
    void isActiveExactlyAtExpiresAt() {
        Reservation r = new Reservation(CREATED);
        assertTrue(r.isActive(CREATED.plusSeconds(600)));
    }

    @Test
    void isNotActiveOneSecondAfterExpiresAt() {
        Reservation r = new Reservation(CREATED);
        assertFalse(r.isActive(CREATED.plusSeconds(601)));
    }

    @Test
    void confirmedReservationIsActiveAfterExpiresAt() {
        Reservation r = new Reservation(CREATED);
        r.confirm(CREATED.plusSeconds(300));
        assertTrue(r.isActive(CREATED.plusSeconds(3600)));
    }
}
