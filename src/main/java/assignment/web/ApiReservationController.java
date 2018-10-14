package assignment.web;

import assignment.domain.Reservation;
import assignment.dto.ReservationDTO;
import assignment.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ApiReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<Reservation>> get(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return ResponseEntity.ok(reservationService.get(date));
    }

    @PostMapping
    public ResponseEntity<Void> add(@Valid @RequestBody ReservationDTO reservationDTO) {
        reservationService.add(reservationDTO);
        return ResponseEntity.ok().build();
    }
}
