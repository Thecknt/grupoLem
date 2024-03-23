
package grupoLem.appGestion.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "acompaniantes")
@ToString
public class Companion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCompanion;

    private String companionName;
    private String companionLastname;
    private String companionDni;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "huesped_id")
    @JsonIgnoreProperties("companions")
    private Host host;
}