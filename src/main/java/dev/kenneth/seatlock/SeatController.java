package dev.kenneth.seatlock;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SeatController {

    private final SeatService service;

    public SeatController(SeatService service) {
        this.service = service;
    }

    @GetMapping("/seats/{id}")
    public ResponseEntity<String> getSeat(@PathVariable Long id) {
        Optional<SeatEntity> result = service.findSeat(id);

        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Seat code: " + result.get().getCode());
    }
}
