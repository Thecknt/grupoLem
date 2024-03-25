
package grupoLem.appGestion.service;

import grupoLem.appGestion.model.Reservation;
import grupoLem.appGestion.model.Room;
import grupoLem.appGestion.model.RoomState;
import grupoLem.appGestion.repository.ReservationRepository;
import grupoLem.appGestion.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService implements IRoomService{
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    private ReservationService reservationService;

    @Override
    public List<Room> rooms() {
        List<Room> rooms = this.roomRepository.findAll();
        return rooms;
    }

    @Override
    public Room findById(Integer idRoom) {
        Room room = this.roomRepository.findById(idRoom).orElse(null);
        return room;
    }

    @Override
    public Room save(Room room) {
        return this.roomRepository.save(room);
    }

    @Override
    public void deleteById(Integer idRoom) {
        this.roomRepository.deleteById(idRoom);
    }

    @Override
    public List<Room> findAvailableRooms(Integer roomId,LocalDate startDate, LocalDate endDate) {
        List<Reservation> overlappingReservations = reservationRepository.findOverlappingReservations(roomId,startDate,endDate);
        List<Room> allRooms = roomRepository.findAll();
        List<Room> availableRooms = new ArrayList<>(allRooms);

        if (overlappingReservations.isEmpty()) {
            return allRooms;
        }

        // Remove the rooms with overlapping reservations
        for (Reservation reservation : overlappingReservations) {
            if (availableRooms.contains(reservation.getRoom())) {
                availableRooms.remove(reservation.getRoom());
            }
        }

        return availableRooms;
    }

}
