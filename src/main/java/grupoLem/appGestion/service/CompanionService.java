package grupoLem.appGestion.service;

import grupoLem.appGestion.model.Companion;
import grupoLem.appGestion.repository.CompanionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanionService implements ICompanionService {


    @Autowired
    private CompanionRepository companionRepository;

    @Override
    public List<Companion> getAllCompanions() {
        return this.companionRepository.findAll();
    }

    @Override
    public Companion findById(Integer idCompanion) {
        Companion companion = this.companionRepository.findById(idCompanion).orElse(null);
        return companion;
    }

    @Override
    public Companion save(Companion companion) {
        return this.companionRepository.save(companion);
    }

    @Override
    public void deleteById(Integer idCompanion) {
        this.companionRepository.deleteById(idCompanion);
    }
}
