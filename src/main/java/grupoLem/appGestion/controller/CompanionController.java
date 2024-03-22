package grupoLem.appGestion.controller;


import grupoLem.appGestion.exception.ResourceNotFoundException;
import grupoLem.appGestion.model.Companion;
import grupoLem.appGestion.model.Host;
import grupoLem.appGestion.repository.CompanionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/")
public class CompanionController {

    @Autowired
    private CompanionRepository companionRepository;

    //Consultar todos los Acompañantes
    @GetMapping("/acompaniantes")
    public List<Companion> getAllHost() {
        List<Companion> companionsList = this.companionRepository.findAll();
        return companionsList;
    }

    //Crear Acompañante
    @PostMapping("/crearAcompaniante")
    public Companion createCompanion(@RequestBody Companion companion) {
        return this.companionRepository.save(companion);
    }

    //Buscar acompañante por ID
    @GetMapping("/acompaniante/{idCompanion}")
    public ResponseEntity<Companion> findCompanionById(@PathVariable Integer idCompanion) {
        Companion companion = this.companionRepository.findById(idCompanion).orElse(null);
        if (companion != null) {
            return ResponseEntity.ok(companion);
        } else {
            throw new ResourceNotFoundException("Acompañante no encontrado.");
        }
    }

    @PutMapping("/actualizarAcompaniante/{idCompanion}")
    public ResponseEntity<Companion> updateCompanion(@PathVariable Integer idCompanion,
                                                     @RequestBody Companion companionReceived) {
        Companion companion = this.companionRepository.findById(idCompanion).orElse(null);
        if (companion == null)
            throw new ResourceNotFoundException("Acompañante No encontrado. " + idCompanion);
        companion.setCompanionDni(companionReceived.getCompanionDni());
        companion.setCompanionName(companionReceived.getCompanionName());
        companion.setCompanionLastname(companionReceived.getCompanionLastname());
        companion.setHost(companionReceived.getHost());
        this.companionRepository.save(companion);
        return ResponseEntity.ok(companion);
    }

    //Eliminar un Acompañante de la base de Datos
    @DeleteMapping("/eliminarAcompaniante/{idCompanion}")
    public ResponseEntity<Map<String, Boolean>> deleteCompanion(@PathVariable Integer idCompanion){
        Companion companion = this.companionRepository.findById(idCompanion).orElse(null);
        if (companion == null)
            throw new ResourceNotFoundException("Acompañante con ID "+ idCompanion +", no fue encontrado");
        this.companionRepository.deleteById(idCompanion);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Eliminado",Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
