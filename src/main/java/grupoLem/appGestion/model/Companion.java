
package grupoLem.appGestion.model;

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
@ToString
public class Companion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCompanion;

    private String companionName;
    private String companionLastname;
    private String companionDni;

    @Override
    public int hashCode() {
        return Objects.hash(idCompanion);
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "huesped_id")
    private Host host;
}