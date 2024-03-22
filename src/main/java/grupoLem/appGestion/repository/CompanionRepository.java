package grupoLem.appGestion.repository;

import grupoLem.appGestion.model.Companion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanionRepository extends JpaRepository<Companion, Integer> {
}
