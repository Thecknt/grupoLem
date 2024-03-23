
package grupoLem.appGestion.repository;

import grupoLem.appGestion.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

//    @Query("SELECT r FROM Room r WHERE r.id NOT IN (SELECT br.room.id FROM Booking br WHERE (br.startDate <= :startDate AND br.endDate >= :startDate) OR (br.startDate <= :endDate AND br.endDate >= :endDate) OR (br.startDate >= :startDate AND br.endDate <= :endDate))")
//    List<Room> findAvailableRooms(LocalDate startDate, LocalDate endDate);
}

