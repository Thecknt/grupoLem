package grupoLem.appGestion.service;

import grupoLem.appGestion.model.Companion;

import java.util.List;

public interface ICompanionService {

    public List<Companion> getAllCompanions();

    public Companion findById(Integer idCompanion);

    public Companion save(Companion companion);

    public void deleteById(Integer idCompanion);
}
