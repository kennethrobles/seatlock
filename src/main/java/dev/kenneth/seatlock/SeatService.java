package dev.kenneth.seatlock;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class SeatService {

    private final SeatRepository repository;

    public SeatService(SeatRepository repository) {
        this.repository = repository;
    }

    public String findSeat(Long id) {
        Optional<SeatEntity> result = repository.findById(id);

        if (result.isPresent()) {
            SeatEntity seat = result.get();
            return "Seat code: " + seat.getCode();
        }
        return "Seat not found";
    }
}
