
package grupoLem.appGestion.controller;

import grupoLem.appGestion.exception.ResourceNotFoundException;
import grupoLem.appGestion.model.Reservation;
import grupoLem.appGestion.model.Room;
import grupoLem.appGestion.model.RoomState;
import grupoLem.appGestion.repository.RoomRepository;
import grupoLem.appGestion.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private RoomRepository roomRepository;

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
    @GetMapping("/buscarReserva/{idReservations}")
    public ResponseEntity<Reservation> searchReservationById(@PathVariable Integer idReservation){
        Reservation reservation = this.reservationService.findById(idReservation);
        if (reservation != null) {
            return ResponseEntity.ok(reservation);
        }else{
            throw new ResourceNotFoundException("Reserva no encontrada con el ID: "+idReservation);
        }
    }

    //Editar una reserva
    @PutMapping("/editarReserva/{idReservations}")
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
        this.reservationService.save(reservation);
        return ResponseEntity.ok(reservation);
    }

    //Eliminar una reserva
    @DeleteMapping("/eliminarReserva/{idReservations}")
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

    @GetMapping("/habitacionesDisponibles")
    public List<Room> findAvailableRooms(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                         @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Reservation> overlappingReservations = reservationService.findOverlappingReservations(startDate, endDate, null);
        List<Room> allRooms = roomRepository.findAll();

        List<Room> availableRooms = new ArrayList<>(allRooms);
        for (Reservation reservation : overlappingReservations) {
            availableRooms.remove(reservation.getRoom());
            reservation.getRoom().setRoomState(RoomState.OCUPADA);
        }

        return availableRooms;
    }
}

//List<Room> availableRooms = reservationService.findAvailableRooms(startDate, endDate);
//        availableRooms.forEach(room -> {
//        room.getReservations().stream()
//                    .filter(reservation -> reservation.getStartDate().isBefore(endDate) && reservation.getEndDate().isAfter(startDate))
//        .forEach(reservation -> room.setRoomState(RoomState.OCUPADA));
//        });