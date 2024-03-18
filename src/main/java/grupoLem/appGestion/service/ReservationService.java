
package grupoLem.appGestion.service;

import grupoLem.appGestion.model.Reservation;
import grupoLem.appGestion.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReservationService implements IReservationService{


    @Autowired
    private ReservationRepository reservationsRepository;

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
        return this.reservationsRepository.save(reservations);
    }

    @Override
    public void deleteById(Integer idReservations) {
        this.reservationsRepository.deleteById(idReservations);
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
    public List<Reservation> findByIncludesBreakfast(boolean includesBreakfast) {
        List<Reservation> breakfastList =
                this.reservationsRepository.findByIncludesBreakfast(includesBreakfast);
        return breakfastList;
    }

    @Override
    public List<Reservation> findByIncludesHalfPension(boolean includesHalfPension) {
        List<Reservation> halfPensionList =
                this.reservationsRepository.findByIncludesHalfPension(includesHalfPension);
        return halfPensionList;
    }

    @Override
    public List<Reservation> findByIncludesFullPension(boolean includesFullPension) {
        List<Reservation> fullPensionList=
                this.reservationsRepository.findByIncludesFullPension(includesFullPension);
        return fullPensionList;
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
