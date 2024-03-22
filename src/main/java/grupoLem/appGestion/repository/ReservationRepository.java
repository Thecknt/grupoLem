
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

    //public Reservation updateReservation(Integer idReservation, Reservation updatedReservation);

    @Query("SELECT r FROM Reservation r WHERE (r.room = :room AND (:startDate BETWEEN r.checkInDate AND r.checkOutDate OR :endDate BETWEEN r.checkInDate AND r.checkOutDate))")
    List<Reservation> findOverlappingReservations(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate,@Param("room") Room room);
}
