
package grupoLem.appGestion.controller;

import grupoLem.appGestion.exception.ResourceNotFoundException;
import grupoLem.appGestion.model.Reservation;
import grupoLem.appGestion.model.Room;
import grupoLem.appGestion.model.RoomState;
import grupoLem.appGestion.model.RoomStatePerDay;
import grupoLem.appGestion.service.ReservationService;
import grupoLem.appGestion.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/")
public class RoomController {

    @Autowired
    RoomService roomService;

    @Autowired
    ReservationService reservationService;

    //Mostrar todas las habitaciones
    @GetMapping("/habitaciones")
    public List<Room> getAllRooms() {
        List<Room> rooms = this.roomService.rooms();
        return rooms;
    }

    //Crear una Habitacion
    @PostMapping("/NuevaHabitacion")
    public Room createRoom(@RequestBody Room room) {
        this.roomService.save(room);
        return room;
    }

    //Busccar habitacion por ID
    @GetMapping("/buscarHabitacion/{idRoom}")
    public ResponseEntity<Room> findById(@PathVariable Integer idRoom) {
        Room room = roomService.findById(idRoom);
        if (room != null) {
            return ResponseEntity.ok(room);
        }else{
            throw new ResourceNotFoundException("Habitacion No encontrada con el id: " + idRoom);
        }
    }

    //Actualizar una habitacion, cambiar estado,etc
    @PutMapping("/editarHabitacion/{idRoom}")
    public ResponseEntity<Room> updateRoom(@PathVariable Integer idRoom,
                                           @RequestBody Room roomReceived){
        Room room = this.roomService.findById(idRoom);
        if (room == null)
            throw new ResourceNotFoundException("Habitacion No encontrada con el ID: "+idRoom);
        room.setRoomBeds(roomReceived.getRoomBeds());
        room.setRoomState(roomReceived.getRoomState());
        room.setRoomNumber(roomReceived.getRoomNumber());
        room.setPrice(roomReceived.getPrice());
        this.roomService.save(room);
        return ResponseEntity.ok(room);
    }

    //Eliminar una habitacion:
    @DeleteMapping("/eliminarHabitacion/{idRoom}")
    public ResponseEntity<Map<String, Boolean>> deleteById(@PathVariable Integer idRoom){
        Room room = this.roomService.findById(idRoom);
        if (room == null)
            throw new ResourceNotFoundException("Habitacion no encontrada con el ID: "+idRoom);
        this.roomService.deleteById(idRoom);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Eliminado",Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/habitacionesDisponibles")
    public List<Room> findAvailableRooms(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                         @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Room> allRooms = roomService.rooms();
        List<Room> availableRooms = new ArrayList<>(allRooms);

        for (Room room : allRooms) {
            List<Reservation> overlappingReservations = room.getReservations().stream()
                    .filter(reservation -> reservation.getStartDate().isBefore(endDate) && reservation.getEndDate().isAfter(startDate))
                    .collect(Collectors.toList());

            if (!overlappingReservations.isEmpty()) {
                availableRooms.remove(room);
            }
        }

        return availableRooms;
    }


    //traer todas las habitaciones por estado
    @GetMapping("/habitaciones/estado")
    public List<RoomStatePerDay> getRoomStatePerDay(@RequestParam Integer idRoom,
                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<RoomStatePerDay> roomStatePerDayList = new ArrayList<>();
        Room room = roomService.findById(idRoom);
        if (room != null) {
            LocalDate date = startDate;
            while (!date.isAfter(endDate)) {
                RoomState roomState = room.getRoomState();
                LocalDate finalDate = date;
                if (date.isBefore(room.getReservations().stream()
                        .map(Reservation::getStartDate)
                        .filter(startDateInRange -> !startDateInRange.isAfter(finalDate))
                        .min((d1, d2) -> d1.compareTo(d2))
                        .orElse(room.getReservations().isEmpty() ? endDate.plusDays(1) : room.getReservations().get(0).getStartDate()))) {
                    roomState = RoomState.LIBRE;
                }
                if (date.isAfter(room.getReservations().stream()
                        .map(Reservation::getEndDate)
                        .filter(endDateInRange -> !endDateInRange.isBefore(finalDate))
                        .max((d1, d2) -> d1.compareTo(d2))
                        .orElse(room.getReservations().isEmpty() ? startDate.minusDays(1) : room.getReservations().get(0).getEndDate()))) {
                    roomState = RoomState.OCUPADA;
                }
                roomStatePerDayList.add(new RoomStatePerDay(date, roomState));
                date = date.plusDays(1);
            }
        }
        return roomStatePerDayList;
    }
    
}
