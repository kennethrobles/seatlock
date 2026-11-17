package dev.kenneth.seatlock;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class SeatService {

    private final SeatRepository repository;

    public SeatService(SeatRepository repository) {
        this.repository = repository;
    }

    public Optional<SeatEntity> findSeat(Long id) {
        Optional<SeatEntity> result = repository.findById(id);
        return result;
    }
}
