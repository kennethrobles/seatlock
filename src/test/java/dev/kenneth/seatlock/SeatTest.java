package dev.kenneth.seatlock;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SeatTest {

    @Test
    void createsSeatWithCode() {
        Seat s = new Seat("A12");
        assertEquals("A12", s.getCode());
    }

    @Test
    void blankCodeThrows() {
        assertThrows(IllegalArgumentException.class, () -> new Seat(" "));
    }
}