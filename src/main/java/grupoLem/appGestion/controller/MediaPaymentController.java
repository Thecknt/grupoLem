
package grupoLem.appGestion.controller;

import grupoLem.appGestion.exception.ResourceNotFoundException;
import grupoLem.appGestion.model.MediaPayment;
import grupoLem.appGestion.model.Reservation;
import grupoLem.appGestion.service.MediaPaymentService;
import grupoLem.appGestion.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/")
public class MediaPaymentController {
    @Autowired
    private MediaPaymentService mediaPaymentsService;

    @Autowired
    ReservationService reservationService;

    @GetMapping("/mediosDePago")
    public List<MediaPayment> getAllMediaPayment() {
        List<MediaPayment> allMedia = this.mediaPaymentsService.getAllMediaPayments();
        return allMedia;
    }

    //guardar el medio de pago en la reserva, creo y lo asocio
    @PostMapping("medioDePagoReserva/{idReservation}")
    public MediaPayment createMediaPayment(@PathVariable("idReservation") Integer idReservation,
                                                           @RequestBody MediaPayment paymentReceived) {
        Reservation reservation = reservationService.getReservationById(idReservation);
        MediaPayment mediaPayment = new MediaPayment(reservation);
        mediaPayment.setCash(paymentReceived.getCash());
        mediaPayment.setCreditCard(paymentReceived.getCreditCard());
        mediaPayment.setExtras(paymentReceived.getExtras());
        mediaPayment.setRemainingTotal(paymentReceived.getRemainingTotal());
        mediaPayment.setTotalDelivered(paymentReceived.getTotalDelivered());
        MediaPayment createdMediaPayment = mediaPaymentsService.save(mediaPayment);
        return createdMediaPayment;
    }

    //Buscar medio de pago por el ID
    @GetMapping("medioDePago/{idMediaPayment}")
    public ResponseEntity<MediaPayment> getMediaPaymentById(@PathVariable Integer idMediaPayment) {
        MediaPayment mediaPayment = mediaPaymentsService.findById(idMediaPayment);
        if (mediaPayment != null) {
            return ResponseEntity.ok(mediaPayment);
        } else {
            throw new ResourceNotFoundException("El medio de pago con el ID " + idMediaPayment + ", no fue encontrado.");
        }
    }

    //Editar el medio de pago por el ID
    @PutMapping("editarMedioPago/{idMediaPayment}")
    public ResponseEntity<MediaPayment> updateMediaPayment(@PathVariable Integer idMediaPayment,
                                                           @RequestBody MediaPayment mediaPaymentReceived) {
        MediaPayment mediaPayment = mediaPaymentsService.findById(idMediaPayment);
        if (mediaPayment != null) {
            mediaPayment.setCash(mediaPaymentReceived.getCash());
            mediaPayment.setDeposit(mediaPaymentReceived.getDeposit());
            mediaPayment.setCreditCard(mediaPaymentReceived.getCreditCard());
            mediaPayment.setExtras(mediaPaymentReceived.getExtras());
            mediaPayment.setInitialPayment(mediaPaymentReceived.getInitialPayment());
            mediaPayment.setTotalReservation(mediaPaymentReceived.getTotalReservation());
            mediaPayment.setRemainingTotal(mediaPaymentReceived.getRemainingTotal());
            MediaPayment updatedMediaPayment = mediaPaymentsService.save(mediaPayment);
            return ResponseEntity.ok(updatedMediaPayment);
        } else {
            throw new ResourceNotFoundException("El medio de pago con el ID " + idMediaPayment + ", no fue encontrado.");
        }
    }

    //Eliminar el medio de pagp por el ID
    @DeleteMapping("eliminarMedioPago/{idMediaPayment}")
    public ResponseEntity<Map<String, Boolean>> deleteMediaPayment(@PathVariable Integer idMediaPayment) {
        MediaPayment mediaPayment = mediaPaymentsService.findById(idMediaPayment);
        if (mediaPayment != null) {
            mediaPaymentsService.deleteById(idMediaPayment);
            Map<String, Boolean> response = new HashMap<>();
            response.put("Deleted", Boolean.TRUE);
            return ResponseEntity.ok(response);
        } else {
            throw new ResourceNotFoundException("El medio pago con el ID " + idMediaPayment + " no fue encontrado.");
        }
    }

    //Controladores sobre la reserva "x"

    //Creo un nuevo medio de pago para la reserva y actualizar el total
    @PostMapping("/metodoDePago")
    public ResponseEntity<Reservation> createNewReservationPaymentAndUpdateTotals(
            @PathVariable Integer idReservation,
            @RequestParam double totalReservation) {
        Reservation reservation = reservationService.findById(idReservation);
        reservationService.createNewPaymentReservationAndUpdateTotals(reservation, totalReservation);
        return new ResponseEntity<>(reservation, HttpStatus.CREATED);
    }

    //Agrego gastos extras y los actualizo
    @PostMapping("/agregarExtras")
    public ResponseEntity<Reservation> addExtraExpenseToReservationPaymentAndUpdateTotals(
            @PathVariable Integer idReservation,
            @RequestParam double extraExpense){
        Reservation reservation = reservationService.findById(idReservation);
        reservationService.addExtraExpenseAndUpdateTotals(idReservation, extraExpense);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    //endPoint para obtener el restante de la deuda
    @PostMapping("/restoAPagar")
    public ResponseEntity<Double> getRestToPayForReservation(@PathVariable Integer idReservation){
        Double remainingTotal = Double.valueOf(reservationService.getRestToPayForReservation(idReservation));
        return new ResponseEntity<>(remainingTotal, HttpStatus.OK);
    }















}

