
package grupoLem.appGestion.service;

import grupoLem.appGestion.model.MediaPayment;
import grupoLem.appGestion.repository.MediaPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class MediaPaymentService implements IMediaPaymentService{

    @Autowired
    private MediaPaymentRepository mediaPaymentsRepository;

    @Transactional
    public void realizarPago(MediaPayment mediaPayments, double amount, String tipoPago) {
        try {
            mediaPayments.payment(amount, tipoPago);
            mediaPayments.updateTotalDelivered();
            mediaPaymentsRepository.save(mediaPayments);
            System.out.println("Pago realizado correctamente.");
        } catch (Exception e) {
            System.err.println("Error al realizar el pago: " + e.getMessage());
            throw new RuntimeException("Error al realizar el pago", e);
        }
    }

    @Transactional
    public void updateExtras(MediaPayment mediaPayments, double amount) {
        try {
            mediaPayments.updateExtras();
            mediaPayments.updateTotalDelivered();
            mediaPaymentsRepository.save(mediaPayments);
            System.out.println("Gastos extras agregados correctamente.");
        } catch (Exception e) {
            System.err.println("Error al agregar gastos extras: " + e.getMessage());
            throw new RuntimeException("Error al agregar gastos extras", e);
        }
    }

    @Transactional
    public void calcularRestoCuenta(MediaPayment mediaPayments) {
        try {
            mediaPayments.updateTotalDelivered();
            mediaPayments.remainingAccount();
            mediaPaymentsRepository.save(mediaPayments);
            System.out.println("Resto de la cuenta calculado correctamente.");
        } catch (Exception e) {
            System.err.println("Error al calcular el resto de la cuenta: " + e.getMessage());
            throw new RuntimeException("Error al calcular el resto de la cuenta", e);
        }
    }

    @Override
    public List<MediaPayment> getAllMediaPayments() {
        return this.mediaPaymentsRepository.findAll();
    }

    @Override
    public MediaPayment findById(Integer idMediaPayments) {
        MediaPayment mediaPayment = this.mediaPaymentsRepository.findById(idMediaPayments).orElse(null);
        return mediaPayment;
    }

    @Override
    public MediaPayment save(MediaPayment mediaPayments) {
        return this.mediaPaymentsRepository.save(mediaPayments);
    }

    @Override
    public void deleteById(Integer idMediaPayments) {
        this.mediaPaymentsRepository.deleteById(idMediaPayments);
    }

}
