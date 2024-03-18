package grupoLem.appGestion.service;

import grupoLem.appGestion.model.Host;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class HostService implements IHostService{

    @Autowired
    private grupoLem.appGestion.repository.HostRepository HostRepository;

    @Override
    public List<Host> hosts() {
        return this.HostRepository.findAll();
    }

    @Override
    public Host findById(Integer idHost) {
        Host host = this.HostRepository.findById(idHost).orElse(null);
        return host;
    }

    @Override
    public Host save(Host host) {
        return this.HostRepository.save(host);
    }

    @Override
    public void deleteById(Integer idHost) {
        this.HostRepository.deleteById(idHost);
    }
}
