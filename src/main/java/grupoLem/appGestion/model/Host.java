
package grupoLem.appGestion.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "huesped")
public class Host {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idHost;

    private String hostName;
    private String hostLastname;
    private String hostDni;
    private String hostTelephone;
    private String hostBirthDay;
    private String notes;
    private Integer numberOfCompanions;

    @OneToMany(mappedBy = "host", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Reservation> reservations = new ArrayList<>();
}
