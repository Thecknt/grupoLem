
package grupoLem.appGestion.service;

import grupoLem.appGestion.exception.ResourceNotFoundException;
import grupoLem.appGestion.model.Reservation;
import grupoLem.appGestion.model.Room;
import grupoLem.appGestion.model.RoomState;
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

    @Override
    public List<Reservation> getAllReservations() {
        List<Reservation> allReservations =
                this.reservationsRepository.findAll();
        return allReservations;
    }

    @Override
    public Reservation findById(Integer IdReservations) {
        Reservation reservations =
                this.reservationsRepository.findById(IdReservations).orElse(null);
        return reservations;
    }

    @Override
    public Reservation save(Reservation reservations) {
        Room room = reservations.getRoom();
        List<Reservation> overlappingReservations = findOverlappingReservations(reservations.getStartDate(), reservations.getEndDate(), room);
        if (!overlappingReservations.isEmpty()) {
            throw new ResourceNotFoundException("There is already a reservation for this room during this period");
        }
        room.setRoomState(RoomState.OCUPADA);
        reservations.setRoom(room);
        reservationsRepository.save(reservations);
        return reservations;
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
    public List<Reservation> findOverlappingReservations(LocalDate startDate, LocalDate endDate, Room room) {
        return reservationsRepository.findOverlappingReservations(startDate, endDate, room);
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

    @Override
    public List<Room> findAvailableRooms(LocalDate startDate, LocalDate endDate) {
        return null;
    }

    @Override
    public Reservation updateReservation(Integer idReservation, Reservation updatedReservation) {
        Reservation existingReservation = findById(idReservation);
        if (existingReservation == null) {
            throw new ResourceNotFoundException("Reservation not found with ID: " + idReservation);
        }
        Room existingRoom = existingReservation.getRoom();
        List<Reservation> overlappingReservations = findOverlappingReservations(updatedReservation.getStartDate(), updatedReservation.getEndDate(), existingRoom);
        if (!overlappingReservations.isEmpty()) {
            throw new ResourceNotFoundException("There is already a reservation for this room during this period");
        }
        existingReservation.setStartDate(updatedReservation.getStartDate());
        existingReservation.setEndDate(updatedReservation.getEndDate());
        existingReservation.setHost(updatedReservation.getHost());
        existingReservation.setTypePension(updatedReservation.getTypePension());
        existingReservation.setCheckInDate(updatedReservation.getCheckInDate());
        existingReservation.setCheckInTime(updatedReservation.getCheckInTime());
        existingReservation.setCheckOutDate(updatedReservation.getCheckOutDate());
        existingReservation.setCheckOutTime(updatedReservation.getCheckOutTime());
        existingReservation.setEndDate(updatedReservation.getEndDate());
        existingReservation.setStartDate(updatedReservation.getStartDate());
        return reservationsRepository.save(existingReservation);
    }
}