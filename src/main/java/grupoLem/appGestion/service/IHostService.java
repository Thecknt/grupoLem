package grupoLem.appGestion.service;

import grupoLem.appGestion.model.Host;

import java.util.List;

public interface IHostService {

    public List<Host> hosts();

    public Host findById(Integer idHost);

    public Host save(Host host);

    public void deleteById(Integer idHost);
}
