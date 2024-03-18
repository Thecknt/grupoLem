package grupoLem.appGestion.repository;

import grupoLem.appGestion.model.MediaPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaPaymentRepository extends JpaRepository<MediaPayment, Integer> {
}
