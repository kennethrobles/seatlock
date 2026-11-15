package dev.kenneth.seatlock;

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
    public String getSeat(@PathVariable Long id) {
        return service.findSeat(id);
    }
}