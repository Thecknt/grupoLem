
package grupoLem.appGestion.service;

import grupoLem.appGestion.model.Room;

import java.time.LocalDate;
import java.util.List;

public interface IRoomService {
    public List<Room> rooms();

    public Room findById(Integer idRoom);

    public Room save(Room room);

    public void deleteById(Integer idRoom);

    public List<Room> findAvailableRooms(Integer roomId, LocalDate startDate, LocalDate endDate);
}