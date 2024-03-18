<<<<<<< HEAD
package grupoLem.appGestion.repository;

import grupoLem.appGestion.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findByStartDate(LocalDate startDate);

    List<Reservation> findByEndDate(LocalDate endDate);

    List<Reservation> findByIncludesBreakfast(boolean includesBreakfast);

    List<Reservation> findByIncludesHalfPension(boolean includesHalfPension);

    List<Reservation> findByIncludesFullPension(boolean includesFullPension);
}
=======
package grupoLem.appGestion.repository;

import grupoLem.appGestion.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findByStartDate(LocalDate startDate);

    List<Reservation> findByEndDate(LocalDate endDate);

    List<Reservation> findByIncludesBreakfast(boolean includesBreakfast);

    List<Reservation> findByIncludesHalfPension(boolean includesHalfPension);

    List<Reservation> findByIncludesFullPension(boolean includesFullPension);
}
>>>>>>> e294b050ac586c1d154ff242081dc37ef230cbbd
