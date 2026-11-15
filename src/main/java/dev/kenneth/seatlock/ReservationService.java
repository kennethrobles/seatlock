package dev.kenneth.seatlock;

import java.time.Instant;

import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationRepository repository;

    public ReservationService(ReservationRepository repository) {
        this.repository = repository;
    }

    public ReservationEntity createReservation(long seatId, Instant createdAt) {
        ReservationEntity reservation = new ReservationEntity(seatId, createdAt);
        return repository.save(reservation);
    }
}
