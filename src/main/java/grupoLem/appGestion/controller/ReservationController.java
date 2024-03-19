
package grupoLem.appGestion.controller;

import grupoLem.appGestion.exception.ResourceNotFoundException;
import grupoLem.appGestion.model.Reservation;
import grupoLem.appGestion.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    //traer todas las reservas
    @GetMapping("/reservas")
    public List<Reservation> findAllReservations(){
        List<Reservation> reservationsList = this.reservationService.getAllReservations();
        return reservationsList;
    }

    //Crear una reserva
    @PostMapping("/nuevaReserva")
    public Reservation createReservation(@RequestBody Reservation reservations){
        return this.reservationService.save(reservations);
    }

    //Buscar Reserva por ID
    @GetMapping("/buscarReserva/{id}")
    public ResponseEntity<Reservation> searchReservationById(@PathVariable Integer idReservation){
        Reservation reservation = this.reservationService.findById(idReservation);
        if (reservation != null) {
            return ResponseEntity.ok(reservation);
        }else{
            throw new ResourceNotFoundException("Reserva no encontrada con el ID: "+idReservation);
        }
    }

    //Editar una reserva
    @PutMapping("/editarReserva/{id}")
    public ResponseEntity<Reservation> updateReservations(@PathVariable Integer idReservation,
                                                           @RequestBody Reservation reservationsReceived){
        Reservation reservation = this.reservationService.findById(idReservation);
        if (reservation == null)
            throw new ResourceNotFoundException("Reserva No encontrada con el ID: "+ idReservation);
        reservation.setHost(reservationsReceived.getHost());
        reservation.setTypePension(reservationsReceived.getTypePension());
        reservation.setCheckInDate(reservationsReceived.getCheckInDate());
        reservation.setCheckInTime(reservationsReceived.getCheckInTime());
        reservation.setCheckOutDate(reservationsReceived.getCheckOutDate());
        reservation.setCheckOutTime(reservationsReceived.getCheckOutTime());
        reservation.setEndDate(reservationsReceived.getEndDate());
        reservation.setStartDate(reservationsReceived.getStartDate());
        reservation.setIncludesBreakfast(reservationsReceived.isIncludesBreakfast());
        reservation.setIncludesHalfPension(reservationsReceived.isIncludesHalfPension());
        reservation.setIncludesFullPension(reservationsReceived.isIncludesFullPension());
        this.reservationService.save(reservation);
        return ResponseEntity.ok(reservation);
    }

    //Eliminar una reserva
    @DeleteMapping("/eliminarReserva/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteReservation(@PathVariable Integer idReservation){
        Reservation reservation = this.reservationService.findById(idReservation);
        if (reservation == null)
            throw new ResourceNotFoundException("Reserva No encontrada con el ID: "+idReservation);
        this.reservationService.deleteById(idReservation);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Eliminado", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    // Encontrar reserva por fecha de inicio
    @GetMapping("/comienzoReserva/{startDate}")
    public List<Reservation> findByStartDate(@PathVariable LocalDate startDate) {
        return reservationService.findByStartDate(startDate);
    }

    // Encontrar reserva por fecha de finalizacion
    @GetMapping("/finalizaReserva/{endDate}")
    public List<Reservation> findByEndDate(@PathVariable LocalDate endDate) {
        return reservationService.findByEndDate(endDate);
    }

    // Encontrar reservas que incluya el desayuno
    @GetMapping("/incluyeDesayuno/{includesBreakfast}")
    public List<Reservation> findByIncludesBreakfast(@PathVariable boolean includesBreakfast) {
        return reservationService.findByIncludesBreakfast(includesBreakfast);
    }

    // Encontrar reservas que sean media pension
    @GetMapping("/mediaPension/{includesHalfPension}")
    public List<Reservation> findByIncludesHalfPension(@PathVariable boolean includesHalfPension) {
        return reservationService.findByIncludesHalfPension(includesHalfPension);
    }

    // Encontrar reservas que sea pension completa
    @GetMapping("/pensionCompleta/{includesFullPension}")
    public List<Reservation> findByIncludesFullPension(@PathVariable boolean includesFullPension) {
        return reservationService.findByIncludesFullPension(includesFullPension);
    }
}

