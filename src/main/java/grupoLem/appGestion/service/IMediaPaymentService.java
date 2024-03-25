
package grupoLem.appGestion.service;

import grupoLem.appGestion.model.MediaPayment;

import java.util.List;
import java.util.Optional;


public interface IMediaPaymentService {
    public List<MediaPayment> getAllMediaPayments();

    public MediaPayment findById(Integer idMediaPayments);

    public MediaPayment save(MediaPayment mediaPayments);

    public void deleteById(Integer idMediaPayments);
}
