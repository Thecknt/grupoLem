<<<<<<< HEAD
package grupoLem.appGestion.service;

import grupoLem.appGestion.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface IReservationService {

    List<Reservation> getAllReservations();

    public Reservation findById(Integer IdReservations);

    public Reservation save(Reservation reservations);

    public void deleteById(Integer idReservations);

    List<Reservation> findByStartDate(LocalDate startDate);

    List<Reservation> findByEndDate(LocalDate endDate);

    List<Reservation> findByIncludesBreakfast(boolean includesBreakfast);

    List<Reservation> findByIncludesHalfPension(boolean includesHalfPension);

    List<Reservation> findByIncludesFullPension(boolean includesFullPension);

    Reservation createNewReservation(String pensionType, LocalDate checkInDate, LocalTime checkInTime, LocalDate checkOutDate, LocalTime checkOutTime);
}
=======
package grupoLem.appGestion.service;

import grupoLem.appGestion.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface IReservationService {

    List<Reservation> getAllReservations();

    public Reservation findById(Integer IdReservations);

    public Reservation save(Reservation reservations);

    public void deleteById(Integer idReservations);

    List<Reservation> findByStartDate(LocalDate startDate);

    List<Reservation> findByEndDate(LocalDate endDate);

    List<Reservation> findByIncludesBreakfast(boolean includesBreakfast);

    List<Reservation> findByIncludesHalfPension(boolean includesHalfPension);

    List<Reservation> findByIncludesFullPension(boolean includesFullPension);

    Reservation createNewReservation(String pensionType, LocalDate checkInDate, LocalTime checkInTime, LocalDate checkOutDate, LocalTime checkOutTime);
}
>>>>>>> e294b050ac586c1d154ff242081dc37ef230cbbd
