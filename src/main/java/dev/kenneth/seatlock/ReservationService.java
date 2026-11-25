package dev.kenneth.seatlock;

import java.time.Instant;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Optional<ReservationEntity> findReservation(Long id) {
        Optional<ReservationEntity> result = repository.findById(id);
        return result;
    }

    @Transactional
    public Optional<ReservationEntity> confirmReservation(Long id, Instant now) {
        Optional<ReservationEntity> result = repository.findById(id);

        if (result.isEmpty()) {
            return result;
        }

        ReservationEntity entity = result.get();
        // Hibernate tracks changes to the loaded entity until commit.
        entity.confirm(now);

        return result;
    }
}
