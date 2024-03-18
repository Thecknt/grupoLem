<<<<<<< HEAD
package grupoLem.appGestion.controller;

import grupoLem.appGestion.exception.ResourceNotFoundException;
import grupoLem.appGestion.model.Host;
import grupoLem.appGestion.service.HostService;
import grupoLem.appGestion.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

    @RestController
    @CrossOrigin(origins = "*")
    @RequestMapping("/")
    public class HostController {

        @Autowired
        private HostService hostService;
        @Autowired
        private ReservationService reservationService;


        //Consultar todos los huespedes
        @GetMapping("/Huespedes")
        public List<Host> getAllHost() {
            List<Host> hosts= this.hostService.hosts();
            return hosts;
        }

        //Crear huesped
        @PostMapping("/crearHuesped")
        public Host createHost(@RequestBody Host host) {
            return this.hostService.save(host);
        }


        //Buscar huesped por ID
        @GetMapping("/huesped/{id}")
        public ResponseEntity<Host> findHostById(@PathVariable Integer idHost) {
            Host host = this.hostService.findById(idHost);
            if (host != null) {
                return ResponseEntity.ok(host);
            } else {
                throw new ResourceNotFoundException("Huesped no encontrado.");
            }
        }

        @PutMapping("/actualizarHuesped/{id}")
        public ResponseEntity<Host> updateHost(@PathVariable Integer idHost,
                                               @RequestBody Host hostReceived){
            Host host = this.hostService.findById(idHost);
            if (host == null)
                throw new ResourceNotFoundException("Huesped No encontrado. "+ idHost);
            host.setHostName(hostReceived.getHostName());
            host.setHostLastname(hostReceived.getHostLastname());
            host.setHostDni(hostReceived.getHostDni());
            host.setHostTelephone(hostReceived.getHostTelephone());
            host.setHostBirthDay(hostReceived.getHostBirthDay());
            host.setNumberOfCompanions(hostReceived.getNumberOfCompanions());
            host.setNotes(hostReceived.getNotes());
            host.setReservations(hostReceived.getReservations());
            this.hostService.save(host);
            return ResponseEntity.ok(host);
        }

        //Eliminar un huesped de la base de Datos
        @DeleteMapping("/eliminarHuesped/{id}")
        public ResponseEntity<Map<String, Boolean>> deleteHost(@PathVariable Integer idHost){
            Host host = this.hostService.findById(idHost);
            if (host == null)
                throw new ResourceNotFoundException("Huesped con ID "+ idHost +", no fue encontrado");
            this.hostService.deleteById(idHost);
            Map<String, Boolean> response = new HashMap<>();
            response.put("Eliminado",Boolean.TRUE);
            return ResponseEntity.ok(response);
        }
    }
=======
package grupoLem.appGestion.controller;

import grupoLem.appGestion.exception.ResourceNotFoundException;
import grupoLem.appGestion.model.Host;
import grupoLem.appGestion.service.HostService;
import grupoLem.appGestion.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

    @RestController
    @CrossOrigin(origins = "*")
    @RequestMapping("/")
    public class HostController {

        @Autowired
        private HostService hostService;
        @Autowired
        private ReservationService reservationService;


        //Consultar todos los huespedes
        @GetMapping("/Huespedes")
        public List<Host> getAllHost() {
            List<Host> hosts= this.hostService.hosts();
            return hosts;
        }

        //Crear huesped
        @PostMapping("/crearHuesped")
        public Host createHost(@RequestBody Host host) {
            return this.hostService.save(host);
        }


        //Buscar huesped por ID
        @GetMapping("/huesped/{id}")
        public ResponseEntity<Host> findHostById(@PathVariable Integer idHost) {
            Host host = this.hostService.findById(idHost);
            if (host != null) {
                return ResponseEntity.ok(host);
            } else {
                throw new ResourceNotFoundException("Huesped no encontrado.");
            }
        }

        @PutMapping("/actualizarHuesped/{id}")
        public ResponseEntity<Host> updateHost(@PathVariable Integer idHost,
                                               @RequestBody Host hostReceived){
            Host host = this.hostService.findById(idHost);
            if (host == null)
                throw new ResourceNotFoundException("Huesped No encontrado. "+ idHost);
            host.setHostName(hostReceived.getHostName());
            host.setHostLastname(hostReceived.getHostLastname());
            host.setHostDni(hostReceived.getHostDni());
            host.setHostTelephone(hostReceived.getHostTelephone());
            host.setHostBirthDay(hostReceived.getHostBirthDay());
            host.setNumberOfCompanions(hostReceived.getNumberOfCompanions());
            host.setNotes(hostReceived.getNotes());
            host.setReservations(hostReceived.getReservations());
            this.hostService.save(host);
            return ResponseEntity.ok(host);
        }

        //Eliminar un huesped de la base de Datos
        @DeleteMapping("/eliminarHuesped/{id}")
        public ResponseEntity<Map<String, Boolean>> deleteHost(@PathVariable Integer idHost){
            Host host = this.hostService.findById(idHost);
            if (host == null)
                throw new ResourceNotFoundException("Huesped con ID "+ idHost +", no fue encontrado");
            this.hostService.deleteById(idHost);
            Map<String, Boolean> response = new HashMap<>();
            response.put("Eliminado",Boolean.TRUE);
            return ResponseEntity.ok(response);
        }
    }
>>>>>>> e294b050ac586c1d154ff242081dc37ef230cbbd
