
package grupoLem.appGestion.service;

import grupoLem.appGestion.exception.ResourceNotFoundException;
import grupoLem.appGestion.model.Host;
import grupoLem.appGestion.model.Reservation;
import grupoLem.appGestion.model.Room;
import grupoLem.appGestion.model.RoomState;
import grupoLem.appGestion.repository.HostRepository;
import grupoLem.appGestion.repository.ReservationRepository;
import grupoLem.appGestion.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService implements IReservationService {


    @Autowired
    private ReservationRepository reservationsRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private HostRepository hostRepository;

    @Override
    public List<Reservation> getAllReservations() {
        List<Reservation> allReservations = this.reservationsRepository.findAll();
        return allReservations;
    }

    public Reservation getReservationById(Integer id) {
        // Thisis just an example, replace this code with the relevant one
        return reservationsRepository.findById(id).orElse(null);
    }
    @Override
    public Reservation findById(Integer IdReservations) {
        Reservation reservations =
                this.reservationsRepository.findById(IdReservations).orElse(null);
        return reservations;
    }

    @Override
    public Reservation save(Reservation reservations) {
        //fijate sebas aca trago el id de la habitacion y lo busco en el repo asi me muestra la habitacion
      Room room = roomRepository.findById(reservations.getRoom().getIdRoom()).orElse(null);
      if (room != null) {
          //Aca lo seteo a ocupada
          room.setRoomState(RoomState.OCUPADA);
          //ACa seteo la habitacion a la reserva
          reservations.setRoom(room);
          //y la guardo en la base de datos con el nuevo estado, sino siempre figura LIBRE
          roomRepository.save(room);
          return this.reservationsRepository.save(reservations);
      }

      //en caso de la que la habitacion sea nula te devuelvo este mensaje
        throw new RuntimeException("habitacion no encontrada con el ID" + reservations.getRoom().getIdRoom());
    }

    @Override
    public void deleteById(Integer idReservations) {
        Reservation reservation = findById(idReservations);
        Room room = reservation.getRoom();
        room.setRoomState(RoomState.LIBRE);
        reservationsRepository.deleteById(idReservations);
    }

    @Override
    public List<Reservation> findByStartDate(LocalDate startDate) {
        List<Reservation> startDateList =
                this.reservationsRepository.findByStartDate(startDate);
        return startDateList;
    }

    @Override
    public List<Reservation> findByEndDate(LocalDate endDate) {
        List<Reservation> endDateList =
                this.reservationsRepository.findByEndDate(endDate);
        return endDateList;
    }

    @Override
    public boolean isReservationOverlapping(Reservation newReservation) {
        Integer selectedRoomId = newReservation.getRoom().getIdRoom();
        LocalDate reservationStartDate = newReservation.getStartDate();
        LocalDate reservationEndDate = newReservation.getEndDate();

        List<Reservation> overlappingReservations = reservationsRepository.findOverlappingReservations(selectedRoomId ,reservationStartDate, reservationEndDate);

        return !overlappingReservations.isEmpty();
    }


    @Override
    public Reservation createNewReservation(String pensionType, LocalDate checkInDate, LocalTime checkInTime, LocalDate checkOutDate, LocalTime checkOutTime) {
        Reservation reservation = new Reservation();
        reservation.setTypePension(pensionType);
        reservation.setCheckInDate(checkInDate);
        reservation.setCheckInTime(checkInTime);
        reservation.setCheckOutDate(checkOutDate);
        reservation.setCheckOutTime(checkOutTime);
        return reservationsRepository.save(reservation);
    }

}