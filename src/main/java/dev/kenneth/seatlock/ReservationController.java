package dev.kenneth.seatlock;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.time.Instant;

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
    } // Aquí termina createReservation

    @GetMapping("/reservations/{id}")
    public ResponseEntity<Long> findReservation(@PathVariable Long id) {
        var resultado = service.findReservation(id);

        if (resultado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(resultado.get().getId());
    }
}