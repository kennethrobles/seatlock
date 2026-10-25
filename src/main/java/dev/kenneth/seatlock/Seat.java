package dev.kenneth.seatlock;

// A seat in a venue, identified by its code (e.g. "A12").
public class Seat {
    private final String code;

    public Seat(String code) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Seat code must not be blank");
        }
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}