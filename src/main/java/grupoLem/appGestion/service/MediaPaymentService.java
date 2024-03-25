
package grupoLem.appGestion.service;

import grupoLem.appGestion.exception.ResourceNotFoundException;
import grupoLem.appGestion.model.MediaPayment;
import grupoLem.appGestion.repository.MediaPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MediaPaymentService implements IMediaPaymentService{

    @Autowired
    private MediaPaymentRepository mediaPaymentsRepository;

    //Todos los medios de pago
    @Override
    public List<MediaPayment> getAllMediaPayments() {
        return this.mediaPaymentsRepository.findAll();
    }

    @Override
    public MediaPayment findById(Integer idMediaPayments) {
        MediaPayment mediaPayment = this.mediaPaymentsRepository.findById(idMediaPayments)
                .orElseThrow(()->new ResourceNotFoundException("Medio de pago no encontrado con el ID:" + idMediaPayments));
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
