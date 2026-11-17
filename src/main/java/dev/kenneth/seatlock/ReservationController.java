package dev.kenneth.seatlock;

import java.time.Instant;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationController {

    private final ReservationService service;

    public ReservationController(ReservationService service) {
        this.service = service;
    }

    @PostMapping("/seats/{seatId}/reservations")
    public Long createReservation(@PathVariable long seatId) {
        ReservationEntity guardada = service.createReservation(seatId, Instant.now());
        return guardada.getId();
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<ReservationResponse> findReservation(@PathVariable Long id) {
        var resultado = service.findReservation(id);

        if (resultado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ReservationEntity reservation = resultado.get();
        ReservationResponse response = new ReservationResponse(reservation.getId(), reservation.getSeatId(),
                reservation.getCreatedAt(), reservation.getExpiresAt());

        return ResponseEntity.ok(response);
    }
}
