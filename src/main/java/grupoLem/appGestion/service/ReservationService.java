
package grupoLem.appGestion.service;

import grupoLem.appGestion.exception.ResourceNotFoundException;
import grupoLem.appGestion.model.*;
import grupoLem.appGestion.repository.HostRepository;
import grupoLem.appGestion.repository.MediaPaymentRepository;
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

    @Autowired
    private MediaPaymentRepository mediaPaymentRepository;

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
        Double totalAmount = reservations.getTotalAmount() == null ? 0 : reservations.getTotalAmount();
        Room room = roomRepository.findById(reservations.getRoom().getIdRoom()).orElse(null);

      if (room != null) {
          room.setRoomState(RoomState.OCUPADA);
          MediaPayment mediaPayment = new MediaPayment(reservations, totalAmount);
          reservations.getMediaPayments().add(mediaPayment);
          reservations.setRoom(room);
          room.setPrice(totalAmount);
          roomRepository.save(room);
          return this.reservationsRepository.save(reservations);
      }

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

    //A partir de aca vamos a getionar los medios de pago de reservas: 👇🏼

     //Este metodo es para actualizar el remanente total:
    public void createNewPaymentReservationAndUpdateTotals(Reservation reservation, double totalReservation){
        if(reservation.getMediaPayments()==null){
            reservation.setMediaPayments(new ArrayList<>());
        }
        MediaPayment mediaPayment = new MediaPayment(reservation, totalReservation);
        reservation.addMediaPayment(mediaPayment);
        mediaPaymentRepository.save(mediaPayment);

        //llamo a la actualizacion del medio de pago
        updateRemainingTotalAfterPaymentCreation(reservation, mediaPayment);
    }

    //Actualizo el restante faltante despues de crear el nuevo medio de pago
    private void updateRemainingTotalAfterPaymentCreation(Reservation reservation, MediaPayment mediaPayment){
        double remainingTotal = mediaPayment.getRemainingTotal();
        reservation.getMediaPayments().get(0).setRemainingTotal(remainingTotal);
        reservationsRepository.save(reservation);
    }

    //Agregar cargos extra a la reserva
    public void addExtraExpenseAndUpdateTotals(Integer idReservations, double extraExpense){
        Reservation reservation = findById(idReservations);
        MediaPayment mediaPayment = reservation.getMediaPayments().get(0);

        //Actualizo el total de la reserva y extras
        double totalReservation = mediaPayment.getTotalReservation();
        double extras = mediaPayment.getExtras();
        mediaPayment.setTotalReservation(totalReservation + extraExpense);
        mediaPayment.setExtras(extras + extraExpense);

        updateRemainingTotalAfterPaymentCreation(reservation, mediaPayment);
    }

    //Obtener el resto de la deuda
    public double getRestToPayForReservation(Integer idReservation){
        Reservation reservation = findById(idReservation);
        MediaPayment mediaPayment = reservation.getMediaPayments().get(0);
        return mediaPayment.getRemainingTotal();
    }
















}