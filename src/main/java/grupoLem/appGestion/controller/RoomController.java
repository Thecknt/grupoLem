<<<<<<< HEAD
package grupoLem.appGestion.controller;

import grupoLem.appGestion.exception.ResourceNotFoundException;
import grupoLem.appGestion.model.Room;
import grupoLem.appGestion.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/")
public class RoomController {

    @Autowired
    RoomService roomService;

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
    @GetMapping("/buscarHabitacion/{id}")
    public ResponseEntity<Room> findById(@PathVariable Integer idRoom) {
        Room room = roomService.findById(idRoom);
        if (room != null) {
            return ResponseEntity.ok(room);
        }else{
            throw new ResourceNotFoundException("Habitacion No encontrada con el id: " + idRoom);
        }
    }

    //Actualizar una habitacion, cambiar estado,etc
    @PutMapping("/editarHabitacion/{id}")
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
    @DeleteMapping("/eliminarHabitacion/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteById(@PathVariable Integer idRoom){
        Room room = this.roomService.findById(idRoom);
        if (room == null)
            throw new ResourceNotFoundException("Habitacion no encontrada con el ID: "+idRoom);
        this.roomService.deleteById(idRoom);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Eliminado",Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
=======
package grupoLem.appGestion.controller;

import grupoLem.appGestion.exception.ResourceNotFoundException;
import grupoLem.appGestion.model.Room;
import grupoLem.appGestion.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/")
public class RoomController {

    @Autowired
    RoomService roomService;

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
    @GetMapping("/buscarHabitacion/{id}")
    public ResponseEntity<Room> findById(@PathVariable Integer idRoom) {
        Room room = roomService.findById(idRoom);
        if (room != null) {
            return ResponseEntity.ok(room);
        }else{
            throw new ResourceNotFoundException("Habitacion No encontrada con el id: " + idRoom);
        }
    }

    //Actualizar una habitacion, cambiar estado,etc
    @PutMapping("/editarHabitacion/{id}")
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
    @DeleteMapping("/eliminarHabitacion/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteById(@PathVariable Integer idRoom){
        Room room = this.roomService.findById(idRoom);
        if (room == null)
            throw new ResourceNotFoundException("Habitacion no encontrada con el ID: "+idRoom);
        this.roomService.deleteById(idRoom);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Eliminado",Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
>>>>>>> e294b050ac586c1d154ff242081dc37ef230cbbd
