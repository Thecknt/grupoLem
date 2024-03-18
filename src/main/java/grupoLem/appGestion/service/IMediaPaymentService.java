package grupoLem.appGestion.service;

import grupoLem.appGestion.model.MediaPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface IMediaPaymentService {
    public List<MediaPayment> getAllMediaPayments();

    public MediaPayment findById(Integer idMediaPayments);

    public MediaPayment save(MediaPayment mediaPayments);

    public void deleteById(Integer idMediaPayments);
}
