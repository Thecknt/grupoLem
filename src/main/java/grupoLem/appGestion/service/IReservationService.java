
package grupoLem.appGestion.service;

import grupoLem.appGestion.model.Reservation;
import grupoLem.appGestion.model.Room;

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

    public List<Reservation> findOverlappingReservations(LocalDate startDate, LocalDate endDate, Room room);

    Reservation createNewReservation(String pensionType, LocalDate checkInDate, LocalTime checkInTime, LocalDate checkOutDate, LocalTime checkOutTime);

    public List<Room> findAvailableRooms(LocalDate startDate, LocalDate endDate);

    public Reservation updateReservation(Integer idReservation, Reservation updatedReservation);
}

