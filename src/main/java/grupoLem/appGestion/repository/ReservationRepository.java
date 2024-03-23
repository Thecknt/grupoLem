
package grupoLem.appGestion.repository;

import grupoLem.appGestion.model.Reservation;
import grupoLem.appGestion.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findByStartDate(LocalDate startDate);

    List<Reservation> findByEndDate(LocalDate endDate);

    @Query("""
      select r
      from Reservation r
      where r.room.id = :roomId
        and r.startDate <= :endDate
        and r.endDate >= :startDate
    """)
    List<Reservation> findOverlappingReservations(@Param("roomId") Integer roomId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
